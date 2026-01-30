package com.rbac.dao;

import com.rbac.model.Screen;
import com.rbac.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAO {

    public List<Screen> getAll() throws SQLException {
        List<Screen> screens = new ArrayList<>();
        String sql = "SELECT id, name, url, description, created_at FROM md_screen ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Screen screen = new Screen();
                screen.setId(rs.getInt("id"));
                screen.setName(rs.getString("name"));
                screen.setUrl(rs.getString("url"));
                screen.setDescription(rs.getString("description"));
                screen.setCreatedAt(rs.getTimestamp("created_at"));
                screens.add(screen);
            }
        }
        return screens;
    }

    public Screen getById(int id) throws SQLException {
        String sql = "SELECT id, name, url, description, created_at FROM md_screen WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Screen screen = new Screen();
                    screen.setId(rs.getInt("id"));
                    screen.setName(rs.getString("name"));
                    screen.setUrl(rs.getString("url"));
                    screen.setDescription(rs.getString("description"));
                    screen.setCreatedAt(rs.getTimestamp("created_at"));
                    return screen;
                }
            }
        }
        return null;
    }

    public Screen getByUrl(String url) throws SQLException {
        String sql = "SELECT id, name, url, description, created_at FROM md_screen WHERE url = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, url);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Screen screen = new Screen();
                    screen.setId(rs.getInt("id"));
                    screen.setName(rs.getString("name"));
                    screen.setUrl(rs.getString("url"));
                    screen.setDescription(rs.getString("description"));
                    screen.setCreatedAt(rs.getTimestamp("created_at"));
                    return screen;
                }
            }
        }
        return null;
    }

    public int create(Screen screen) throws SQLException {
        String sql = "INSERT INTO md_screen (name, url, description) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, screen.getName());
            stmt.setString(2, screen.getUrl());
            stmt.setString(3, screen.getDescription());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean update(Screen screen) throws SQLException {
        String sql = "UPDATE md_screen SET name = ?, url = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, screen.getName());
            stmt.setString(2, screen.getUrl());
            stmt.setString(3, screen.getDescription());
            stmt.setInt(4, screen.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM md_screen WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
