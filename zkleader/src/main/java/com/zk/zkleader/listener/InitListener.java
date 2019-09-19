package com.zk.zkleader.listener;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;
import java.util.TimerTask;

public class InitListener implements ServletContextListener {

    //创建zk连接
    ZkClient zkClient=new ZkClient("127.0.0.1:2181");
    private String path="/election";

    @Value("${server.port}")
    private String serverPort;

    private void init(){
        System.out.println("项目启动完成");
        createEphemeral();
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("主节点已经挂了，重新开始选举。。。");
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        createEphemeral();
                    }
                },5000);
            }
        });


    }

    //创建节点成功   则他为主节点  不成功就是从节点
    private void createEphemeral(){
        try {
            //创建临时节点
            zkClient.createEphemeral(path, serverPort);
            ElectionMaster.isSurvival=true;
            System.out.println("serverPort："+serverPort+",选举成功！");
        } catch (RuntimeException e) {
            ElectionMaster.isSurvival=false;
        }

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
