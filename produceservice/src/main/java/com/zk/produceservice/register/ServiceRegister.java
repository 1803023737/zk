package com.zk.produceservice.register;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class ServiceRegister {

    private static final String BASE_SERVICE="/services";
    private static final String SERVICE_NAME="/products";
    public static void register(String address,String port){
        //注册
        String path=BASE_SERVICE+SERVICE_NAME;
        try {
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });
            Stat exists = zooKeeper.exists(path, false);
            if (exists==null){
                zooKeeper.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String server_path=address+":"+port;
            zooKeeper.create(path+"/child",server_path.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("服务注册成功。。。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
