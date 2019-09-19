package com.zk.orderservice.listener;

import com.zk.orderservice.util.LoadBalance;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

public class InitListener implements ServletContextListener {

    private static final String BASE_SERVICE="/services";
    private static final String SERVICE_NAME="/products";
    private ZooKeeper zooKeeper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            zooKeeper=new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent ) {
                  if (watchedEvent.getType()== Event.EventType.NodeChildrenChanged
                         && watchedEvent.getPath().equals(BASE_SERVICE+SERVICE_NAME)
                          ){
                      System.out.println("节点变更时，监控节点更新。。。。");
                      updateServerList();
                  }
                }
            });
            //第一次获得列表信息
            updateServerList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void updateServerList(){
        List<String> list=new ArrayList<>();
        try {
            //检测多次
            List<String> children = zooKeeper.getChildren(BASE_SERVICE + SERVICE_NAME, true);
            for (String child : children) {
                byte[] data = zooKeeper.getData(BASE_SERVICE + SERVICE_NAME + "/" + child, false, null);
                String host = new String(data, "UTF-8");
                System.out.println("host:"+host);
                list.add(host);
            }
            LoadBalance.SERVICE_LIST=list;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
