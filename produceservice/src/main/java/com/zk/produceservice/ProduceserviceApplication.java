package com.zk.produceservice;

import com.zk.produceservice.listener.InitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProduceserviceApplication {

	/**
	 * 产品的服务  在服务启动的时候向zk 创建节点
	 * 将服务注册在zk上
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProduceserviceApplication.class, args);
	}

	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean(){
		ServletListenerRegistrationBean registrationBean=new ServletListenerRegistrationBean();
		registrationBean.setListener(new InitListener());
		return registrationBean;
	}


}
