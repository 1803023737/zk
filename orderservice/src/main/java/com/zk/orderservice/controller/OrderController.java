package com.zk.orderservice.controller;

import com.zk.orderservice.bean.Order;
import com.zk.orderservice.bean.Product;
import com.zk.orderservice.util.LoadBalance;
import com.zk.orderservice.util.RandomLoadBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    private LoadBalance loadBalance=new RandomLoadBalance();

    @RequestMapping("/getOrder/{id}")
    public Object getProduct(HttpServletRequest request,@PathVariable String id){
        String host=loadBalance.chooseServiceHost();
        Product product = restTemplate.getForObject("http://" + host + "product/getproduct/1", Product.class);
        return new Order(id,"orderName",product);
    }


}
