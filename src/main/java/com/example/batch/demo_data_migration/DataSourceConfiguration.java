package com.example.batch.demo_data_migration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {
    @Bean(name = "dataSource")
    @Primary
    DataSource dataSource() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSourceProperties.setUsername("postgres");
        dataSourceProperties.setPassword("123");
        dataSourceProperties.setDriverClassName("org.postgresql.Driver");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    DataSource dataTarget() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl("jdbc:mysql://localhost:3306/data_migration");
        dataSourceProperties.setUsername("root");
        dataSourceProperties.setPassword("password123");
        dataSourceProperties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
