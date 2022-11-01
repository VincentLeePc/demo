package com.example.demo.config;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * spring-cloud-starter-openfeign Config
 * @remark
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class OpenfeignConfig {
    /** 每次讀取時間上限(秒) */
    private int readTimeout = 30;

    /** 整個Request完成時間上限(秒) */
    private int connectTimeout = 90;

    /** 每次寫入時間上限(秒) */
    private int writeTimeout = 30;

    /** 最大並發數 */
    private int maxRequest = 100;

    /** 單域名最大並發數 */
    private int maxRequestPerHost = 50;

    /** 最多只能存在空閒連線數(秒) */
    private int maxIdleConnections = 20;

    /** 空閒連接最多只能存活時間(秒) */
    private int keepAliveDuration = 60;
 


    /**
     * Dispatcher configuration
     * @return Dispatcher
     * @remark
     */
    @Bean
    public Dispatcher dispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequest);
        dispatcher.setMaxRequestsPerHost(maxRequestPerHost);
 

        return dispatcher;
    }

    /**
     * Create okHttpClientBuilder
     * @return OkHttpClient.Builder
     * @remark
     */
    @Bean
    public OkHttpClient.Builder okHttpClientBuilder() {
        return new OkHttpClient.Builder().readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .dispatcher(dispatcher())
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS))
                ;
    }

    /**
     * Feign Form Encoder configuration
     * @param messageConverters message converter
     * @return Encoder
     * @remark
     */
    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}