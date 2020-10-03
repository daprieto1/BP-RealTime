package com.byteprogramming.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class RethinkDBConfiguration {

    @Autowired
    private Environment env;

    public static String DATABASE;
    public static String DB_HOST;

    @PostConstruct
    public void init() {
        this.DB_HOST = this.env.getProperty("rethinkdb.db_host");
        this.DATABASE = this.env.getProperty("rethinkdb.database");
    }

    @Bean
    public RethinkDBConnectionFactory connectionFactory() {
        return new RethinkDBConnectionFactory(DB_HOST, DATABASE);
    }

    @Bean
    DbInitializer dbInitializer() {
        return new DbInitializer();
    }
}
