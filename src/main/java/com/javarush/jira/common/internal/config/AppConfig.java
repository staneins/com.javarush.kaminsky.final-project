package com.javarush.jira.common.internal.config;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.javarush.jira.common.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.ProblemDetail;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Configuration
@Slf4j
@EnableCaching
@RequiredArgsConstructor
@EnableScheduling
public class AppConfig {

    private final AppProperties appProperties;
    private final Environment env;

    @Bean("mailExecutor")
    Executor getAsyncExecutor() {
        return new ThreadPoolTaskExecutor() {
            {
                setCorePoolSize(appProperties.getMailSendingProps().corePoolSize);
                setMaxPoolSize(appProperties.getMailSendingProps().maxPoolSize);
                setThreadNamePrefix("mail-");
            }
        };
    }

    public boolean isProd() {
        return env.acceptsProfiles(Profiles.of("prod"));
    }

    public boolean isTest() {
        return env.acceptsProfiles(Profiles.of("test"));
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource(@Value("${spring.datasource.url}") String url,
                                     @Value("${spring.datasource.username}") String username,
                                     @Value("${spring.datasource.password}") String password) {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource(@Value("jdbc:h2:mem:testdb;NON_KEYWORDS=VALUE") String url,
                                     @Value("sa") String username,
                                     @Value("password") String password,
                                     @Value("org.h2.Driver") String driver) {
        return DataSourceBuilder.create().url(url).username(username).password(password).driverClassName(driver).build();
    }

    @Autowired
    void configureAndStoreObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Hibernate5JakartaModule());
        // https://stackoverflow.com/questions/7421474/548473
        objectMapper.addMixIn(ProblemDetail.class, MixIn.class);
        JsonUtil.setMapper(objectMapper);
    }

    //    https://stackoverflow.com/a/74630129/548473
    @JsonAutoDetect(fieldVisibility = NONE, getterVisibility = ANY)
    interface MixIn {
        @JsonAnyGetter
        Map<String, Object> getProperties();
    }
}
