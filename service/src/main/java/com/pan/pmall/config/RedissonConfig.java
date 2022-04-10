package com.pan.pmall.config;

import com.pan.pmall.properties.RedissonConfigProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-03-07 11:23
 **/
@Configuration
public class RedissonConfig {
    @Resource
    private RedissonConfigProperties redissonConfigProperties;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();

        //redisson版本是3.5，集群的ip前面要加上“redis://”
        /*取出Cluster类中的nodes, 并加上"redis://"前缀, 并构造为一个数组*/
        List<String> nodes = redissonConfigProperties.getCluster().getNodes();
        String[] nodeArr = new String[nodes.size()];
        for (int i = 0; i < nodeArr.length; i++) {
            nodeArr[i] = "redis://" + nodes.get(i);
        }
        
        /*addNodeAddress(): 要传入一个数组*/
        config.useClusterServers()
                .addNodeAddress(nodeArr)
                .setPassword(redissonConfigProperties.getPassword())
                .setTimeout(redissonConfigProperties.getTimeout())
                .setCheckSlotsCoverage(false);

        return Redisson.create(config);
    }
}
