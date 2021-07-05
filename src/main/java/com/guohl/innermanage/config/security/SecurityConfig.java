package com.guohl.innermanage.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailHandler loginFailHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义用户验证和加密方式
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                //          .loginPage("/login.html") //自定义登录页面
//                .loginProcessingUrl("/login") //自定义登录接口地址
//                .successHandler(loginSuccessHandler)
                .and()
                // 定义哪些URL需要被保护、哪些不需要被保护
                .authorizeRequests().antMatchers("/login").permitAll() //不需要保护的URL
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated()
                .and()
                .logout().logoutSuccessUrl("/login").permitAll() // 登出
                .and()
                .csrf().disable();
        //配置自定义过滤器 增加post json 支持
        http.addFilterAt(UserAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    private UserAuthenticationFilter UserAuthenticationFilterBean() throws Exception {
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter();
        userAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
        userAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        userAuthenticationFilter.setAuthenticationFailureHandler(loginFailHandler);
        return userAuthenticationFilter;
    }

}
