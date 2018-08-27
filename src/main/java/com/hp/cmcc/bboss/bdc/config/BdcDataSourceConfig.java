package com.hp.cmcc.bboss.bdc.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class BdcDataSourceConfig {
	
	//校验数据源
	@Bean(name = "bdcCompareTemplate")
	public JdbcTemplate bdcCompareJdbcTemplate(@Qualifier("bdcCompareDataSource") DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
	@Bean(name = "bdcCompareDataSource")
    @Qualifier("bdcCompareDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource bdcCompareDataSource() {
        return DataSourceBuilder.create().build();
    }
}

