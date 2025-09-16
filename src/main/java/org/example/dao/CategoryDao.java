package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CategoryDao {
    private final DataSource pool;


    public List<Category> findAll() throws SQLException {
        String sql = "SELECT id, name FROM category ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Category(rs.getLong("id"), rs.getString("name")));
            }
            return list;
        }
    }

    public Category findById(Long id) throws SQLException {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getLong("id"),
                            rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    public void create(Category category) throws SQLException {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) category.setId(rs.getLong(1));
            }
        }
    }

    public void update(Category category) throws SQLException {
        String sql = "UPDATE category SET name=? WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setLong(2, category.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM category WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Category> search(String keyword) throws SQLException {
        String sql = "SELECT id, name FROM category WHERE LOWER(name) LIKE ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Category> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Category(
                            rs.getLong("id"),
                            rs.getString("name")
                    ));
                }
                return list;
            }
        }
    }
}
