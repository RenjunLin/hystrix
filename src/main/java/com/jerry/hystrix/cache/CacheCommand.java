package com.jerry.hystrix.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * ﻿请求缓存可以让(CommandKey/CommandGroup)相同的情况下,直接共享结果，降低依赖调用次数，在高并发和CacheKey碰撞率高场景下可以提升性能.
 */
public class CacheCommand extends HystrixCommand<String> {
    private static final HystrixCommandKey GETTER_KEY= HystrixCommandKey.Factory.asKey("CommandKey");
    private final int id;
    public CacheCommand( int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CacheCommand")).andCommandKey(GETTER_KEY));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        System.out.println(Thread.currentThread().getName() + " execute id=" + id);
        return "executed=" + id;
    }

    //重写getCacheKey方法,实现区分不同请求的逻辑
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    public static void main(String[] args){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CacheCommand command2a = new CacheCommand(2);
            CacheCommand command2b = new CacheCommand(2);

            System.out.println("1: " + command2a.execute());
            HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear("2");
            //isResponseFromCache判定是否是在缓存中获取结果
            System.out.println("2: " + command2a.isResponseFromCache());
            System.out.println("3: " + command2b.execute());
            System.out.println("4: " + command2b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
        context = HystrixRequestContext.initializeContext();
        try {
            CacheCommand command3b = new CacheCommand(2);
            System.out.println("5: " + command3b.execute());
            System.out.println("6: " + command3b.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}
