package com.myblog.version3.controller.Public;

import com.myblog.version3.entity.Form.Login;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.userMapper;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Controller
@Api(tags = "登录控制")
public class LoginController {

    @Autowired
    userMapper mapper;

    private Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @RequestMapping(value = "/userLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public String UserLogin(Login user, ModelMap modelMap, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassword(), false);
        Subject subject = SecurityUtils.getSubject();
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        logger.info("原访问地址为：" + savedRequest);
        logger.info("用户的电话号码为：" + user.getPhone());
        logger.info("用户的密码为:" + user.getPassword());
        logger.info("是否记住密码:" + user.getRememberMe());
        try {
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("isLogIn" ,true);
            User user1 = mapper.getByPhone(user.getPhone());
            session.setAttribute("User" ,user1);
            if (savedRequest != null) {
                return "redirect:/" + savedRequest;
            } else {
                return "redirect:/";
            }
        } catch (IncorrectCredentialsException e) {
            modelMap.addAttribute("message", "登录密码错误");
            return "redirect:/login";
        } catch (ExcessiveAttemptsException e) {
            modelMap.addAttribute("message", "登录失败次数过多");
            return "redirect:/login";
        } catch (LockedAccountException e) {
            modelMap.addAttribute("message", "账号已被锁定");
            return "redirect:/login";
        } catch (DisabledAccountException e) {
            modelMap.addAttribute("message", "账号已被禁用");
            return "redirect:/login";
        } catch (ExpiredCredentialsException e) {
            modelMap.addAttribute("message", "账号已过期");
            return "redirect:/login";
        } catch (AuthenticationException e) {
            modelMap.addAttribute("message", "账号不存在,快去注册一个吧！");
            return "redirect:/login";
        }
    }

    @RequestMapping("/login")
    @ApiIgnore
    public String Login(ModelMap modelMap) {
        modelMap.addAttribute("message", "输入正确的手机号和密码即可登录本网站");
        return "public/login";
    }
}
