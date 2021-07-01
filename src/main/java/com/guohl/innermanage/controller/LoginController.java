package com.guohl.innermanage.controller;

import com.guohl.innermanage.dao.UserDao;
import com.guohl.innermanage.entity.CommonResponse;
import com.guohl.innermanage.entity.LoginParamRequest;
import com.guohl.innermanage.entity.LoginParamResponse;
import com.guohl.innermanage.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDao userDao;
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public LoginParamResponse login(@RequestBody LoginParamRequest request, HttpServletRequest httprequest){

        LoginParamResponse response=new LoginParamResponse();
        String username = request.getUsername();
        String password = request.getPassword();
        logger.info("username="+username+",password="+password);
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            response.setCode("100");
            response.setMsg("请输入登录账号及密码");
            return response;
        }
        UserEntity UserEntity = userDao.getUser(username);

        if(null == UserEntity || !UserEntity.getUserName().equals(username) || !UserEntity.getPassWord().equals(password)){
            response.setCode("100");
            response.setMsg("用户名密码输入有误，请重新输入");
            return response;
        }
        httprequest.getSession().setAttribute("login","true");
        response.setCode("200");
        response.setMsg("登陆成功");
        return response;

    }

    //登出
    @RequestMapping(value="loginout",method = {RequestMethod.GET})
    @ResponseBody
    public CommonResponse loginout(HttpServletRequest request){
        CommonResponse response=new CommonResponse();
        request.getSession().removeAttribute("login");
        response.setCode("200");
        response.setMsg("交易成功");
        return response;
    }


}
