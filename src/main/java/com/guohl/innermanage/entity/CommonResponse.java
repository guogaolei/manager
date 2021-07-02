package com.guohl.innermanage.entity;

import java.util.HashMap;
import java.util.Map;

public class CommonResponse {
    private String code;
    private String msg;
    private Map result=new HashMap<String,Object>();
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }
}
