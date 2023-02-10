package com.udacity.jwdnd.course1.cloudstorage.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)

public class H2DBConfigs {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")

    public DataSourceProperties DataSourceProperties() {return new DataSourceProperties();}
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.configuration")

    public HikariDataSource DataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();}
}
