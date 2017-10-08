package com.goodeen.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.goodeen.db.service.UserService;
import com.goodeen.model.User;
import com.goodeen.service.MailService;

@Controller
@RequestMapping("/account")
public class AccountController {
  @Autowired
	private UserService userService;

  @Autowired
	private MailService mailService;

	@ResponseBody
	@GetMapping(value = "/resendPassword")
	public Boolean resendPassword(User user) {
	  if(user != null && user.getEmail()!=null){
	    user = userService.getByEmail(user.getEmail());
	    if(user != null) {
	      String time = new Date().getTime() + "";
	      String key =  new Md5Hash("key", time + user.getEmail()).toString();
	      user.setKey(key);
	      user.setPassword(time);
	      userService.createForgetPasswordRequest(user);
	      mailService.sendForgetPasswordEmail(user);
	      return true;
	    }
	  }
	  return false;
	}
	
	@GetMapping(value = "/forgetPassword/{key}")
  public ModelAndView forgetPassword(@PathVariable("key") String key, HttpSession session) {
    return new ModelAndView("/account/forget-password", "key", key);
  }	
	
	@ResponseBody
	@GetMapping(value = "/resendActivateEmail")
	public Boolean resendActivateEmail(HttpSession session) {
	  User currentUser = (User) session.getAttribute("currentUser");
	  if (currentUser != null) {
	    mailService.sendActivateEmail(currentUser);
	    return true;
	  } else {
	    return false;
	  }
	}
	
	@GetMapping(value = "/confirmEmailReset")
	public ModelAndView confirmEmailReset(HttpSession session) {
		ModelAndView mv = new ModelAndView("/account/confirm-email-reset");
		return mv;
	}
}
