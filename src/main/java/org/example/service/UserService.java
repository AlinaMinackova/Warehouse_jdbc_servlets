package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public List<User> listUsers() throws SQLException {
        return dao.findAll();
    }

    public void addUser(String name, String email) throws SQLException {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("email required");
        dao.create(new User(name.trim(), email.trim()));
    }
}