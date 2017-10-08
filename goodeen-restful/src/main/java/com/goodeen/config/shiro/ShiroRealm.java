package com.goodeen.config.shiro;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.goodeen.db.service.UserService;
import com.goodeen.model.User;

public class ShiroRealm extends AuthorizingRealm {
  protected final Log logger = LogFactory.getLog(getClass());

  @Autowired
  private UserService userService;
  
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    String email = (String) token.getPrincipal();
    User user = userService.getByEmail(email);
    if (user != null) {
      SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(token.getPrincipal(),
          user.getPassword(), ByteSource.Util.bytes(user.getEmail()), getName());
      return authcInfo;
    }
    return null;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String username = (String) principals.getPrimaryPrincipal();
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    List<String> roles = userService.queryRoleCodes(username);
    authorizationInfo.addRoles(roles);
    List<String> permissions = userService.queryPermissionCodes(username);
    authorizationInfo.addStringPermissions(permissions);
    return authorizationInfo;
  }
}
