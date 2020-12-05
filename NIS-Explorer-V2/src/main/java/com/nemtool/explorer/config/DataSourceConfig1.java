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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
* mysql database data source config
* @author Masker
* @date 2020.08.29
*/
@Configuration
//mybatis interface path
@MapperScan(basePackages = "com.nemtool.explorer.mapper.mysql", sqlSessionFactoryRef = "dbSqlSessionFactory")
public class DataSourceConfig1 {
  // put into the Spring container
  @Bean(name = "db")
  // Primary database
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.db")
  public DataSource getDataSource1() {
      return DataSourceBuilder.create().build();
  }
  @Bean(name = "dbSqlSessionFactory")
  @Primary
  public SqlSessionFactory dbSqlSessionFactory(@Qualifier("db") DataSource datasource)
          throws Exception {
      SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
      bean.setDataSource(datasource);
      bean.setMapperLocations(
              // mybatis xml path
              new PathMatchingResourcePatternResolver().getResources("classpath*:com/nemtool/explorer/mapper/mysql/*.xml"));
      return bean.getObject();
  }
  @Bean("dbSqlSessionTemplate")
  @Primary
  public SqlSessionTemplate test1sqlsessiontemplate(
          @Qualifier("dbSqlSessionFactory") SqlSessionFactory sessionfactory) {
      return new SqlSessionTemplate(sessionfactory);
  }
}
