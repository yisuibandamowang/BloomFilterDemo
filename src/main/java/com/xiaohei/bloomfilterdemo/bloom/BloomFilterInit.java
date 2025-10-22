package com.xiaohei.bloomfilterdemo.bloom;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BloomFilterInit {
    @Resource private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init(){
        String key = "12";
        int hasValue = Math.abs(key.hashCode());
        //通过hasValue和2的32次方取余后，获得对应的下标索引
        long index = (long) (hasValue % Math.pow(2,32));
        log.info(key + "对应的坑位index:{}", index);
        //设置 redis 里面的对应的 bitmap 对应类型的坑位，将值设置为1
        stringRedisTemplate.opsForValue().setBit("whitelistCustomer", index, true);
    }

}
