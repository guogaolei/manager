package com.guohl.innermanage.filter;

import com.guohl.innermanage.utils.RSAEncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

public class RequestWrapper extends HttpServletRequestWrapper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    RSAEncryptUtil RSAEncryptUtil;
    boolean RasFlag=false;
    private final byte[] body;
    public RequestWrapper(HttpServletRequest request, RSAEncryptUtil RSAEncryptUtil, boolean rasFlag) {
        super(request);
        this.RSAEncryptUtil=RSAEncryptUtil;
        this.RasFlag=rasFlag;
        body=getBody(request).getBytes(Charset.forName("UTF-8"));

    }

    public ServletInputStream getInputStream(){
        final ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(body);
        return new ServletInputStream() {


            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
               return byteArrayInputStream.read();
            }
        };

    }

    private String getBody(ServletRequest request) {
        StringBuffer sb=new StringBuffer();
        InputStream inputStream=null;
        BufferedReader bufferedReader=null;
         try {
             inputStream = request.getInputStream();
             bufferedReader= new BufferedReader(new InputStreamReader(inputStream,Charset.forName("UTF-8")));
             String line=null;
             while((line=bufferedReader.readLine())!=null){
                 sb.append(line);
             }
         }catch(Exception e){
                logger.info("RequestWrapper.getBody?????????"+e);
         }finally {
             if(null != inputStream){
                 try{
                 inputStream.close();}
                 catch (Exception e){
                     logger.info("inputStream.close?????????"+e);
                 }
             }
             if(null != bufferedReader){
                 try{
                     bufferedReader.close();}
                 catch (Exception e){
                     logger.info("bufferedReader.close?????????"+e);
                 }
             }
         }
         //ras???????????????
        String decrypt=sb.toString();
         logger.info("???????????????"+decrypt);
        if(RasFlag){
             //ras????????????
             try {
                 decrypt = RSAEncryptUtil.decrypt(decrypt);
             } catch (Exception e) {
                 logger.info("????????????????????????");
                 e.printStackTrace();
                 return "";
             }
         }
        String base64param = decrypt;
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] bytes=null;
        try {
            bytes = decoder.decodeBuffer(base64param);
        } catch (IOException e) {
            logger.info("base64????????????"+e);
            return "";
        }
        if(null !=  bytes){
            try {
                String result=new String(bytes,"utf-8");
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }else{
            return "";
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
