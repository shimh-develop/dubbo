package com.shimh.config;

import org.apache.dubbo.config.AbstractConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.ServiceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.ServiceClassPostProcessor;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.schema.DubboNamespaceHandler;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.protocol.ProtocolFilterWrapper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: shiminghui
 * @create: 2021年03月
 **/
public class ConfigTest {

    @Test
    public void test1() {
        /**
         * 使用XML进行配置
         * 解析标签 <dubbo:service/> <dubbo:reference/> 生成对应的bean定义
         *
         */
        // 解析XML配置文件
        // /dubbo/dubbo-config/dubbo-config-spring/src/main/resources/META-INF/dubbo.xsd
        // org.apache.dubbo.config.spring.schema.DubboNamespaceHandler

        DubboNamespaceHandler handler;
    }

    @Test
    public void test2() {
        /**
         * 基于注解配置
         * 1 EnableDubboConfig
         *  配置属性解析dubbo.application 生成对应的bean ApplicationConfig
         *
         * 2 @DubboComponentScan
         *  解析@Service
         *  @see ServiceClassPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
         *  解析@Reference ReferenceAnnotationBeanPostProcessor
         *
         *
         */
        EnableDubbo dubbo;

        // 提供者实现类
        DubboService service;
        //s 应用实现接口
        DubboReference reference;
    }

    @Test
    public void test3() {

        /**
         * 服务暴露
         * @see ServiceConfig#export()
         *
         */

        ServiceBean bean;
    }

    @Test
    public void test4() {

        /**
         * 服务引用
         * @see ReferenceConfig#get()
         *
         */

        ReferenceBean bean;

        //s 消费拦截器filter
        /**
         * @see ProtocolFilterWrapper#refer(java.lang.Class, org.apache.dubbo.common.URL)
         */
    }

    @Test
    public void test5() {
        Cluster cluster;
        Directory directory;
        Router router;
        LoadBalance balance;
    }


}
