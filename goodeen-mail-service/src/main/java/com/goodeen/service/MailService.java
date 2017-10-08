package com.goodeen.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.goodeen.model.User;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("mailService")
public class MailService {
  @Autowired
  private Configuration freeMarkerConfiguration;

  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.username:ypek@163.com}")
  private String from;

  private String getMailTextbyTemplate(Map param, String template) {
    String htmlText = null;
    try {
      Template tpl = freeMarkerConfiguration.getTemplate(template);
      htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, param);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return htmlText;
  }

  @Async
  public void sendActivateEmail(User user) {
    MimeMessage msg = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
      helper.setFrom(from);
      helper.setTo(user.getEmail());
      helper.setSubject("请激活您的goodeen账户！");
      String template = "mail/activate.ftl";
      Map paramMap = new HashMap();
      paramMap.put("user", user);
      String htmlText = getMailTextbyTemplate(paramMap, template);
      helper.setText(htmlText, true);
      mailSender.send(msg);
      System.out.println("邮件发送成功！");
    } catch (MessagingException e) {
      e.printStackTrace();
      System.out.println("邮件发送失败！，异常信息：" + e.getMessage());
    }
  }
  
  @Async
  public void sendForgetPasswordEmail(User user) {
    MimeMessage msg = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");
      helper.setFrom(from);
      helper.setTo(user.getEmail());
      helper.setSubject("Goodeen账户密码重置！");
      String template = "mail/forget-password.ftl";
      Map paramMap = new HashMap();
      paramMap.put("user", user);
      String htmlText = getMailTextbyTemplate(paramMap, template);
      helper.setText(htmlText, true);
      mailSender.send(msg);
      System.out.println("邮件发送成功！");
    } catch (MessagingException e) {
      e.printStackTrace();
      System.out.println("邮件发送失败！，异常信息：" + e.getMessage());
    }
  }
}
