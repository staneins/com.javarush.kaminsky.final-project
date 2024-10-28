package com.javarush.jira.common.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("prod")
    public DataSource prodDataSource(@Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource(@Value("jdbc:h2:mem:testdb") String url,
                                     @Value("sa") String username,
                                     @Value("password") String password,
                                     @Value("org.h2.Driver") String driver) {
        return DataSourceBuilder.create().url(url).username(username).password(password).driverClassName(driver).build();
    }
}
