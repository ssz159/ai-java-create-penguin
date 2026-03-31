package org.example.aijavacreate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.example.aijavacreate.mapper")
public class AiJavaCreateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiJavaCreateApplication.class, args);
    }

}
