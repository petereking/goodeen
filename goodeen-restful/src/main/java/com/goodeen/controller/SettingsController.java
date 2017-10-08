package com.goodeen.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.goodeen.db.service.UserService;
import com.goodeen.model.Theme;
import com.goodeen.model.User;

@Controller
@RequestMapping("/settings")
public class SettingsController {
  @Autowired
  private UserService userService;

  private static final Map<String, String> navMap = new LinkedHashMap<String, String>() {
    private static final long serialVersionUID = 3305733563221005241L;
    {
      put("account", "账号");
      put("security", "安全与隐私");
      put("password", "密码");
      put("notifications", "邮件通知");
      put("profile", "个人资料 ");
      put("design", "主题");
    }
  };

  @GetMapping(value = "/account")
  public ModelAndView account(HttpSession session) {
    return new ModelAndView("/settings/account", "navMap", navMap);
  }

  @PutMapping(value = "/account/update")
  public RedirectView updateAccount(HttpSession session, User user, RedirectAttributes ra) {
    User currentUser = (User) session.getAttribute("currentUser");
    user.setId(currentUser.getId());
    String md5Password = new Md5Hash(user.getPassword(), currentUser.getEmail()).toString();
    if (currentUser.getPassword().equals(md5Password)) {
      if (!user.getEmail().equals(currentUser.getEmail())) {
        user.setKey(new Md5Hash("key", user.getEmail()).toString());
        user.setPassword(new Md5Hash(user.getPassword(), user.getEmail()).toString());
        currentUser.setEmail(user.getEmail());
      } else {
        user.setEmail(null); // email为空则只修改用户名
      }
      userService.updateAccount(user);
      session.setAttribute("currentUser", userService.getByEmail(currentUser.getEmail()));
      ra.addFlashAttribute("message", "保存设置成功!");
    } else {
      ra.addFlashAttribute("message", "你当前输入的密码不正确，请输入其他密码。");
    }
    return new RedirectView("../account");
  }
  
  @GetMapping(value = "/security")
  public ModelAndView security(HttpSession session) {
    return new ModelAndView("/settings/security", "navMap", navMap);
  }

  @ResponseBody
  @PutMapping(value = "/security/update")
  public String updateSecurity(HttpSession session, User user) {
    userService.updateSecurity(user);
    return "SUCCESS";
  }

  @GetMapping(value = "/password")
  public ModelAndView password() {
    return new ModelAndView("/settings/password", "navMap", navMap);
  }

  @PutMapping(value = "/password/update")
  public RedirectView updatePassword(HttpSession session, User user, String currentPassword, RedirectAttributes ra) {
    User currentUser = (User) session.getAttribute("currentUser");
    String md5Password = new Md5Hash(currentPassword, currentUser.getEmail()).toString();
    if (currentUser.getPassword().equals(md5Password)) {
      String newPassword = new Md5Hash(user.getPassword(), currentUser.getEmail()).toString();
      currentUser.setPassword(newPassword);
      currentUser.setKey(new Md5Hash("key", user.getEmail()).toString());
      userService.updateAccount(user);
      ra.addFlashAttribute("message", "密码修改成功!");
    } else {
      ra.addFlashAttribute("message", "你当前输入的密码不正确，请输入其他密码。");
    }
    return new RedirectView("../password");
  }

  @GetMapping(value = "/notifications")
  public ModelAndView notifications(HttpSession session) {
    return new ModelAndView("/settings/notifications", "navMap", navMap);
  }

  @ResponseBody
  @PutMapping(value = "/notifications/update")
  public String updateNotifications(HttpSession session, User user) {
    userService.updateNotifications(user);
    return "SUCCESS";
  }

  @GetMapping(value = "/profile")
  public ModelAndView profile(HttpSession session) {
    return new ModelAndView("/settings/profile", "navMap", navMap);
  }

  @PutMapping(value = "/profile/update")
  public RedirectView updateProfile(User user, HttpSession session, RedirectAttributes ra) {
    User currentUser = (User) session.getAttribute("currentUser");
    user.setId(currentUser.getId());
    userService.updateProfile(user);
    session.setAttribute("currentUser", userService.getByEmail(currentUser.getEmail()));
    ra.addFlashAttribute("message", "非常感谢，你的设置已保存。");
    return new RedirectView("../profile");
  }

  @GetMapping(value = "/design")
  public ModelAndView design(HttpSession session) {
    List<Theme> themeList = new ArrayList<Theme>() {
      private static final long serialVersionUID = -7661912638503734217L;
      {
        add(new Theme(1, "bg.png", false, "#C6E2EE", "#1F98C7"));
        add(new Theme(2, "bg.gif", false, "#C0DEED", "#0084B4"));
        add(new Theme(3, "bg.gif", false, "#EDECE9", "#088253"));
        add(new Theme(4, "bg.gif", false, "#0099B9", "#0099B9"));
        add(new Theme(5, "bg.gif", false, "#352726", "#D02B55"));
        add(new Theme(6, "bg.gif", false, "#709397", "#FF3300"));
        add(new Theme(7, "bg.gif", false, "#EBEBEB", "#990000"));
        add(new Theme(8, "bg.gif", false, "#8B542B", "#9D582E"));
        add(new Theme(9, "bg.gif", false, "#1A1B1F", "#2FC2EF"));
        add(new Theme(10, "bg.gif", true, "#642D8B", "#FF0000"));
        add(new Theme(11, "bg.gif", true, "#FF6699", "#B40B43"));
        add(new Theme(12, "bg.gif", false, "#BADFCD", "#FF0000"));
        add(new Theme(13, "bg.gif", false, "#B2DFDA", "#93A644"));
        add(new Theme(14, "bg.gif", true, "#131516", "#009999"));
        add(new Theme(15, "bg.png", false, "#022330", "#0084B4"));
        add(new Theme(16, "bg.gif", false, "#9AE4E8", "#0084B4"));
        add(new Theme(17, "bg.gif", false, "#DBE9ED", "#CC3366"));
        add(new Theme(18, "bg.gif", false, "#ACDED6", "#038543"));
        add(new Theme(19, "bg.gif", false, "#FFF04D", "#0099CC"));
      }
    };
    ModelAndView mv = new ModelAndView("/settings/design");
    mv.addObject("navMap", navMap);
    mv.addObject("themeList", themeList);
    return mv;
  }

  @PutMapping(value = "/design/update")
  public RedirectView updateDesign(User user, MultipartFile backgroundImageFile,
      HttpSession session, HttpServletRequest request, RedirectAttributes ra)
      throws IllegalStateException, IOException {
    User currentUser = (User) session.getAttribute("currentUser");
    user.setId(currentUser.getId());
    if (backgroundImageFile != null && !backgroundImageFile.isEmpty()) {
      String dirPath = request.getSession().getServletContext()
          .getRealPath("/attachment/images/themes/custom/" + currentUser.getId());
      File dir = new File(dirPath);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      String fileName = backgroundImageFile.getOriginalFilename();
      String suffix = fileName.substring(fileName.lastIndexOf("."));
      fileName =
          dirPath + File.separator + UUID.randomUUID().toString().replaceAll("-", "") + suffix;
      user.getTheme().setBackgroundImage(
          fileName.substring(fileName.indexOf("attachment") - 1).replace("\\", "/"));
      backgroundImageFile.transferTo(new File(fileName));
    }
    userService.updateDesign(user);
    currentUser.setTheme(user.getTheme());
    session.setAttribute("currentUser", currentUser);
    ra.addFlashAttribute("message", "你的主题设置已保存。");
    return new RedirectView("../design");
  }
}
