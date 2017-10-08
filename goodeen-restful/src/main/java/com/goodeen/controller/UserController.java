package com.goodeen.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.goodeen.db.service.UserService;
import com.goodeen.enums.State;
import com.goodeen.model.User;
import com.goodeen.service.MailService;

@Controller
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private MailService mailService;

  @PostMapping(value = "/create")
  public RedirectView create(User user, RedirectAttributes ra) {
    user.setKey(new Md5Hash("key", user.getEmail()).toString());
    user.setPassword(new Md5Hash(user.getPassword(), user.getEmail()).toString());
    userService.create(user);
    mailService.sendActivateEmail(user);
    ra.addFlashAttribute("message", "恭喜您，注册成功。");
    ra.addFlashAttribute("email", user.getEmail());
    return new RedirectView("/login", true);
  }
  
  @ResponseBody
  @PostMapping(value = "/isPasswordRight")
  public Boolean isPasswordRight(HttpSession session, String password) {
    User currentUser = (User) session.getAttribute("currentUser");
    String md5Password = new Md5Hash(password, currentUser.getEmail()).toString();
    return currentUser.getPassword().equals(md5Password);
  }  

  @ResponseBody
  @GetMapping(value = "/email/available")
  public Boolean emailAvailable(HttpSession session, String email) {
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser != null && currentUser.getEmail().equals(email)) {
      return true;
    } else {
      return userService.getByEmail(email) == null;
    }
  }

  @ResponseBody
  @GetMapping(value = "/screenName/available")
  public Boolean screenNameAvailable(HttpSession session, String screenName) {
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser != null && currentUser.getScreenName().equals(screenName)) {
      return true;
    } else {
      return userService.screenNameAvailable(screenName);
    }
  }

  @GetMapping(value = "/activate/{key}")
  public RedirectView activate(@PathVariable("key") String key, HttpSession session,
      RedirectAttributes ra) {
    User currentUser = (User) session.getAttribute("currentUser");
    Boolean activated = userService.isActivated(key);
    if (!activated) {
      userService.activate(key);
      if (currentUser != null && currentUser.getKey().equals(key)) {
        currentUser.setState(State.ACTIVATED);
      }
    }
    ra.addFlashAttribute("alertMsg", activated ? "该用户已经激活过了。" : "已经成功激活用户。");
    return new RedirectView(currentUser == null ? "/login" : "/", true);
  }
  
  @PutMapping(value = "/resetPassword")
  public RedirectView resetPassword(User user, HttpSession session,
      RedirectAttributes ra) {
    User currentUser = (User) session.getAttribute("currentUser");
    String password = user.getPassword();
    user = userService.getByForgetPasswordKey(user.getKey());
    
    // 15分钟key无效
    if(user!=null && ((Long.parseLong(user.getPassword())+15*60*1000)>new Date().getTime())) {
      String md5Password = new Md5Hash(password, user.getEmail()).toString();
      user.setKey(new Md5Hash("key", user.getEmail()).toString());
      user.setPassword(md5Password);
      userService.updateAccount(user);
      if(currentUser != null && currentUser.getEmail().equals(user.getEmail())){
        currentUser.setPassword(user.getPassword());
        currentUser.setKey(user.getKey());
      }
      ra.addFlashAttribute("alertMsg", "密码已经重置。");
    } else {
      ra.addFlashAttribute("alertMsg", "链接过期，请重新发送忘记密码请求。");
    }
    return new RedirectView(currentUser == null ? "/login" : "/", true);
  }

  @ResponseBody
  @PostMapping(value = "/follow")
  public Integer follow(HttpSession session, User user) {
    User currentUser = (User) session.getAttribute("currentUser");
    return userService.follow(user, currentUser);
  }

  @ResponseBody
  @PostMapping(value = "/unFollow")
  public Integer unFollow(HttpSession session, User user) {
    User currentUser = (User) session.getAttribute("currentUser");
    return userService.unFollow(user, currentUser);
  }
}
