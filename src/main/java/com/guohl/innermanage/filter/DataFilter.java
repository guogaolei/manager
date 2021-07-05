package com.guohl.innermanage.filter;

import com.google.gson.Gson;
import com.guohl.innermanage.utils.RSAEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class DataFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    RSAEncryptUtil RSAEncryptUtil;
    @Value("${ras.activate}")
    boolean RasFlag;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        String method = httpServletRequest.getMethod();
        String url = httpServletRequest.getRequestURI();

        //如果是访问css/js/png不处理
        if(url.contains(".js")||url.contains(".css")||url.contains(".png")||url.contains(".html")||url.contains(".woff2")
                ||url.contains(".jpeg") ||url.contains(".ico") || url.contains(".gif") ){
            chain.doFilter(request,response);
        }else {
            ResponseWrapper responseWrapper=new ResponseWrapper((HttpServletResponse)response);
            //数据解析成功标志,如果不上送数据就不解析，
            boolean dataParse=false;
            if("GET".equals(method)){
                //如果是get请求，不解析数据
                dataParse=true;
                String queryString = httpServletRequest.getQueryString();
                logger.info("DateFilter:>url:"+url+",------- requestParam: "+queryString);
                chain.doFilter(request,response);
            }
            if ("POST".equals(method)){
                RequestWrapper requestWrapper=new RequestWrapper((HttpServletRequest)request,RSAEncryptUtil,RasFlag);
                BufferedReader reader = requestWrapper.getReader();
                String line=null;
                StringBuffer sb=new StringBuffer();
                while((line=reader.readLine())!=null){
                    sb.append(line);
                }
                logger.info("DateFilter:>url:"+url+",------- requestParam: "+sb.toString());
                dataParse=parseData(sb.toString());
                if(!dataParse){
                    logger.info("上送数据解析失败");
                }else {
                    chain.doFilter(requestWrapper,responseWrapper);
                }
            String result="";
            if(!dataParse){
                result="{\"code\":\"100\",\"msg\":\"数据解析失败\"}";
            }else {
                result= new String(responseWrapper.getResponseData(),"UTF-8");
            }
            response.setContentLength(-1);
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
            logger.info("DateFilter:>url:"+url+",------- 返回数据: "+result);
            }
        }

    }

    private boolean parseData(String toString) {
        if(StringUtils.isEmpty(toString)){
            return false;
        }
        Gson gson=new Gson();
        try{
        gson.fromJson(toString, Map.class);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void destroy() {

    }
}
