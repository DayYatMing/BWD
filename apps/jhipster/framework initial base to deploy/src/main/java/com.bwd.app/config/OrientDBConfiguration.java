package com.bwd.nms.config;

import com.orientechnologies.orient.core.db.ODatabasePool;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrientDBConfiguration {
/*
############# This configuration is for OrientDB, if using, remove comments ##############
 */
//    private final Logger log = LoggerFactory.getLogger(OrientDBConfiguration.class);
//
//    @Value("${orientdb.datasource.url}")
//    private String orientDbUrl;
//
//    @Value("${orientdb.datasource.username}")
//    private String orientDbUsername;
//
//    @Value("${orientdb.datasource.password}")
//    private String orientDbPassword;
//
//    private ODatabasePool pool;
//
//    @PostConstruct
//    public void init() {
//        log.info("Starting Orient DB Datasource");
//        pool = new ODatabasePool(orientDbUrl, orientDbUsername, orientDbPassword);
//    }
//
//    @PreDestroy
//    public void close() {
//        if (pool != null) {
//            pool.close();
//        }
//    }
//
//    @Bean
//    public ODatabasePool databasePool() {
//        return pool;
//    }
}
