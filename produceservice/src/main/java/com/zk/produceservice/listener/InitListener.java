package com.zk.produceservice.listener;

import com.zk.produceservice.register.ServiceRegister;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Properties properties=new Properties();
        try {
            properties.load(InitListener.class.getClassLoader().getResourceAsStream("application.properties"));
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String port = properties.getProperty("server.port");
            ServiceRegister.register(hostAddress,port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
