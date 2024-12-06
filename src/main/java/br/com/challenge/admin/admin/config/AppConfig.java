package br.com.challenge.admin.admin.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = "br.com.challenge.admin.admin")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://fiap-tech-challenge-instance.cpsw6q668tdw.us-east-1.rds.amazonaws.com:3306/fiap_tech_challenge?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername("foo");
        dataSource.setPassword("rHaK9a7vAfw0RPDLYI1Y");
        return dataSource;
    }
}
