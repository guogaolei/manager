package com.guohl.innermanage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohl.innermanage.entity.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        logger.info("登录成功，用户："+authentication.getName());
        CommonResponse result=new CommonResponse();
        result.setMsg("success");
        result.setCode("200");
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}