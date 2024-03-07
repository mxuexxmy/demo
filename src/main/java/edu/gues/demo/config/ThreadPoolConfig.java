package edu.gues.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Lemonxu
 */
@Component
public class ThreadPoolConfig {


    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolExecutor.CallerRunsPolicy policy = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        threadPoolTaskExecutor.setCorePoolSize(2*Runtime.getRuntime().availableProcessors()+1);
        //最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(3*Runtime.getRuntime().availableProcessors());
        //队列最大等待数
        threadPoolTaskExecutor.setQueueCapacity(100);
        //设置线程名称
        threadPoolTaskExecutor.setThreadNamePrefix("collection-");
        //线程闲时等待时间：秒
        threadPoolTaskExecutor.setKeepAliveSeconds(45);
        //设置核心线程意外中断时可回收
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        //设置拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(policy);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
