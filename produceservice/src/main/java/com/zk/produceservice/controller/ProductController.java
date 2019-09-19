package com.zk.produceservice.controller;

import com.zk.produceservice.bean.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/getproduct/{id}")
     public Object getProduct(HttpServletRequest request,@PathVariable String id){
        int localPort = request.getLocalPort();
        return new Product(id,"productName"+localPort);
    }


}
