package com.zk.zookeeperlock.simple;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单生成器
 */
public class OrderNumGenerator {
    //全局订单ID
    public static int count =0;

    /**
     * 生成订单id
     * @return
     */
    public String getNum(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date())+"-"+ ++count;
    }


}
