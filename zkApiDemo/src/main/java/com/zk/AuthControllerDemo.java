package com.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AuthControllerDemo implements Watcher {

    private static CountDownLatch countDownLatch=new CountDownLatch(1);
    private static CountDownLatch countDownLatch2=new CountDownLatch(1);

    private static String CONNECTSTR="192.168.211.128";
    Stat stat=new Stat();
    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTR, 5000, new AuthControllerDemo());
        //等待
        countDownLatch.await();

        //权限定义
        ACL acl1 = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
        ACL acl2 = new ACL(ZooDefs.Perms.ALL, new Id("ip","192.168.1.3"));

        List<ACL> aclList=new ArrayList<>();
        aclList.add(acl1);
        aclList.add(acl2);

        //创建节点  并且设置权限
        zooKeeper.create("/auth1","aaa".getBytes(),aclList,CreateMode.PERSISTENT);
        //授权
        zooKeeper.addAuthInfo("digest","root:root".getBytes());
        //创建子节点
        zooKeeper.create("/auth1/auth1","123".getBytes(),ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.EPHEMERAL);

        //删除节点
        ZooKeeper zooKeeper2 = new ZooKeeper(CONNECTSTR, 5000, new AuthControllerDemo());
        countDownLatch.await();
        zooKeeper2.addAuthInfo("digest","root:root".getBytes());
        //删除节点
        zooKeeper2.delete("/auth1/auth1",-1);

    }


    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getState()== Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
        }
        if (watchedEvent.getType()== Event.EventType.NodeDataChanged){
            //节点发生变化
            System.out.println("现在节点发生了变化，变化路径:"+watchedEvent.getPath());
        }
    }
}
