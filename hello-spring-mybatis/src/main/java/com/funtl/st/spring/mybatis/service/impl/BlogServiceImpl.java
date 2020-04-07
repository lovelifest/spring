package com.funtl.st.spring.mybatis.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.funtl.st.spring.mybatis.entity.Blog;
import com.funtl.st.spring.mybatis.dao.BlogMapper;
import com.funtl.st.spring.mybatis.service.BlogService;
/**
@author songtao
@create 2020-03-2020/3/25-23:34
*/
@Service
public class BlogServiceImpl implements BlogService{

    @Resource
    private BlogMapper blogMapper;

    @Override
    public int insert(Blog record) {
        return blogMapper.insert(record);
    }

    @Override
    public int insertSelective(Blog record) {
        return blogMapper.insertSelective(record);
    }

}
