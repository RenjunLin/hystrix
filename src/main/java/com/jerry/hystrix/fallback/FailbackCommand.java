package com.jerry.hystrix.fallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

public class FailbackCommand extends HystrixCommand<String> {

    private final String name;

    public FailbackCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FailBackGroup")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)
        ));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("Hello " + name +" thread:" + Thread.currentThread().getName());
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        System.out.println("exeucute Falled");
        return "exeucute Falled";
    }
}
