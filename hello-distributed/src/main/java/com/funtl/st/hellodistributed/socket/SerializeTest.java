package com.funtl.st.hellodistributed.socket;

import java.io.Serializable;

/**
 * @author songtao
 * @create 2020-04-2020/4/6-21:18
 */
public class SerializeTest {
    public static void main(String[] args) {
//        Iserializer iserializer = new XSerializable();
//        Iserializer iserializer = new JsonSerializer();
        Iserializer iserializer = new HessionSerializer();
        User user = new User();
        user.setName("tom");
        user.setAge(18);
        byte[] bytes = iserializer.serialize(user);
        User user1 = (User) iserializer.deserialize(bytes, User.class);
        System.out.println(user1);
    }




}
