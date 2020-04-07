package com.funtl.st.mybatis.entity;

/**
@author songtao
@create 2020-03-2020/3/24-22:15
*/
public class Blog {
    private Integer bid;

    private String name;

    private Integer authorId;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}