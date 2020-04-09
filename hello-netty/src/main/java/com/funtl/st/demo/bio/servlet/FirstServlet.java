package com.funtl.st.demo.bio.servlet;

import com.funtl.st.demo.bio.http.StRequest;
import com.funtl.st.demo.bio.http.StResponse;
import com.funtl.st.demo.bio.http.StServlet;

import java.io.IOException;

/**
 * @author songtao
 * @create 2020-04-2020/4/9-22:34
 */
public class FirstServlet extends StServlet {

    public void doPost(StRequest request, StResponse response) {
        this.doPost(request,response);
    }

    public void doGet(StRequest request, StResponse response) throws IOException {
        response.write("THIS IS first servlet");
    }
}
