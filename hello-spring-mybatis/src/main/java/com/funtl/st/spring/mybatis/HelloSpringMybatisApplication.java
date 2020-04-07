package com.funtl.st.spring.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.funtl.st.spring.mybatis")
public class HelloSpringMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringMybatisApplication.class, args);
    }

}
