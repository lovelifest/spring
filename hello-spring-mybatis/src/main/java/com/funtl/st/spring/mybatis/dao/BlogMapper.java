package com.funtl.st.spring.mybatis.dao;

import com.funtl.st.spring.mybatis.entity.Blog;

import java.util.List;

/**
@author songtao
@create 2020-03-2020/3/25-23:34
*/
public interface BlogMapper {
    int insert(Blog record);
    List<Blog> selectAll();

    int insertSelective(Blog record);
}