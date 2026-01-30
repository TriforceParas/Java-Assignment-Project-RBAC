package com.rbac.dao;

import com.rbac.model.RoleScreen;
import com.rbac.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleScreenDAO {

    public List<RoleScreen> getAll() throws SQLException {
        List<RoleScreen> mappings = new ArrayList<>();
        String sql = "SELECT rs.id, rs.role_id, rs.screen_id, rs.read_permission, rs.write_permission, " +
                "r.name as role_name, s.name as screen_name, s.url as screen_url " +
                "FROM md_role_screen rs " +
                "JOIN md_role r ON rs.role_id = r.id " +
                "JOIN md_screen s ON rs.screen_id = s.id " +
                "ORDER BY rs.role_id, rs.screen_id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RoleScreen mapping = new RoleScreen();
                mapping.setId(rs.getInt("id"));
                mapping.setRoleId(rs.getInt("role_id"));
                mapping.setScreenId(rs.getInt("screen_id"));
                mapping.setReadPermission(rs.getInt("read_permission"));
                mapping.setWritePermission(rs.getInt("write_permission"));
                mapping.setRoleName(rs.getString("role_name"));
                mapping.setScreenName(rs.getString("screen_name"));
                mapping.setScreenUrl(rs.getString("screen_url"));
                mappings.add(mapping);
            }
        }
        return mappings;
    }

    public List<RoleScreen> getByRoleId(int roleId) throws SQLException {
        List<RoleScreen> mappings = new ArrayList<>();
        String sql = "SELECT rs.id, rs.role_id, rs.screen_id, rs.read_permission, rs.write_permission, " +
                "r.name as role_name, s.name as screen_name, s.url as screen_url " +
                "FROM md_role_screen rs " +
                "JOIN md_role r ON rs.role_id = r.id " +
                "JOIN md_screen s ON rs.screen_id = s.id " +
                "WHERE rs.role_id = ? " +
                "ORDER BY rs.screen_id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RoleScreen mapping = new RoleScreen();
                    mapping.setId(rs.getInt("id"));
                    mapping.setRoleId(rs.getInt("role_id"));
                    mapping.setScreenId(rs.getInt("screen_id"));
                    mapping.setReadPermission(rs.getInt("read_permission"));
                    mapping.setWritePermission(rs.getInt("write_permission"));
                    mapping.setRoleName(rs.getString("role_name"));
                    mapping.setScreenName(rs.getString("screen_name"));
                    mapping.setScreenUrl(rs.getString("screen_url"));
                    mappings.add(mapping);
                }
            }
        }
        return mappings;
    }

    public RoleScreen getByRoleAndUrl(int roleId, String url) throws SQLException {
        String sql = "SELECT rs.id, rs.role_id, rs.screen_id, rs.read_permission, rs.write_permission, " +
                "r.name as role_name, s.name as screen_name, s.url as screen_url " +
                "FROM md_role_screen rs " +
                "JOIN md_role r ON rs.role_id = r.id " +
                "JOIN md_screen s ON rs.screen_id = s.id " +
                "WHERE rs.role_id = ? AND s.url = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            stmt.setString(2, url);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RoleScreen mapping = new RoleScreen();
                    mapping.setId(rs.getInt("id"));
                    mapping.setRoleId(rs.getInt("role_id"));
                    mapping.setScreenId(rs.getInt("screen_id"));
                    mapping.setReadPermission(rs.getInt("read_permission"));
                    mapping.setWritePermission(rs.getInt("write_permission"));
                    mapping.setRoleName(rs.getString("role_name"));
                    mapping.setScreenName(rs.getString("screen_name"));
                    mapping.setScreenUrl(rs.getString("screen_url"));
                    return mapping;
                }
            }
        }
        return null;
    }

    public int create(RoleScreen mapping) throws SQLException {
        String sql = "INSERT INTO md_role_screen (role_id, screen_id, read_permission, write_permission) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mapping.getRoleId());
            stmt.setInt(2, mapping.getScreenId());
            stmt.setInt(3, mapping.getReadPermission());
            stmt.setInt(4, mapping.getWritePermission());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean update(RoleScreen mapping) throws SQLException {
        String sql = "UPDATE md_role_screen SET read_permission = ?, write_permission = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mapping.getReadPermission());
            stmt.setInt(2, mapping.getWritePermission());
            stmt.setInt(3, mapping.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateByRoleAndScreen(int roleId, int screenId, int readPerm, int writePerm) throws SQLException {
        String sql = "UPDATE md_role_screen SET read_permission = ?, write_permission = ? " +
                "WHERE role_id = ? AND screen_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, readPerm);
            stmt.setInt(2, writePerm);
            stmt.setInt(3, roleId);
            stmt.setInt(4, screenId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM md_role_screen WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteByRoleId(int roleId) throws SQLException {
        String sql = "DELETE FROM md_role_screen WHERE role_id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            return stmt.executeUpdate() > 0;
        }
    }

    public void initializePermissionsForRole(int roleId, int readPerm, int writePerm) throws SQLException {
        String sql = "INSERT INTO md_role_screen (role_id, screen_id, read_permission, write_permission) " +
                "SELECT ?, id, ?, ? FROM md_screen " +
                "WHERE NOT EXISTS (SELECT 1 FROM md_role_screen WHERE role_id = ? AND screen_id = md_screen.id)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roleId);
            stmt.setInt(2, readPerm);
            stmt.setInt(3, writePerm);
            stmt.setInt(4, roleId);
            stmt.executeUpdate();
        }
    }

    public java.util.Set<String> getAccessibleScreens(List<Integer> roleIds) throws SQLException {
        java.util.Set<String> urls = new java.util.HashSet<>();
        if (roleIds == null || roleIds.isEmpty()) {
            return urls;
        }

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < roleIds.size(); i++) {
            if (i > 0)
                placeholders.append(",");
            placeholders.append("?");
        }

        String sql = "SELECT DISTINCT s.url " +
                "FROM md_role_screen rs " +
                "JOIN md_screen s ON rs.screen_id = s.id " +
                "WHERE rs.read_permission = 1 AND rs.role_id IN (" + placeholders + ")";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < roleIds.size(); i++) {
                stmt.setInt(i + 1, roleIds.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    urls.add(rs.getString("url"));
                }
            }
        }
        return urls;
    }
}
