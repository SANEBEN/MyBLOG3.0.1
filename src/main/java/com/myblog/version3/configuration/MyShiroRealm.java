package com.myblog.version3.configuration;

import com.myblog.version3.entity.Message;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.messageMapper;
import com.myblog.version3.mapper.userMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    com.myblog.version3.mapper.userMapper userMapper;

    @Autowired
    messageMapper messageMapper;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        Message message = messageMapper.getByUid(user.getID());
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRole(message.getRole());
        return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String phone = token.getUsername();
        User user = userMapper.getByPhone(phone);//根据登陆名account从库中查询user对象
        if(user==null){throw new AuthenticationException("用户不存在");}
        //进行认证，将正确数据给shiro处理
        //密码不用自己比对，AuthenticationInfo认证信息对象，一个接口，new他的实现类对象SimpleAuthenticationInfo
        /*	第一个参数随便放，可以放user对象，程序可在任意位置获取 放入的对象
         * 第二个参数必须放密码，
         * 第三个参数放 当前realm的名字，因为可能有多个realm*/
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        //AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user,user.getPassword(),new MySimpleByteSource(account), this.getName());
        //清之前的授权信息
        super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
        SecurityUtils.getSubject().getSession().setAttribute("login", user);
        return authcInfo;//返回给安全管理器，securityManager，由securityManager比对数据库查询出的密码和页面提交的密码
        //如果有问题，向上抛异常，一直抛到控制器
    }
}
