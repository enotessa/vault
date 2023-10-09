package com.hashicorp.test.DAO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class UserTable {
    private String fio;
    private String login;

    public UserTable(){}
    public UserTable(String fio, String login) {
        this.fio = fio;
        this.login = login;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
