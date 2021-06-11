package com.guohl.innermanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InnermanageApplication {

    //nohup java -jar xxx.jar > catalina.out 2>&1
    public static void main(String[] args) {
        SpringApplication.run(InnermanageApplication.class, args);
    }

}
