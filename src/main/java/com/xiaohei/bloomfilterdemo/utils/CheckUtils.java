package com.xiaohei.bloomfilterdemo.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckUtils {

    @Resource private StringRedisTemplate stringRedisTemplate;

    public boolean checkWhiteBloomFilter(String checkItem , String key) {
        int hasValue = Math.abs(key.hashCode());
        long index = (long) (hasValue % Math.pow(2,32));
        log.info("index:{}",index);
        Boolean existOk = stringRedisTemplate.opsForValue().getBit(checkItem, index);
        log.info("whitelistCustomer:{}",existOk);
        return existOk;
    }

}
