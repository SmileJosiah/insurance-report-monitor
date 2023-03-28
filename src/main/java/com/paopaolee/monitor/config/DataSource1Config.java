package com.paopaolee.monitor.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author paopaolee
 */
@Configuration
@MapperScan(basePackages = {"com.paopaolee.monitor.dao.ds1"},  sqlSessionFactoryRef = "sqlSessionFactory1")
public class DataSource1Config {
    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Bean("dataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("sqlSessionFactory1")
    public SqlSessionFactoryBean createSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = null;
        try {
            // 实例SessionFactory
            sqlSessionFactoryBean = new SqlSessionFactoryBean();
            // 配置数据源
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactoryBean;
    }

}
