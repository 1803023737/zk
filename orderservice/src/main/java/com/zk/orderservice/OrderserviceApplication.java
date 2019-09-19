package com.zk.orderservice;

import com.zk.orderservice.listener.InitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrderserviceApplication {

	/**
	 * 启动时获得服务列表
	 * 自己实现负载算法  负载均衡调用
	 * @param args
	 */
	//启动类
	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ServletListenerRegistrationBean servletListenerRegistrationBean(){
		ServletListenerRegistrationBean registrationBean=new ServletListenerRegistrationBean();
		registrationBean.setListener(new InitListener());
		return registrationBean;
	}

}
