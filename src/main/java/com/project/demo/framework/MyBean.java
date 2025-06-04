package com.project.demo.framework;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

@Component
public class MyBean {

    private final DataSource dataSource;

    @Autowired
    public MyBean(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void checkConnection() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Connected: " + !conn.isClosed());
        }
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50); // number of CPU?
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("worker-");
        executor.initialize();
        return executor;
    }
}
