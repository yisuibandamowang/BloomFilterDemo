package com.xiaohei.bloomfilterdemo.serivice;

import com.xiaohei.bloomfilterdemo.entity.Customer;
import com.xiaohei.bloomfilterdemo.utils.CheckUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TestService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource private CheckUtils checkUtils;

    Map<Long,String> map = new HashMap<>();
    public void addCustomer(Customer customer){
        map.put(Long.valueOf(customer.getId()), customer.getName());
        stringRedisTemplate.opsForValue().set(customer.getId(), customer.getName());
    }

    public Customer getCustomer(String id){

        if(!checkUtils.checkWhiteBloomFilter("whitelistCustomer",id)){
            log.info("gai用户bu可访问");
            return null;
        }

        // 1. 先从 Redis 获取数据
        String name = stringRedisTemplate.opsForValue().get(id);
        Customer customer = null;

        // 2. 如果 Redis 中有数据，直接反序列化返回
        if (name != null && !name.isEmpty()) {
            customer = Customer.builder().id(id).name(name).build();
        } 
        
        // 3. 如果 Redis 中没有数据，则从本地 map 获取
        if (customer == null) {
            name = map.get(Long.valueOf(id));
            if (name != null) {
                customer = Customer.builder().id(id).name(name).build();
                // 同步到 Redis
                if (customer != null) {
                    stringRedisTemplate.opsForValue().set(id, name);
                }
            }
        }
        
        return customer;
    }

}