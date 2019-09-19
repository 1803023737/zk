package com.zk.orderservice.util;

import java.util.List;

//负载接口
public abstract class LoadBalance {

    public volatile static List<String> SERVICE_LIST;
    public abstract String chooseServiceHost();

}
