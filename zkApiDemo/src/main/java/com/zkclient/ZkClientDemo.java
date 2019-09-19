package com.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkClientDemo {

    private static final String CONNECTuRL="192.168.211.128";

    public static ZkClient newInstance(){
        ZkClient zkClient = new ZkClient(CONNECTuRL, 4000);
        return zkClient;
    }

    public static void main(String[] args) throws InterruptedException {

        ZkClient zkClient = newInstance();
    /*    zkClient.createPersistent("/zkclient2/client2/client2.1",true);
        System.out.println("zkclient:"+zkClient);

        //级联删除
        zkClient.deleteRecursive("/zkclient1");*/

        //获得子节点
/*        List<String> children = zkClient.getChildren("/zkclient2");
        System.out.println(children);*/

        //监听器
        zkClient.subscribeDataChanges("/zkclient2", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+",节点修改后的值："+o);
            }
            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
        zkClient.writeData("/zkclient2","zkclient1111");
        Thread.sleep(2000);
        zkClient.writeData("/zkclient2","zkclient2222");
        Thread.sleep(2000);

/*        //监听子节点
        zkClient.subscribeChildChanges("/zkclient2", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("修改的节点名称："+s+",子节点列表："+list);
            }
        });
        zkClient.deleteRecursive("/zkclient2/client2");
        Thread.sleep(2000);*/

    }


}
