package com.jerry.hystrix.helloworld;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {

        //三种种不同的执行方式
        //1. 同步执行
        String string = new CommandHelloWorld("Bob").execute();
        //2. 异步调用
        Future<String> futureString = new CommandHelloWorld("Bob").queue();
        //3. 通过注册回调函数，可同步，可异步
        Observable<String> observableString = new CommandHelloWorld("Bob").observe();

        System.out.println(string);

        try {
            System.out.println(futureString.get(100, TimeUnit.MILLISECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        // blocking
        String single = observableString.toBlocking().single();
        System.out.println("single = " + single);

        // non-blocking
        observableString.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                //执行结果处理
                System.out.println(s);
            }
        });

        //non-blocking
        observableString.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("On Completed");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("On Error");
            }

            @Override
            public void onNext(String s) {
                System.out.println("On Next");
            }
        });
    }
}
