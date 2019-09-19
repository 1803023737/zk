package com.zk.zkconfig.listener;

import com.zk.zkconfig.utils.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    private static final String URL="/db/url";
    private static final String PASSWORD="/db/password";
    private static final String USERNAME="/db/username";
    private static final String DRIVER="/db/driver";

    ZkClient zkClient=new ZkClient("127.0.0.1",5000,5000,new MyZkSerializer());


    private void init(){

        zkClient.subscribeDataChanges(URL, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据库连接url发生变化");
                
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
