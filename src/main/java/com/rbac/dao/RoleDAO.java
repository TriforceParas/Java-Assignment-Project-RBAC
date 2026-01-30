package com.rbac.dao;

import com.rbac.model.Role;
import com.rbac.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public List<Role> getAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT id, name, description, created_at FROM md_role ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                role.setCreatedAt(rs.getTimestamp("created_at"));
                roles.add(role);
            }
        }
        return roles;
    }

    public Role getById(int id) throws SQLException {
        String sql = "SELECT id, name, description, created_at FROM md_role WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                    role.setCreatedAt(rs.getTimestamp("created_at"));
                    return role;
                }
            }
        }
        return null;
    }

    public Role getByName(String name) throws SQLException {
        String sql = "SELECT id, name, description, created_at FROM md_role WHERE name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    role.setDescription(rs.getString("description"));
                    role.setCreatedAt(rs.getTimestamp("created_at"));
                    return role;
                }
            }
        }
        return null;
    }

    public int create(Role role) throws SQLException {
        String sql = "INSERT INTO md_role (name, description) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, role.getName());
            stmt.setString(2, role.getDescription());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean update(Role role) throws SQLException {
        String sql = "UPDATE md_role SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role.getName());
            stmt.setString(2, role.getDescription());
            stmt.setInt(3, role.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM md_role WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM md_role";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
