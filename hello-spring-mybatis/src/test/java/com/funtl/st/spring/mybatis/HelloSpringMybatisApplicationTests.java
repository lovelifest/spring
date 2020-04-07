package com.funtl.st.spring.mybatis;

import com.funtl.st.spring.mybatis.dao.BlogMapper;
import com.funtl.st.spring.mybatis.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HelloSpringMybatisApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Test
    void contextLoads() {
        List<Blog> blogs = blogMapper.selectAll();
        System.out.println(blogs.size());
    }

}
