package com.jerry.hystrix.helloworld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))   //通过group隔离
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))           //通过command key隔离
                /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));  //通过资源隔离
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + " !";
    }
}
