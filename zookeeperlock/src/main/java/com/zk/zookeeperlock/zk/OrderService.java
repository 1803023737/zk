package com.zk.zookeeperlock.zk;

import com.zk.zookeeperlock.simple.OrderNumGenerator;

public class OrderService implements Runnable {

    private OrderNumGenerator orderNumGenerator=new OrderNumGenerator();
    private Lock lock=new ZookeeperDistrbuteLock2();
   // private Lock lock=new ZookeeperDistrbuteLock();

    @Override
    public void run() {
        lock.getLock();
        try {
            String num = orderNumGenerator.getNum();
            System.out.println(Thread.currentThread().getName()+",生成订单ID："+num);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
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
            new Thread(new OrderService(),""+i).start();
        }
    }


}
