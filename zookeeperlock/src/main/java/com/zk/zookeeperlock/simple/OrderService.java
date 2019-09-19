package com.zk.zookeeperlock.simple;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class OrderService implements Runnable {

    private OrderNumGenerator orderNumGenerator=new OrderNumGenerator();
    //并发抢
    private static CountDownLatch countDownLatch=new CountDownLatch(50);
    private static List<String> list=new Vector<>();


    @Override
    public void run() {
        countDownLatch.countDown();
        list.add(orderNumGenerator.getNum());
    }

    /**
     * 并发安全问题
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        System.out.println("生成订单唯一编号");

        //创建50个线程
        for (int i=0;i<50;i++){
            new Thread(new OrderService()).start();
        }

        countDownLatch.await();

        Thread.sleep(2000);
        Collections.sort(list);
        for (String s : list) {
            System.out.println(s);
        }

    }


}
