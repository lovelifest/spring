package com.funtl.st.spring.mybatis.dao;

import com.funtl.st.spring.mybatis.entity.Blog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author songtao
 * @create 2020-03-2020/3/25-23:36
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogMapperTest {
    @Autowired
    private BlogMapper blogMapper;


    @org.junit.Test
    public void select() {
        List<Blog> blogs = blogMapper.selectAll();
        blogs.forEach(o->{
            System.out.println(o.toString());
        });
    }

    @org.junit.Test
    public void selectByPage() {
        PageHelper.startPage(1,2);
        List<Blog> blogs = blogMapper.selectAll();
        blogs.forEach(o-> System.out.println(o.toString()));
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs,1);

    }
}