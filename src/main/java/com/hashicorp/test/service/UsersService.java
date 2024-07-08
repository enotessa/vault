package com.hashicorp.test.service;

import com.hashicorp.test.DAO.User;
import com.hashicorp.test.DAO.UserTable;
import com.hashicorp.test.repository.UsersCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Transactional
    public User getUserById(String id) {
        return usersCrudRepository.getUserById(id);
    }

    @Transactional
    public List<UserTable> getUsers(){
        List<User> userList = usersCrudRepository.findAll();
        return userList.stream().map(user ->
                new UserTable(user.getName()+" "+user.getSurname()+" "+user.getPatronymic(), user.getId())).toList();

    }

}
