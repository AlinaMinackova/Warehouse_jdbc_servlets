package org.example.dao;

import org.example.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final DataSource pool;

    public UserDao(DataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
            return list;
        }
    }

    public void create(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) user.setId(rs.getInt(1));
            }
        }
    }
}
