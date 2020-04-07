package com.funtl.st.spring.mybatis.service;

import com.funtl.st.spring.mybatis.entity.Blog;
    /**
@author songtao
@create 2020-03-2020/3/25-23:34
*/
public interface BlogService{


    int insert(Blog record);

    int insertSelective(Blog record);

}
