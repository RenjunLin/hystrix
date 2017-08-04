package com.jerry.hystrix.fallback;

import com.jerry.hystrix.jetty.HystrixConfig;
import com.jerry.hystrix.jetty.JettyServer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import com.netflix.hystrix.metric.consumer.HystrixDashboardStream;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        AnnotationConfigWebApplicationContext rootApplication = new AnnotationConfigWebApplicationContext();
//        rootApplication.register(HystrixConfig.class);
        new JettyServer().init();
        FailbackCommand failbackCommand = new FailbackCommand("test-fail-back");
        String result = failbackCommand.execute();
        TimeUnit.DAYS.sleep(1);
    }

}
