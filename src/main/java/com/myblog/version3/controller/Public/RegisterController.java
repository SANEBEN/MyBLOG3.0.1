package com.myblog.version3.controller.Public;

import com.myblog.version3.Tools.Random;
import com.myblog.version3.Tools.eMessage;
import com.myblog.version3.entity.Form.Register;
import com.myblog.version3.entity.Message;
import com.myblog.version3.entity.User;
import com.myblog.version3.mapper.messageMapper;
import com.myblog.version3.mapper.userMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Api(tags = "注册控制")
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    userMapper usermapper;

    @Autowired
    messageMapper messageMapper;

    @RequestMapping(value = "/register" ,method = RequestMethod.GET)
    @ApiIgnore
    public ModelAndView register(){
        ModelAndView view = new ModelAndView();
        view.setViewName("public/register");
        return view;
    }

    @RequestMapping(value = "/userRegister" , method = {RequestMethod.GET ,RequestMethod.POST})
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(value = "注册用的电话号码" ,name = "phone" ,dataType = "String" ,paramType = "query" ,required = true),
            @ApiImplicitParam(value = "用户昵称" ,name = "userName" ,dataType = "String" ,paramType = "query" ,required = true),
            @ApiImplicitParam(value = "密码" ,name = "Rpassword" ,dataType = "String" ,paramType = "query" ,required = true),
            @ApiImplicitParam(value = "确认密码" ,name = "Lpassword" ,dataType = "String" ,paramType = "query" ,required = true),
            @ApiImplicitParam(value = "验证码" ,name = "verification" ,dataType = "String" ,paramType = "query" ,required = true)
    })
    public String userRegister(@Valid Register user , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> list=bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for(FieldError error : list){
                builder.append(error.getDefaultMessage()+"&");
            }
            return builder.toString();
        }else {
            if (usermapper.DuplicateChecking(user.getPhone()) == 0) {
                if(user.getRpassword().equals(user.getLpassword())){
                    Subject subject = SecurityUtils.getSubject();
                    Session session =subject.getSession();
                    if(session.getAttribute("verification") != null){
                        String verification = (String)session.getAttribute("verification");
                        String[] data = verification.split("&");
                        if(data[1].equals(user.getPhone())){
                            if(data[0].equals(user.getVerification())){
                                User register = new User();
                                register.setID(Random.getUUID().substring(0,8));
                                register.setPhone(user.getPhone());
                                register.setPassword(user.getRpassword());
                                Message message = new Message();
                                message.setCreatedTime(new Date());
                                message.setUid(register.getID());
                                message.setPhone(user.getPhone());
                                message.setUserName(user.getUserName());
                                message.setID(Random.getUUID().substring(0,8));
                                usermapper.insert(register);
                                messageMapper.insert(message);
                                return "注册成功";
                            }else {
                                return "验证码输入错误！";
                            }
                        }else {
                            return "发送验证码后电话号码不能随意更改，请刷新界面后重试";
                        }
                    }else {
                        return "验证码已过期，请重新发送";
                    }
                }else {
                    return "两次输入的密码不同，请重新输入";
                }
            }else {
                return "该手机号已经注册过了！！！";
            }
        }
    }

    @RequestMapping(value = "/sendVerification" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ResponseBody
    public String sendVerification(String phone){
        Pattern pattern = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
        Matcher matcher = pattern.matcher(phone);
        if(matcher.matches()) {
            if(usermapper.DuplicateChecking(phone) == 0) {
                String verification = eMessage.sendVerification(phone);
                if (verification != null) {
                    Subject subject = SecurityUtils.getSubject();
                    Session session = subject.getSession();
                    session.setTimeout(60000);
                    session.setAttribute("verification", verification + "&" + phone);
                    return "验证码已经发送到你的手机，有效期为60秒";
                } else {
                    return "验证码发送失败，请稍后再试";
                }
            }else {
                return "该手机号已经注册过了";
            }
        }else {
            return "请输入正确的手机号";
        }
    }
}
