package com.bwd.nms.config;

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
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.bwd.nms.otrsrepository", entityOperationsRef = "otrsR2dbcEntityOperations")
public class OtrsDbConfig {

    private final Logger log = LoggerFactory.getLogger(OtrsDbConfig.class);

    @Bean
    @ConfigurationProperties("otrs.datasource")
    public DataSourceProperties otrsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("otrsConnectionFactory")
    public ConnectionFactory otrsConnectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(otrsDataSourceProperties().getUrl());
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
        ob = ob.option(USER, otrsDataSourceProperties().getUsername());
        ob = ob.option(PASSWORD, otrsDataSourceProperties().getPassword());

        log.info("Starting OTRS DB Datasource");

        return ConnectionFactories.get(ob.build());
    }

    @Bean
    public R2dbcEntityOperations otrsR2dbcEntityOperations(
        @Qualifier("otrsConnectionFactory") ConnectionFactory connectionFactory
    ) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);

        return new R2dbcEntityTemplate(databaseClient, MySqlDialect.INSTANCE);
    }
}
