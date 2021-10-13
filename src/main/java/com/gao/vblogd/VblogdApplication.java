package com.gao.vblogd;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;



@SpringBootApplication
@EnableScheduling//开启定时任务支持
public class VblogdApplication {
//    private static Logger logger= (Logger) LoggerFactory.getLogger(VblogdApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(VblogdApplication.class, args);
    }

}
