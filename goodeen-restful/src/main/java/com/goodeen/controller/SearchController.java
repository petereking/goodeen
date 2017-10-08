package com.goodeen.controller;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.goodeen.mapper.TripMapper;
import com.goodeen.mapper.UserMapper;
import com.goodeen.model.Trip;
import com.goodeen.model.User;
import com.goodeen.solr.model.TripCore;
import com.goodeen.solr.model.UserCore;
import com.goodeen.solr.repository.TripRepository;
import com.goodeen.solr.repository.UserRepository;

@Controller
@RequestMapping("/search")
public class SearchController {
  @Autowired
  private UserMapper userMapper;
  
  @Autowired
  private TripMapper tripMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TripRepository tripRepository;

  private static final Map<String, String> navMap = new LinkedHashMap<String, String>() {
    private static final long serialVersionUID = 5359710847506868837L;
    {
      // put("all", "所有结果");
      put("trips", "行程");
      put("users", "用户");
      // put("photos", "照片");
    }
  };

  @GetMapping
  public ModelAndView index(@RequestParam(value = "q", defaultValue = "") String q,
      @RequestParam(defaultValue = "1") Integer page) {
    ModelAndView mv = new ModelAndView("/search/index");
    mv.addObject("q", q);
    mv.addObject("navMap", navMap);
    return trips(q, page);
  }

  @GetMapping(params = "mode=trips")
  public ModelAndView trips(@RequestParam(value = "q", defaultValue = "") String q,
      @RequestParam(defaultValue = "1") Integer page) {
    ModelAndView mv = new ModelAndView("/search/trips");
    if(q.trim().equals("")){
      mv.addObject("total", 0);
      mv.addObject("q", q);
      mv.addObject("navMap", navMap);
      mv.addObject("searchMode", "trips");
      return mv;
    }
    HighlightPage<TripCore> hlTrips =
        tripRepository.findBySolrText(q, new PageRequest(page - 1, 2, new Sort(Sort.Direction.DESC, "id")));
    List<TripCore> tripCores = getHighlightContent(hlTrips);
    if (tripCores.size() > 0) {
      List<Integer> ids =
          tripCores.stream().map(TripCore::getId).collect(Collectors.toList());
      List<Trip> trips = tripMapper.queryByIds(ids);
      int index = 0;
      for (Trip trip : trips) {
        trip.setSummary(tripCores.get(index).getSummary());
        index++;
      }
      mv.addObject("trips", trips);
    }    
    if (page == 1) {
      mv.addObject("total", hlTrips.getTotalPages());
      mv.addObject("q", q);
      mv.addObject("navMap", navMap);
      mv.addObject("searchMode", "trips");
    } else {
      mv.setViewName("/trip/temp");
    }
    return mv;
  }

  @GetMapping(params = "mode=users")
  public ModelAndView users(HttpSession session,
      @RequestParam(value = "q", defaultValue = "") String q,
      @RequestParam(defaultValue = "1") Integer page) {
    ModelAndView mv = new ModelAndView("/search/users");
    if(q.trim().equals("")){
      mv.addObject("total", 0);
      mv.addObject("q", q);
      mv.addObject("navMap", navMap);
      mv.addObject("searchMode", "users");
      return mv;
    }
    HighlightPage<UserCore> hlUsers =
        userRepository.findBySolrText(q, new PageRequest(page - 1, 2, new Sort(Sort.Direction.DESC, "id")));
    List<UserCore> accounts = getHighlightContent(hlUsers);
    if (accounts.size() > 0) {
      List<Integer> ids =
          accounts.stream().map(UserCore::getId).collect(Collectors.toList());
      User currentUser = (User) session.getAttribute("currentUser");
      List<User> withRelationAccounts = userMapper.queryByIdsWithRelation(currentUser, ids);
      int index = 0;
      for (User user : withRelationAccounts) {
        user.setSummary(accounts.get(index).getSummary());
        index++;
      }
      mv.addObject("accounts", withRelationAccounts);
    }
    if (page == 1) {
      mv.addObject("total", hlUsers.getTotalPages());
      mv.addObject("q", q);
      mv.addObject("navMap", navMap);
      mv.addObject("searchMode", "users");
    } else {
      mv.setViewName("/account/temp");
    }
    return mv;
  }

  private <T> List<T> getHighlightContent(HighlightPage<T> highlightPage) {
    highlightPage.getHighlighted()
        .forEach(highlighted -> highlighted.getHighlights().forEach(highlight -> {
          String formatterString =
              highlight.getSnipplets().stream().collect(Collectors.joining(", "));
          Field field = ReflectionUtils.findField(highlighted.getEntity().getClass(),
              highlight.getField().getName());
          ReflectionUtils.makeAccessible(field);
          ReflectionUtils.setField(field, highlighted.getEntity(), formatterString);
        }));
    return highlightPage.getContent();
  }
}
