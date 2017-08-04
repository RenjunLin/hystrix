package com.jerry.hystrix.jetty;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JettyServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int port;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private Server server = null;

    public void init() {
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //绑定8080端口,加载HystrixMetricsStreamServlet并映射url
                        server = new Server(8080);
                        WebAppContext context = new WebAppContext();
                        context.setContextPath("/");
                        context.addServlet(HystrixMetricsStreamServlet.class, "/hystrix.stream");
                        context.setResourceBase(".");
                        server.setHandler(context);
                        server.start();
                        server.join();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    public void destory() {
        if (server != null) {
            try {
                server.stop();
                server.destroy();
                logger.warn("jettyServer stop and destroy!");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
