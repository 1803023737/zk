package com.zk.zookeeperlock.zk;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock extends AbstractLock{

      //zk连接地址
      private static final String CONNECTIONSTR="127.0.0.1:2181";

      protected ZkClient zkClient=new ZkClient(CONNECTIONSTR);

      protected static final String PATH="/lock";
      protected static final String PATH2="/lock2";






}
