package com.jerry.hystrix.fallback;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        FailbackCommand failbackCommand = new FailbackCommand("test-fail-back");
        String result = failbackCommand.execute();
    }

}
