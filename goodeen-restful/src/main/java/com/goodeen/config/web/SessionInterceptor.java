package com.goodeen.config.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.goodeen.db.service.UserService;
import com.goodeen.model.User;

public class SessionInterceptor implements HandlerInterceptor {
  private final Logger logger = Logger.getLogger(SessionInterceptor.class);
  @Autowired
  private UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)
      throws Exception {
    if(request.getSession().getAttribute("currentUser")!=null) {
      return true;
    } else {
      Subject currentUser = SecurityUtils.getSubject();
      if (!currentUser.isAuthenticated() && currentUser.isRemembered()) {
        User user = userService.getByEmail(currentUser.getPrincipals().toString());
        Session session = currentUser.getSession();
        session.setAttribute("currentUser", user);
        logger.info(currentUser.getPrincipals() + "通过RememberMe重新获得session信息。");
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView)
      throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
  }
}
