package com.company.web.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class HibernateConfig {
    private final String dbUrl, dbUsername, dbPassword;

    @Autowired
    public HibernateConfig(Environment env) {
        this.dbUrl = env.getProperty("database.url");
        this.dbUsername = env.getProperty("database.username");
        this.dbPassword = env.getProperty("database.password");
    }

    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionfactory = new LocalSessionFactoryBean();
        sessionfactory.setDataSource(dataSource());
        //modelite, koito shte budat svurzani s tablicite
        sessionfactory.setPackagesToScan("com.telerikacademy.web.springdemo.models");
        //hibernate shte raboti na bazata na nastroikite v tozi metod
        sessionfactory.setHibernateProperties(hibernateProperties());
        return sessionfactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //zadavame s kakuv driver da raboti
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");

        return hibernateProperties;
    }
}
