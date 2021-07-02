package com.guohl.innermanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Demo {

    @RequestMapping("user/getData")
    @ResponseBody
    public String get1(){
        return "ok";
    }


    @RequestMapping("guest/getData")
    @ResponseBody
    public String get2(){
        return "ok";
    }
    @RequestMapping(value="authenticated/getData",method = RequestMethod.POST)
    @ResponseBody
    public String get3(){
        return "ok";
    }
    @RequestMapping("permission1/getData")
    @ResponseBody
    public String get4(){
        return "ok";
    }

    @RequestMapping("admin/getData")
    @ResponseBody
    public String get5(){
        return "ok";
    }

}
