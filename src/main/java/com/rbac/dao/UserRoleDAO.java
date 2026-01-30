package com.rbac.dao;

import com.rbac.model.UserRole;
import com.rbac.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    public List<UserRole> getAll() throws SQLException {
        List<UserRole> mappings = new ArrayList<>();
        String sql = "SELECT ur.id, ur.user_id, ur.role_id, ur.created_at, " +
                "e.name as user_name, r.name as role_name " +
                "FROM md_map_user_role ur " +
                "JOIN md_employee e ON ur.user_id = e.id " +
                "JOIN md_role r ON ur.role_id = r.id " +
                "ORDER BY ur.user_id, ur.role_id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UserRole mapping = new UserRole();
                mapping.setId(rs.getInt("id"));
                mapping.setUserId(rs.getInt("user_id"));
                mapping.setRoleId(rs.getInt("role_id"));
                mapping.setCreatedAt(rs.getTimestamp("created_at"));
                mapping.setUserName(rs.getString("user_name"));
                mapping.setRoleName(rs.getString("role_name"));
                mappings.add(mapping);
            }
        }
        return mappings;
    }

    public List<UserRole> getByUserId(int userId) throws SQLException {
        List<UserRole> mappings = new ArrayList<>();
        String sql = "SELECT ur.id, ur.user_id, ur.role_id, ur.created_at, " +
                "e.name as user_name, r.name as role_name " +
                "FROM md_map_user_role ur " +
                "JOIN md_employee e ON ur.user_id = e.id " +
                "JOIN md_role r ON ur.role_id = r.id " +
                "WHERE ur.user_id = ? " +
                "ORDER BY ur.role_id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserRole mapping = new UserRole();
                    mapping.setId(rs.getInt("id"));
                    mapping.setUserId(rs.getInt("user_id"));
                    mapping.setRoleId(rs.getInt("role_id"));
                    mapping.setCreatedAt(rs.getTimestamp("created_at"));
                    mapping.setUserName(rs.getString("user_name"));
                    mapping.setRoleName(rs.getString("role_name"));
                    mappings.add(mapping);
                }
            }
        }
        return mappings;
    }

    public List<Integer> getRoleIdsByUserId(int userId) throws SQLException {
        List<Integer> roleIds = new ArrayList<>();
        String sql = "SELECT role_id FROM md_map_user_role WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roleIds.add(rs.getInt("role_id"));
                }
            }
        }
        return roleIds;
    }

    public int create(int userId, int roleId) throws SQLException {
        String sql = "INSERT INTO md_map_user_role (user_id, role_id) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM md_map_user_role WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteByUserId(int userId) throws SQLException {
        String sql = "DELETE FROM md_map_user_role WHERE user_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteByUserAndRole(int userId, int roleId) throws SQLException {
        String sql = "DELETE FROM md_map_user_role WHERE user_id = ? AND role_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hasRole(int userId, int roleId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM md_map_user_role WHERE user_id = ? AND role_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
