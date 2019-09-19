package com.zk.orderservice.util;

import org.springframework.util.CollectionUtils;

import java.util.Random;

public class RandomLoadBalance extends LoadBalance {
    @Override
    public String chooseServiceHost() {
        String result="";
        if (!CollectionUtils.isEmpty(SERVICE_LIST)){
            int index = new Random().nextInt(SERVICE_LIST.size());
            result=SERVICE_LIST.get(index);
        }
        return result;
    }
}
