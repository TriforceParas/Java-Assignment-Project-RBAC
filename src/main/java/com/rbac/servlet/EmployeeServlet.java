package com.rbac.servlet;

import com.rbac.dao.EmployeeDAO;
import com.rbac.dao.RoleDAO;
import com.rbac.dao.UserRoleDAO;
import com.rbac.model.Employee;
import com.rbac.model.Role;
import com.rbac.model.UserRole;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EmployeeServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;
    private RoleDAO roleDAO;
    private UserRoleDAO userRoleDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
        roleDAO = new RoleDAO();
        userRoleDAO = new UserRoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            List<Role> roles = roleDAO.getAll();
            request.setAttribute("allRoles", roles);

            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Employee employee = employeeDAO.getById(id);
                List<UserRole> userRoles = userRoleDAO.getByUserId(id);

                request.setAttribute("employee", employee);
                request.setAttribute("userRoles", userRoles);
                request.getRequestDispatcher("/edit-employee.jsp").forward(request, response);
                return;
            }

            List<Employee> employees = employeeDAO.getAll();

            for (Employee emp : employees) {
                List<UserRole> empRoles = userRoleDAO.getByUserId(emp.getId());
                request.setAttribute("roles_" + emp.getId(), empRoles);
            }

            request.setAttribute("employees", employees);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading employees: " + e.getMessage());
        }

        request.getRequestDispatcher("/employees.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createEmployee(request);
                    break;
                case "update":
                    updateEmployee(request);
                    break;
                case "delete":
                    deleteEmployee(request);
                    break;
                case "assignRole":
                    assignRole(request);
                    break;
                case "removeRole":
                    removeRole(request);
                    break;
            }

            request.setAttribute("success", "Operation completed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void createEmployee(HttpServletRequest request) throws Exception {
        Employee employee = new Employee();
        employee.setName(request.getParameter("name"));
        employee.setUsername(request.getParameter("username"));
        employee.setPassword(request.getParameter("password"));

        int employeeId = employeeDAO.create(employee);

        String[] roleIds = request.getParameterValues("roleIds");
        if (roleIds != null) {
            for (String roleId : roleIds) {
                userRoleDAO.create(employeeId, Integer.parseInt(roleId));
            }
        }
    }

    private void updateEmployee(HttpServletRequest request) throws Exception {
        Employee employee = new Employee();
        employee.setId(Integer.parseInt(request.getParameter("id")));
        employee.setName(request.getParameter("name"));
        employee.setUsername(request.getParameter("username"));

        String password = request.getParameter("password");
        if (password != null && !password.trim().isEmpty()) {
            employee.setPassword(password);
        }

        employeeDAO.update(employee);

        userRoleDAO.deleteByUserId(employee.getId());
        String[] roleIds = request.getParameterValues("roleIds");
        if (roleIds != null) {
            for (String roleId : roleIds) {
                userRoleDAO.create(employee.getId(), Integer.parseInt(roleId));
            }
        }
    }

    private void deleteEmployee(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        userRoleDAO.deleteByUserId(id);
        employeeDAO.delete(id);
    }

    private void assignRole(HttpServletRequest request) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        userRoleDAO.create(userId, roleId);
    }

    private void removeRole(HttpServletRequest request) throws Exception {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int roleId = Integer.parseInt(request.getParameter("roleId"));
        userRoleDAO.deleteByUserAndRole(userId, roleId);
    }
}
