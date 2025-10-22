package com.xiaohei.bloomfilterdemo.controller;

import com.xiaohei.bloomfilterdemo.entity.Customer;
import com.xiaohei.bloomfilterdemo.serivice.TestService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Resource private TestService testService;
    @Resource private StringRedisTemplate stringRedisTemplate;
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public void addCustomer() {
        for (int i = 0; i < 2; i++) {
            Customer customer = Customer.builder().id(String.valueOf(i)).name("xiaohei" + i ).build();
            testService.addCustomer(customer);
        }
    }

    @RequestMapping("/getCustomer/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return testService.getCustomer(id);
    }


}
