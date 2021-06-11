package com.guohl.innermanage.config;

import com.guohl.innermanage.interceptor.StaticInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 不拦截静态路径
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    public static List<String> EXCLUDE_PATH= Arrays.asList("/","/inner","/inner/","/js","/css","/image");

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new StaticInterceptor()).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATH);
    }
}
