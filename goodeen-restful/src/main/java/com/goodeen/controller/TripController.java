package com.goodeen.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.goodeen.db.service.TripService;
import com.goodeen.enums.CommentLevel;
import com.goodeen.model.Comment;
import com.goodeen.model.Trip;
import com.goodeen.model.User;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

@Controller
@RequestMapping("/trip")
public class TripController {
  @Autowired
  private TripService tripService;

  @GetMapping(value = "/add")
  public ModelAndView add() throws TemplateModelException {
    ModelAndView mv = new ModelAndView("/trip/creator");
    TemplateHashModel enumModels =
        new BeansWrapperBuilder(Configuration.getVersion()).build().getEnumModels();
    mv.addObject("transportModel", enumModels.get("com.goodeen.enums.Transport"));
    mv.addObject("tripModeModel", enumModels.get("com.goodeen.enums.TripMode"));
    return mv;
  }

  @GetMapping(value = "/{id:[\\d]+}")
  public ModelAndView detail(HttpSession session, @PathVariable Integer id)
      throws TemplateModelException {
    ModelAndView mv = new ModelAndView("/trip/detail");
    User currentUser = (User) session.getAttribute("currentUser");
    Trip trip = tripService.getTripWithCommentsMap(id, currentUser);
    mv.addObject("trip", trip);
    return mv;
  }

  @GetMapping(value = "/update")
  public ModelAndView update() {
    return new ModelAndView("update");
  }

  @PostMapping
  public RedirectView create(HttpSession session, Trip trip) {
    User currentUser = (User) session.getAttribute("currentUser");
    trip.setCreator(currentUser.getId());
    tripService.create(trip);
    return new RedirectView("/" + currentUser.getScreenName(), true);
  }

  @ResponseBody
  @PostMapping(value = "/plus")
  public Integer plus(HttpSession session, Trip trip) {
    User currentUser = (User) session.getAttribute("currentUser");
    return tripService.plus(trip, currentUser);
  }

  @ResponseBody
  @PostMapping(value = "/unPlus")
  public Integer unPlus(HttpSession session, Trip trip) {
    User currentUser = (User) session.getAttribute("currentUser");
    return tripService.unPlus(trip, currentUser);
  }

  @ResponseBody
  @PostMapping(value = "/favorite")
  public Integer favorite(HttpSession session, Trip trip) {
    User currentUser = (User) session.getAttribute("currentUser");
    return tripService.favorite(trip, currentUser);
  }

  @ResponseBody
  @PostMapping(value = "/unFavorite")
  public Integer unFavorite(HttpSession session, Trip trip) {
    User currentUser = (User) session.getAttribute("currentUser");
    return tripService.unFavorite(trip, currentUser);
  }
  
  @ResponseBody
  @PostMapping(value = "/comment/create")
  public Map createComment(HttpSession session, Comment<Trip> comment,Integer landLordId) {
    Trip landLord = new Trip();
    landLord.setId(landLordId);
    comment.setLandLord(landLord);
    User currentUser = (User) session.getAttribute("currentUser");
    comment.setCreator(currentUser.getId());
    tripService.createComment(comment);
    Map map = new HashMap();
    map.put("id", comment.getId());
    map.put("sId", comment.getCommentLevel()==CommentLevel.STOREYLORD ? comment.getId() : comment.getStoreyLord().getId());
    return map;
  }  
}
