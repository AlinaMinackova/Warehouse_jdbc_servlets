package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductDao {
    private final DataSource pool;


    public List<Product> findAll() throws SQLException {
        String sql = "SELECT id, name, manufacturer_id, category_id, image_url, life_days, weight, composition FROM product ORDER BY id";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("manufacturer_id"),
                        rs.getLong("category_id"),
                        rs.getString("image_url"),
                        rs.getInt("life_days"),
                        BigDecimal.valueOf(rs.getDouble("weight")),
                        rs.getString("composition"),
                        null, // manufacturer пока null
                        null  // category пока null
                ));
            }
            return list;
        }
    }

    public Product findById(Long id) throws SQLException {
        String sql = "SELECT id, name, manufacturer_id, category_id, image_url, life_days, weight, composition FROM product WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getLong("manufacturer_id"),
                            rs.getLong("category_id"),
                            rs.getString("image_url"),
                            rs.getInt("life_days"),
                            BigDecimal.valueOf(rs.getDouble("weight")),
                            rs.getString("composition"),
                            null, // manufacturer пока null
                            null
                    );
                }
            }
        }
        return null;
    }

    public void create(Product product) throws SQLException {
        String sql = "INSERT INTO product (name, manufacturer_id, category_id, image_url, life_days, weight, composition) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getManufacturer().getId());
            ps.setLong(3, product.getCategory().getId());
            ps.setString(4, product.getImageUrl());
            ps.setInt(5, product.getLifeDays());
            ps.setBigDecimal(6, product.getWeight());
            ps.setString(7, product.getComposition());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) product.setId(rs.getLong(1));
            }
        }
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE product SET name=?, manufacturer_id=?, category_id=?, image_url=?, life_days=?, weight=?, composition=? WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getManufacturer().getId());
            ps.setLong(3, product.getCategory().getId());
            ps.setString(4, product.getImageUrl());
            ps.setInt(5, product.getLifeDays());
            ps.setBigDecimal(6, product.getWeight());
            ps.setString(7, product.getComposition());
            ps.setLong(8, product.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Product> findFiltered(Long manufacturerId, Long categoryId) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT id, name, manufacturer_id, category_id, image_url, life_days, weight, composition " +
                        "FROM product WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (manufacturerId != null) {
            sql.append(" AND manufacturer_id = ?");
            params.add(manufacturerId);
        }

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        sql.append(" ORDER BY id");

        try (Connection conn = pool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Привязываем параметры
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Product> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Product(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getLong("manufacturer_id"),
                            rs.getLong("category_id"),
                            rs.getString("image_url"),
                            rs.getInt("life_days"),
                            BigDecimal.valueOf(rs.getDouble("weight")),
                            rs.getString("composition"),
                            null, // manufacturer пока null
                            null  // category пока null
                    ));
                }
                return list;
            }
        }
    }



}
