package com.hashicorp.test.DAO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
public class UserTable {
    private String fio;
    private String login;

    public UserTable(){}
    public UserTable(String fio, String login) {
        this.fio = fio;
        this.login = login;
    }
}
