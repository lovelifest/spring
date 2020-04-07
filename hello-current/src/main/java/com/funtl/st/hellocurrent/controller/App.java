package com.funtl.st.hellocurrent.controller;

import lombok.val;

/**
 * @author songtao
 * @create 2020-03-2020/3/28-23:33
 */
public class App     {
    private static IRequestProcessor processor;

    public void  setUp(){
        SaveProcessor saveProcessor = new SaveProcessor();
        saveProcessor.start();
        PrintProcessor printProcessor = new PrintProcessor(saveProcessor);
        printProcessor.start();
        processor = new PreProcessor(printProcessor);
        ((PreProcessor)processor).start();
    }


    public static void main(String[] args) {
        App app = new App();
        app.setUp();
        Request request = new Request();
        request.setName("Mic");
        processor.process(request);
    }
}
