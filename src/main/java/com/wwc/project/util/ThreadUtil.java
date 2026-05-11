package com.wwc.project.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadUtil {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 從 application.yml 讀取設定
        // 這些值將由 application.yml 中的 spring.task.execution.thread-name-prefix 等屬性填充
        executor.setCorePoolSize(5); // 核心執行緒數
        executor.setMaxPoolSize(10); // 最大執行緒數
        executor.setQueueCapacity(20); // 佇列容量
        executor.setThreadNamePrefix("MyThread-"); // 執行緒名稱前綴
        executor.initialize();
        return executor;
    }
}
