package com.hashicorp.test.JDBCService;

import com.hashicorp.test.vault.Vault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class JDBCConnectorPostgreSql {
    @Autowired
    private Vault vault;
    private Properties properties = new Properties();
    File file = new File("src/main/resources/application.properties");
    Connection jdbcConnection;

    @Autowired
    public JDBCConnectorPostgreSql(ApplicationContext applicationContext) {
        this.vault = applicationContext.getBean(Vault.class);
        this.jdbcConnection = getConnection();
        try {
            this.properties.load(new FileReader(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getJdbcConnection() {
        return jdbcConnection;
    }

//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
//        dataSource.setUsername("guest_user");
//        dataSource.setPassword("guest_password");
//
//        return dataSource;
//    }

    public Connection getConnection() {
        String password = vault.getPass();
        //String login = properties.getProperty("db.login");
        String login = "jdbc:postgresql://127.0.0.1:5432/dbusers";
        //String host = properties.getProperty("db.host");
        String host = "postgres";
        try (Connection conn = DriverManager.getConnection(
                host, login, password)) { // TODO DataSource

            if (conn != null) {
                return conn;
            } else {
                System.out.println("Failed to make connection!");
                return null;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
