package com.goodeen.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfiguration {
  public static final String PREMISSION_STRING = "perms[\"%s\"]";

  // @Autowired
  // private UserService userService;

  @Bean
  public CredentialsMatcher credentialsMatcher() {
    HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
    credentialsMatcher.setHashIterations(1);
    return credentialsMatcher;
  }

  @Bean
  public Realm realm(CredentialsMatcher credentialsMatcher) {
    ShiroRealm realm = new ShiroRealm();
    realm.setCredentialsMatcher(credentialsMatcher);
    return realm;
  }

  @Bean
  public CacheManager cacheManager() {
    return new MemoryConstrainedCacheManager();
  }

  @Bean
  public SimpleCookie rememberMeCookie() {
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    simpleCookie.setMaxAge(7*12*3600);
    return simpleCookie;
  }

  @Bean
  public CookieRememberMeManager rememberMeManager(Cookie rememberMeCookie) {
    CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
    rememberMeManager.setCookie(rememberMeCookie);
    rememberMeManager.setCipherKey(Base64.decode("wGiHplamyXlVB11UXWol8g=="));
    return rememberMeManager;
  }

  @Bean
  public SecurityManager securityManager(Realm realm, CacheManager cacheManager, CookieRememberMeManager rememberMeManager) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(realm);
    securityManager.setCacheManager(cacheManager);
    securityManager.setRememberMeManager(rememberMeManager);
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setLoginUrl("/login");
    shiroFilterFactoryBean.setSuccessUrl("/");
    shiroFilterFactoryBean.setUnauthorizedUrl("/error/access-denied");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
    return shiroFilterFactoryBean;
  }

  public Map<String, String> getFilterChainDefinitionMap() {
    Map<String, String> permissionsMap = new LinkedHashMap<String, String>() {
      private static final long serialVersionUID = -3099948755127100301L;
      {
        put("/favicon.ico", "anon");
        put("/data/*.json", "anon");
        put("/**/*.css", "anon");
        put("/**/*.js", "anon");
        put("/**/*.swf", "anon");
        put("/**/*.png", "anon");
        put("/**/*.gif", "anon");
        put("/**/*.jpg", "anon");
        put("/**/*.eot", "anon");
        put("/**/*.svg", "anon");
        put("/**/*.ttf", "anon");
        put("/**/*.woff", "anon");
        put("/login", "anon");
        put("/logout", "logout");
        put("/signup", "anon");
        put("/account/resendPassword", "anon");
        put("/account/forgetPassword/**", "anon");
        put("/user/create", "anon");
        put("/user/activate/**", "anon");
        put("/user/resetPassword", "anon");
        put("/user/email/available", "anon");
        put("/user/screenName/available", "anon");
        put("/settings/**", "authc");

        put("/**", "user");
      }
    };
    // 数据库方式设置权限
    // List<Permission> permissions = userService.queryAllPermissions();
    // for (Permission permission : permissions) {
    // if (!StringUtils.isEmpty(permission.getCode()) && !StringUtils.isEmpty(permission.getUrl()))
    // {
    // permissionsMap.put(permission.getUrl(),
    // String.format(PREMISSION_STRING, permission.getCode()));
    // }
    // }
    return permissionsMap;
  }

  public static void main(String[] args) {
    System.out.println("test@163.com password:" + new Md5Hash("666666", "test@163.com"));
    System.err.println("test@163.com key:" + new Md5Hash("key", "test@163.com"));
    System.out.println("test1@163.com password:" + new Md5Hash("666666", "test1@163.com"));
    System.err.println("test1@163.com key:" + new Md5Hash("key", "test1@163.com"));
    System.err.println(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
  }
}
