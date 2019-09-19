package com.zk.zookeeperlock.zk;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock{

    private CountDownLatch countDownLatch=null;

    @Override
    public void getLock() {
        super.getLock();
    }

    @Override
    public boolean tryLock() {
        //尝试获得连接 创建临时节点
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (RuntimeException e) {
            //创建失败  返回false
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public void waitLock() {
        //zk监听器
        IZkDataListener iZkDataListener = new IZkDataListener() {
            /**
             * 处理节点变化
             * @param s
             * @param o
             * @throws Exception
             */
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            /**
             * 处理节点删除
             * @param s
             * @throws Exception
             */
            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        };

        //注册事件
        zkClient.subscribeDataChanges(PATH,iZkDataListener);
                //如果节点存在
        if (zkClient.exists(PATH)){
                    countDownLatch=new CountDownLatch(1);
                    try {
                        //等待  一直等到事件通知
                        countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //删除监听
        zkClient.unsubscribeDataChanges(PATH,iZkDataListener);
    }

    @Override
    public void unLock() {
        if (zkClient!=null){
            zkClient.delete(PATH);
            zkClient.close();
            System.out.println(Thread.currentThread().getName()+"释放锁资源。。。。");
        }
    }
}
