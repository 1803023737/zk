package com.zk.zkleader.controller;

import com.zk.zkleader.listener.ElectionMaster;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("getServerInfo")
    public String getServerInfo(){
        return ElectionMaster.isSurvival ?  "当前服务器为主节点" : "当前服务器为从节点";
    }

}
