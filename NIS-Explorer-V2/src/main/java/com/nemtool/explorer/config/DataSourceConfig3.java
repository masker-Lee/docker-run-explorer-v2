package com.nemtool.explorer.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
* h2mem database data source config
* @author Masker
* @date 2020.08.29
*/
@Configuration
@MapperScan(basePackages = "com.nemtool.explorer.h2mem", sqlSessionFactoryRef = "db3SqlSessionFactory")
public class DataSourceConfig3 {
    @Bean(name = "db3")
    @ConfigurationProperties(prefix = "spring.datasource.db3")
    public DataSource getDataSource3() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "db3SqlSessionFactory")
    public SqlSessionFactory db3SqlSessionFactory(@Qualifier("db3") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/nemtool/explorer/h2mem/*.xml"));
        return bean.getObject();
    }
    @Bean("db3SqlSessionTemplate")
    public SqlSessionTemplate test3sqlsessiontemplate(
            @Qualifier("db3SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}

