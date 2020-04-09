package com.funtl.st.demo.bio.http;

import java.io.IOException;

/**
 * @author songtao
 * @create 2020-04-2020/4/9-22:34
 */
public abstract class StServlet {
    public void service(StRequest request,StResponse response) throws IOException {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else {
            doPost(request,response);
        }
    }

    public abstract void doPost(StRequest request, StResponse response);
    public abstract void doGet(StRequest request, StResponse response) throws IOException;
}
