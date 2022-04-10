package com.pan.pmall.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 配置类, 用来读取配置文件, 使用@ConfigurationProperties必须提供get和set方法
 * @author: Mr.Pan
 * @create: 2022-03-08 13:59
 **/
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfigProperties {
    /*这些属性用来接收配置文件中spring.redis下的属性值*/
    private Cluster cluster;
    private String password;
    private int database;
    private int timeout;

    /*内部类, 用来保存配置文件中cluster中的节点*/
    public static class Cluster {
        private List<String> nodes;

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
