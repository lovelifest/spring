package com.funtl.st.mybatis.dao;

import com.funtl.st.mybatis.entity.Blog;

/**
@author songtao
@create 2020-03-2020/3/24-22:15
*/
public interface BlogMapper {
    int insert(Blog record);

    int insertSelective(Blog record);
}