package com.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collection;

public class CuratorOpreatorDemo {

    private static final String CONNECTuRL="192.168.211.128";

    public static void main(String[] args) throws Exception {
        //创建节点
        CuratorFramework curatorFramework = newInstance();
        curatorFramework.start();

        //创建节点
/*        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curator/curator1/curator11","111".getBytes());*/

        //删除节点1
//        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/curator");

        //获得节点数据
//        Stat stat = new Stat();
//        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/node1");
//        System.out.println(new String(bytes));

     /*   Stat stat = curatorFramework.setData().forPath("/node1", "123".getBytes());
        System.out.println(stat);*/


      /*
      * 异步
      * */
/*        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final CountDownLatch countDownLatch=new CountDownLatch(1);
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    //后台进程
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println(Thread.currentThread().getName()+"-->resultcode:"+event.getResultCode()+"-->"+event.getType());
                        countDownLatch.countDown();
                    }
                },executorService)
                .forPath("/enjoycurator","hello".getBytes());
        System.out.println("await....before");
        //阻塞方法
        countDownLatch.await();
        System.out.println("await....after");
        executorService.shutdown();*/


        /*
        * 事务操作
        * */
        Collection<CuratorTransactionResult> collect = curatorFramework.inTransaction()
                .create().forPath("/tran1", "111".getBytes())
                .and()
                .create().forPath("/tran2", "222".getBytes())
                .and()
                .setData().forPath("/tran1", "333".getBytes()).and().commit();
        for (CuratorTransactionResult curatorTransactionResult : collect) {
            System.out.println(curatorTransactionResult.getForPath()+"====="+curatorTransactionResult.getType());
        }
    }

    public static CuratorFramework newInstance(){
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(CONNECTuRL).sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        return  curator;
    }

}
