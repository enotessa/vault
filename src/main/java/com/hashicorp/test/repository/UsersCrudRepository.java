package com.hashicorp.test.repository;

import com.hashicorp.test.DAO.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersCrudRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User getUserById(String id);

}
