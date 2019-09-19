package com.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSession {

    //单节点 集群
    private static final String CONNECTuRL="192.168.211.128";
    private static CountDownLatch countDownLatch=new CountDownLatch(1);


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        final ZooKeeper zooKeeper= new ZooKeeper(CONNECTuRL, 5000,new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //监听器监听状态  如果已经获得连接
                if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                    System.out.println("zk watchedEvent ststus:"+watchedEvent.getState());
                }
                if (watchedEvent.getType()== Event.EventType.NodeDataChanged){
                    //节点发生变化
                    System.out.println("现在节点发生了变化，变化路径:"+watchedEvent.getPath());
                }
            }
        });
        countDownLatch.await();
        System.out.println("zk:"+zooKeeper);
        System.out.println("zk ststus:"+zooKeeper.getState());
        //创建节点  路径  节点值  权限  节点类型
      /*  zooKeeper.create("/enjoy1","enjoy1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);*/

      //设置节点的值
       // zooKeeper.setData("/enjoy1","hello".getBytes(),-1);

      //获得节点
/*        Stat stat=new Stat();
        byte[] data=zooKeeper.getData("/enjoy1",true,stat);
        String result = new java.lang.String(data);
        System.out.println("result:"+result);
        System.out.println("stat:"+stat);*/

        //删除节点
//        zooKeeper.delete("/enjoy1",-1);

/*        System.out.println("---------------------------------------------------");
        //获得子节点
        List<String> children = zooKeeper.getChildren("/enjoy1", true);
        System.out.println(children);*/

        //服务注册与发现



        //watch机制  当数据或者节点变更通知客户端
        //创建节点   没有触发监听
        //zooKeeper.create("/enjoy2","enjoy2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);



        //触发变更前必须存在watcher  并且watch是一次性的
        Stat stat=new Stat();
        byte[] data=zooKeeper.getData("/enjoy1",true,stat);
        zooKeeper.setData("/enjoy1","hello334".getBytes(),-1);
        zooKeeper.setData("/enjoy1","hello333".getBytes(),-1);
    }
}
