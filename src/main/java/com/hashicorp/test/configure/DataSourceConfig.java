package com.hashicorp.test.configure;

import com.hashicorp.test.vault.Vault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DataSourceConfig {
    @Autowired
    private Vault vault;
    File file = new File("src/main/resources/application.properties");
    private Properties properties = new Properties();

    @Bean
    public DataSource getDataSource() {
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String login = properties.getProperty("spring.datasource.username");
        String password = vault.getPass();
        String driver = properties.getProperty("spring.datasource.driver-class-name");
        String url = properties.getProperty("spring.datasource.url");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(login);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
