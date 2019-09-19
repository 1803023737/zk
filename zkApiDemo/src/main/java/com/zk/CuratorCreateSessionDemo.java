package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorCreateSessionDemo {

    private static final String CONNECTuRL="192.168.211.128";

    public static void main(String[] args) {

        //normal 风格
        // new ExponentialBackoffRetry(1000, 3)  连接策略  重试连接3次  第一次1秒
/*        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECTuRL, 5000, 5000,
                new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();*/


        //fluent风格  链式编程
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(CONNECTuRL).sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curator.start();
        System.out.println("success......");
    }
}
