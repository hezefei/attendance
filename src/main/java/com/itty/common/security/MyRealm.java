package com.itty.common.security;


import com.itty.user.entity.Permission;
import com.itty.user.entity.Role;
import com.itty.user.entity.User;
import com.itty.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/7/2.
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author hezefei
     * @Description 授权
     * @Date 15:11 2018/9/14
     * @Param [principalCollection]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUserName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (Permission permission : role.getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }


    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author hezefei
     * @Description 认证 登录
     * @Date 15:12 2018/9/14
     * @Param [authenticationToken]
     **/

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToke.getUsername();
        User user = userService.findUserByUserName(username);
        if (user == null) {
            return null;
        } else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            SecurityUtils.getSubject().getSession().setAttribute("userinfo", user);
            return info;
        }

    }
}
