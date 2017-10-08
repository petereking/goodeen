package com.goodeen.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.goodeen.db.service.UserService;
import com.goodeen.exception.NotFoundException;
import com.goodeen.model.User;

@Controller
public class IndexController {
  @Autowired
  private UserService userService;


  private static final Map<String, String> navMap = new LinkedHashMap<String, String>() {
    private static final long serialVersionUID = -2412876424347314255L;
    {
      put("trips", "行程");
      put("following", "正在关注");
      put("followers", "关注者");
    }
  };

  @GetMapping(value = "/")
  public ModelAndView index(@RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    User currentUser = (User) session.getAttribute("currentUser");
    Pageable pageable = new PageRequest(page-1, 3, new Sort(Sort.Direction.DESC, "id"));
    ModelAndView mv = new ModelAndView("index");
    mv.addObject("trips", userService.followingTrips(pageable, currentUser));
    mv.addObject("user", currentUser);
    mv.addObject("accounts", userService.queryLastestUsers(currentUser));
    if (page == 1) {  
      mv.addObject("total", Math.ceil((double) pageable.getPageSize()/3));
      mv.addObject("navMap", navMap);
    } else {
      mv.setViewName("/trip/temp");
    }
    return mv;
  }  

  @GetMapping(value = "/upload")
  public ModelAndView upload() {
    return new ModelAndView("upload");
  }

  @GetMapping(value = "/login")
  public ModelAndView login(RedirectAttributes ra) {
    ra.getFlashAttributes();
    return new ModelAndView("login");
  }

  @PostMapping(value = "/login")
  public RedirectView doLogin(HttpSession session, ServletRequest request, RedirectAttributes ra,
      String name, String password, boolean rememberMe) {
    try {
      UsernamePasswordToken token = new UsernamePasswordToken(name, password, rememberMe);
      Subject subject = SecurityUtils.getSubject();
      subject.login(token);
      User user = userService.getByEmail(name);
      session.setAttribute("currentUser", user);
      String lastestUrl = "";
      try {
        lastestUrl = WebUtils.getSavedRequest(request).getRequestUrl();
      } catch(NullPointerException e) {
        lastestUrl = "";
      }
      return new RedirectView(lastestUrl);
    } catch (UnknownAccountException e) {
      e.printStackTrace();
      ra.addFlashAttribute("message", "该邮箱尚未祖册！<a href=\"/signup?email=" + name + "\">现在就注册吧 »</a>");
      return new RedirectView("login");
    } catch (IncorrectCredentialsException e) {
      e.printStackTrace();
      ra.addFlashAttribute("message", "密码有误！忘记密码，想要<a href=\"/account/resend_password\">恢复密码</a>吗?");
      return new RedirectView("login");
    } catch (LockedAccountException e) {
      e.printStackTrace();
      ra.addFlashAttribute("message", "账户已被冻结！");
      return new RedirectView("login");
    } catch (AuthenticationException e) {
      e.printStackTrace();
      return new RedirectView("login");
    }
  }

  @GetMapping(value = "/signup")
  public ModelAndView signup(String email) {
    return new ModelAndView("signup", "email", email);
  }

  @GetMapping(value = "/forgetpassword")
  public ModelAndView forgetpassword() {
    return new ModelAndView("register");
  }

  @GetMapping(value = "/{screenName:[\\w]+}")
  public ModelAndView trip(@PathVariable("screenName") String screenName,
      @RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    User currentUser = (User) session.getAttribute("currentUser");
    Pageable pageable = new PageRequest(page-1, 3, new Sort(Sort.Direction.DESC, "id"));
    User user = userService.getUserWithRelation(page == 1, currentUser, screenName);
    userService.trips(pageable, user);
    ModelAndView mv = new ModelAndView("trips");
    mv.addObject("trips", user.getTrips());
    mv.addObject("user", user);
    if (page == 1) {
      mv.addObject("total", Math.ceil((double) pageable.getPageSize()/3));
      mv.addObject("navMap", navMap);
    } else {
      mv.setViewName("/trip/temp");
    }
    return mv;
  }

  @GetMapping(value = "/following")
  public ModelAndView following(@RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    return this.following(null, page, session);
  }

  @GetMapping(value = "/{screenName:[\\w]+}/following")
  public ModelAndView following(@PathVariable String screenName,
      @RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    User currentUser = (User) session.getAttribute("currentUser");
    if (screenName == null) {
      screenName = currentUser.getScreenName();
    }
    Pageable pageable = new PageRequest(page-1, 3, new Sort(Sort.Direction.DESC, "id"));
    User user = userService.getUserWithRelation(page == 1, currentUser, screenName);
    userService.following(pageable, currentUser, user);
    ModelAndView mv = new ModelAndView("following");
    mv.addObject("accounts", user.getFollowing());
    if (page == 1) {
      mv.addObject("total", Math.ceil((double) pageable.getPageSize()/3));
      mv.addObject("user", user);
      mv.addObject("navMap", navMap);
    } else {
      mv.setViewName("/account/temp");
    }
    return mv;
  }

  @GetMapping(value = "/followers")
  public ModelAndView followers(@RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    return this.followers(null, page, session);
  }

  @GetMapping(value = "/{screenName:[\\w]+}/followers")
  public ModelAndView followers(@PathVariable String screenName,
      @RequestParam(defaultValue = "1") Integer page, HttpSession session)
      throws NotFoundException {
    User currentUser = (User) session.getAttribute("currentUser");
    if (screenName == null) {
      screenName = currentUser.getScreenName();
    }
    Pageable pageable = new PageRequest(page-1, 3, new Sort(Sort.Direction.DESC, "id"));
    User user = userService.getUserWithRelation(page == 1, currentUser, screenName);
    userService.followers(pageable, currentUser, user);
    ModelAndView mv = new ModelAndView("followers");
    mv.addObject("accounts", user.getFollowers());
    if (page == 1) {
      mv.addObject("total", Math.ceil((double) pageable.getPageSize()/3));
      mv.addObject("user", user);
      mv.addObject("navMap", navMap);
    } else {
      mv.setViewName("/account/temp");
    }
    return mv;
  }
}
