package com.zk.zookeeperlock.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 订单生成器
 */
public class OrderNumGenerator {
    //全局订单ID
    public static int count =0;

    /**
     * 通过锁的方式  防止并发安全为题
     */
    private Lock lock=new ReentrantLock();


    /**
     * 生成订单id
     * @return
     */
    public String getNum(){
        lock.lock();

        SimpleDateFormat sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date()) + "-" + ++count;
        }  finally {
            lock.unlock();
        }
    }


}
