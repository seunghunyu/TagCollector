package com.tag.collector.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CrmSourceConfig {

    private final String CRM_SOURCE = "crmSource";

    @Bean(CRM_SOURCE)
    @ConfigurationProperties(prefix = "spring.crm.datasource.hikari")
    public DataSource crmSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "crmJdbcTemplate")
    public JdbcTemplate crmDataConnection(@Qualifier(CRM_SOURCE) DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager CrmTransactionManager(@Qualifier(CRM_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
