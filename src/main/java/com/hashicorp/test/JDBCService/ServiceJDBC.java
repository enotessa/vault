package com.hashicorp.test.JDBCService;

import com.hashicorp.test.DAO.User;
import com.hashicorp.test.DAO.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceJDBC {
    @Autowired
    JDBCConnectorPostgreSql jdbcConnectorPostgreSql;

    String SQL_SELECT_ALL_USERS = "SELECT * FROM users;";
    String SQL_SELECT_USER_BY_ID = "SELECT * FROM users where id = '?';";

    public List<UserTable> getUsers(){
        List<UserTable> result = new ArrayList<>();
        try {
        Connection connection = jdbcConnectorPostgreSql.getJdbcConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String fio = resultSet.getString("surname")
                        + " " + resultSet.getString("name")
                        + " " + resultSet.getString("patronymic");
                String login = resultSet.getString("login");
                UserTable userTable = new UserTable(fio, login);
                result.add(userTable);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User getUserById(String id){
        User user;
        try {
            Connection connection = jdbcConnectorPostgreSql.getJdbcConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getString("id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("login"));
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
