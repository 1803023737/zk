package com.zk.zookeeperlock.syn;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单生成器
 */
public class OrderNumGenerator {
    //全局订单ID
    public static int count =0;

    /**
     * 通过锁的方式  防止并发安全为题
     */
     private static Object lock=new Object();


    /**
     * 生成订单id
     * @return
     */
    public String getNum(){
        synchronized (lock) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date()) + "-" + ++count;
        }
    }


}
