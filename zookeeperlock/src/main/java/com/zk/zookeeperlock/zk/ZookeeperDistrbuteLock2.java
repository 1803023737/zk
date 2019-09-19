package com.zk.zookeeperlock.zk;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperDistrbuteLock2 extends ZookeeperAbstractLock{

    private CountDownLatch countDownLatch=null;
    private String currentPath;
    private String beforePath;

    /**
     * 创建一个永久节点  lock2
     */
    public ZookeeperDistrbuteLock2() {
        if (!this.zkClient.exists(PATH2)){
            this.zkClient.createPersistent(PATH2);
        }
    }

    @Override
    public boolean tryLock() {
        if (currentPath==null || currentPath.length()<=0){
           //创建一个顺序临时节点
            currentPath=this.zkClient.createEphemeralSequential(PATH2+"/","lock");
        }
        //获取所有临时节点并排序
        List<String> children = this.zkClient.getChildren(PATH2);
        Collections.sort(children);
        System.out.println(children);
        if (currentPath.equals(PATH2+"/"+children.get(0))){
            System.out.println("当前是第一个节点了。。。。。"+currentPath);
            return true;
        }else {
            //
            System.out.println("当前节点的号码："+currentPath);
            System.out.println("当前节点的号码："+currentPath.substring(7));
            int wz = Collections.binarySearch(children, currentPath.substring(7));
            beforePath=PATH+"/"+children.get(wz-1);
            System.out.println("前一个节点的号码："+beforePath);
        }
        return false;
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

        //注册事件  监听排在自己之前的了临时节点
        zkClient.subscribeDataChanges(beforePath,iZkDataListener);
        //如果节点存在
        if (zkClient.exists(beforePath)){
            countDownLatch=new CountDownLatch(1);
            try {
                //等待  一直等到事件通知
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //删除监听
        zkClient.unsubscribeDataChanges(beforePath,iZkDataListener);
    }

    @Override
    public void unLock() {
          zkClient.delete(currentPath);
          zkClient.close();
    }
}
