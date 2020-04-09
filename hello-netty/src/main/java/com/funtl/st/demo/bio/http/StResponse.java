package com.funtl.st.demo.bio.http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author songtao
 * @create 2020-04-2020/4/9-22:34
 */
public class StResponse {

    private OutputStream out;

    public StResponse(OutputStream out) {
        this.out = out;
    }

    public void write(String s) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }

}
