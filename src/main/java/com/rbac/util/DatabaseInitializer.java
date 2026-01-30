package com.rbac.util;

import java.sql.*;

public class DatabaseInitializer {

    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            System.out.println("=== Database Initialization Started ===");

            createTables(conn);

            seedData(conn);

            initialized = true;
            System.out.println("=== Database Initialization Completed ===");

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS md_employee (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    name VARCHAR(100) NOT NULL," +
                            "    username VARCHAR(50) UNIQUE NOT NULL," +
                            "    password VARCHAR(255) NOT NULL," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")");
            System.out.println("  ✓ Table md_employee ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS md_role (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    name VARCHAR(50) UNIQUE NOT NULL," +
                            "    description VARCHAR(255)," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")");
            System.out.println("  ✓ Table md_role ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS md_screen (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    name VARCHAR(100) NOT NULL," +
                            "    url VARCHAR(255) NOT NULL," +
                            "    description VARCHAR(255)," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")");
            System.out.println("  ✓ Table md_screen ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS md_role_screen (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    role_id INT NOT NULL," +
                            "    screen_id INT NOT NULL," +
                            "    read_permission INT DEFAULT 0," +
                            "    write_permission INT DEFAULT 0," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "    CONSTRAINT fk_role_screen_role FOREIGN KEY (role_id) REFERENCES md_role(id) ON DELETE CASCADE,"
                            +
                            "    CONSTRAINT fk_role_screen_screen FOREIGN KEY (screen_id) REFERENCES md_screen(id) ON DELETE CASCADE"
                            +
                            ")");
            System.out.println("  ✓ Table md_role_screen ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS md_map_user_role (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    user_id INT NOT NULL," +
                            "    role_id INT NOT NULL," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "    CONSTRAINT fk_map_user_role_user FOREIGN KEY (user_id) REFERENCES md_employee(id) ON DELETE CASCADE,"
                            +
                            "    CONSTRAINT fk_map_user_role_role FOREIGN KEY (role_id) REFERENCES md_role(id) ON DELETE CASCADE"
                            +
                            ")");
            System.out.println("  ✓ Table md_map_user_role ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS students (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    name VARCHAR(100) NOT NULL," +
                            "    email VARCHAR(100)," +
                            "    grade VARCHAR(20)," +
                            "    enrollment_date DATE" +
                            ")");
            System.out.println("  ✓ Table students ready");

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS teachers (" +
                            "    id INT AUTO_INCREMENT PRIMARY KEY," +
                            "    name VARCHAR(100) NOT NULL," +
                            "    email VARCHAR(100)," +
                            "    department VARCHAR(100)," +
                            "    join_date DATE" +
                            ")");
            System.out.println("  ✓ Table teachers ready");
        }
    }

    private static void seedData(Connection conn) throws SQLException {

        if (isTableEmpty(conn, "md_screen")) {
            System.out.println("  Seeding screens...");
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO md_screen (name, url, description) VALUES (?, ?, ?)")) {

                String[][] screens = {
                        { "Employees", "/employees", "Employee management page" },
                        { "Roles", "/roles", "Role and permission management page" },
                        { "Students", "/students", "Student records management" },
                        { "Teachers", "/teachers", "Teacher records management" }
                };

                for (String[] screen : screens) {
                    stmt.setString(1, screen[0]);
                    stmt.setString(2, screen[1]);
                    stmt.setString(3, screen[2]);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("    ✓ Screens seeded");
            }
        }

        if (isTableEmpty(conn, "md_role")) {
            System.out.println("  Seeding roles...");
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO md_role (name, description) VALUES (?, ?)")) {

                stmt.setString(1, "Super_Admin");
                stmt.setString(2, "Full access to all screens and operations");
                stmt.addBatch();

                stmt.setString(1, "Viewer");
                stmt.setString(2, "Read-only access to all screens");
                stmt.addBatch();

                stmt.executeBatch();
                System.out.println("    ✓ Roles seeded");
            }

            setupRolePermissions(conn);
        }

        seedSampleData(conn);
    }

    private static void setupRolePermissions(Connection conn) throws SQLException {
        System.out.println("  Setting up role permissions...");

        int superAdminId = getRoleIdByName(conn, "Super_Admin");
        int viewerId = getRoleIdByName(conn, "Viewer");

        if (superAdminId == -1 || viewerId == -1) {
            System.err.println("    ✗ Could not find roles for permission setup");
            return;
        }

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id FROM md_screen")) {

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO md_role_screen (role_id, screen_id, read_permission, write_permission) VALUES (?, ?, ?, ?)")) {

                while (rs.next()) {
                    int screenId = rs.getInt("id");

                    insertStmt.setInt(1, superAdminId);
                    insertStmt.setInt(2, screenId);
                    insertStmt.setInt(3, 1);
                    insertStmt.setInt(4, 1);
                    insertStmt.addBatch();

                    insertStmt.setInt(1, viewerId);
                    insertStmt.setInt(2, screenId);
                    insertStmt.setInt(3, 1);
                    insertStmt.setInt(4, 0);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
                System.out.println("    ✓ Role permissions configured");
            }
        }
    }

    private static void seedSampleData(Connection conn) throws SQLException {

        if (isTableEmpty(conn, "students")) {
            System.out.println("  Seeding students...");
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)")) {

                String[][] students = {
                        { "John Smith", "john.smith@example.com", "A" },
                        { "Emma Wilson", "emma.wilson@example.com", "B+" },
                        { "Michael Brown", "michael.brown@example.com", "A-" }
                };

                for (String[] student : students) {
                    stmt.setString(1, student[0]);
                    stmt.setString(2, student[1]);
                    stmt.setString(3, student[2]);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("    ✓ Students seeded");
            }
        }

        if (isTableEmpty(conn, "teachers")) {
            System.out.println("  Seeding teachers...");
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO teachers (name, email, department) VALUES (?, ?, ?)")) {

                String[][] teachers = {
                        { "Dr. Sarah Johnson", "sarah.johnson@example.com", "Computer Science" },
                        { "Prof. David Lee", "david.lee@example.com", "Mathematics" },
                        { "Ms. Jennifer Davis", "jennifer.davis@example.com", "Physics" }
                };

                for (String[] teacher : teachers) {
                    stmt.setString(1, teacher[0]);
                    stmt.setString(2, teacher[1]);
                    stmt.setString(3, teacher[2]);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("    ✓ Teachers seeded");
            }
        }
    }

    private static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private static int getRoleIdByName(Connection conn, String roleName) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM md_role WHERE name = ?")) {
            stmt.setString(1, roleName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }
}
