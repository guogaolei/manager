package com.guohl.innermanage.config.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guohl.innermanage.entity.CommonResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security 登录成功后返回一串json
 */
@Component("MyAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String currentUser = authentication.getName();
        logger.info("用户"+currentUser+"登录成功");
        CommonResponse result=new CommonResponse();
        result.setCode("200");
        result.setMsg("success");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}

