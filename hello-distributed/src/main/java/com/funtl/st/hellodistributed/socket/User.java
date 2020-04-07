package com.funtl.st.hellodistributed.socket;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-20:44
 */
public class User  implements Serializable {

    private static final long serialVersionUID = -6936329468474744514L;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
