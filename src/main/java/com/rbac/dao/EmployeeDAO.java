package com.rbac.dao;

import com.rbac.model.Employee;
import com.rbac.util.DatabaseUtil;
import com.rbac.util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, name, username, password, created_at FROM md_employee ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setUsername(rs.getString("username"));
                emp.setPassword(rs.getString("password"));
                emp.setCreatedAt(rs.getTimestamp("created_at"));
                employees.add(emp);
            }
        }
        return employees;
    }

    public Employee getById(int id) throws SQLException {
        String sql = "SELECT id, name, username, password, created_at FROM md_employee WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setUsername(rs.getString("username"));
                    emp.setPassword(rs.getString("password"));
                    emp.setCreatedAt(rs.getTimestamp("created_at"));
                    return emp;
                }
            }
        }
        return null;
    }

    public Employee getByUsername(String username) throws SQLException {
        String sql = "SELECT id, name, username, password, created_at FROM md_employee WHERE username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setUsername(rs.getString("username"));
                    emp.setPassword(rs.getString("password"));
                    emp.setCreatedAt(rs.getTimestamp("created_at"));
                    return emp;
                }
            }
        }
        return null;
    }

    public int create(Employee employee) throws SQLException {
        String sql = "INSERT INTO md_employee (name, username, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getUsername());
            stmt.setString(3, PasswordUtil.hashPassword(employee.getPassword()));

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public boolean update(Employee employee) throws SQLException {
        String sql;
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            sql = "UPDATE md_employee SET name = ?, username = ?, password = ? WHERE id = ?";
        } else {
            sql = "UPDATE md_employee SET name = ?, username = ? WHERE id = ?";
        }

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getUsername());

            if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
                stmt.setString(3, PasswordUtil.hashPassword(employee.getPassword()));
                stmt.setInt(4, employee.getId());
            } else {
                stmt.setInt(3, employee.getId());
            }

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM md_employee WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean hasAnyEmployee() throws SQLException {
        String sql = "SELECT COUNT(*) FROM md_employee";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public Employee authenticate(String username, String password) throws SQLException {
        Employee employee = getByUsername(username);
        if (employee != null && PasswordUtil.verifyPassword(password, employee.getPassword())) {
            return employee;
        }
        return null;
    }

    public int getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM md_employee";

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
