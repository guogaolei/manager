package com.guohl.innermanage.controller;

import com.guohl.innermanage.dao.UserDao;
import com.guohl.innermanage.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(HttpServletRequest request){
        Object login = request.getSession().getAttribute("login");
        if(null == login){
            return "login";
        }
        return "index";
    }
}
