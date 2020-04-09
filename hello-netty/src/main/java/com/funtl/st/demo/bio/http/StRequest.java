package com.funtl.st.demo.bio.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author songtao
 * @create 2020-04-2020/4/9-22:34
 */
public class StRequest {

    private String method;
    private String url;

    public StRequest(InputStream in) {
        try {
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = in.read(buff)) > 0) {
                content = new String(buff, 0, len);
            }

            String line = content.split("\\n")[0];//GET /firstServlet.do HTTP/1.1
            String[] arr =line.split("\\s");
            this.method = arr[0];//GET
            this.url = arr[1].split("\\?")[0];///firstServlet.do

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
