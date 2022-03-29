//package com.example.demoBook;
//
//import javax.sql.DataSource;
//
//import org.h2.jdbcx.JdbcDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:application.properties")
//public class PersistenceContext {
//    @Autowired
//    private Environment environment;
//
//    @Bean
//    public DataSource dataSource() {
//        JdbcDataSource dataSource = new JdbcDataSource();
//        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
//        dataSource.setUser(environment.getRequiredProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
//        return dataSource;
//    }
//}
