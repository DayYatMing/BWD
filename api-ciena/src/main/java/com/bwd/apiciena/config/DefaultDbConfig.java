package com.bwd.apiciena.config;

import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.bwd.nms.repository", entityOperationsRef = "defaultR2dbcEntityOperations")
public class DefaultDbConfig {

    private final Logger log = LoggerFactory.getLogger(DefaultDbConfig.class);

    @Bean
    @Primary
    @ConfigurationProperties("spring.r2dbc")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Qualifier("defaultConnectionFactory")
    public ConnectionFactory defaultConnectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(defaultDataSourceProperties().getUrl());
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
        ob = ob.option(USER, defaultDataSourceProperties().getUsername());
        ob = ob.option(PASSWORD, defaultDataSourceProperties().getPassword());

        log.info("Starting Default DB Datasource");

        return ConnectionFactories.get(ob.build());
    }

    @Bean
    @Primary
    public R2dbcEntityOperations defaultR2dbcEntityOperations(@Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);

        return new R2dbcEntityTemplate(databaseClient, MySqlDialect.INSTANCE);
    }
}
