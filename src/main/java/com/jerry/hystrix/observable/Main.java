package com.jerry.hystrix.observable;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observableString = new CommandObServableHelloWorld("Bob").toObservable();
        observableString.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                //执行结果处理
                System.out.println(s);
            }
        });

        Thread.sleep(1000);
    }
}
