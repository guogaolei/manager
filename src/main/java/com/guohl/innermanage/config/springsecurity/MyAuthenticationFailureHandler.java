package com.guohl.innermanage.config.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohl.innermanage.entity.CommonResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security 登录失败后返回一串json
 */
@Component("MyAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        CommonResponse result=new CommonResponse();
        if(exception instanceof AccountExpiredException){
            result.setMsg("账号过期");
            result.setCode("1");
        }else if(exception instanceof BadCredentialsException){
            result.setMsg("密码错误");
            result.setCode("1");
        }else if(exception instanceof LockedException){
            result.setMsg("账号被锁");
            result.setCode("1");
        }else if(exception instanceof DisabledException){
            result.setMsg("账号不可用");
            result.setCode("1");
        }else if(exception instanceof InternalAuthenticationServiceException){
            result.setMsg("用户不存在");
            result.setCode("1");
        }else{
            result.setMsg("其他错误");
            result.setCode("1");
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
