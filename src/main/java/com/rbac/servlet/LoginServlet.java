package com.rbac.servlet;

import com.rbac.dao.EmployeeDAO;
import com.rbac.dao.UserRoleDAO;
import com.rbac.model.Employee;
import com.rbac.model.UserRole;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;
    private UserRoleDAO userRoleDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
        userRoleDAO = new UserRoleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {

            Employee user = employeeDAO.authenticate(username, password);

            if (user != null) {

                List<UserRole> roles = userRoleDAO.getByUserId(user.getId());
                List<String> roleNames = roles.stream()
                        .map(UserRole::getRoleName)
                        .collect(Collectors.toList());

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userRoles", roleNames);

                List<Integer> roleIds = roles.stream()
                        .map(UserRole::getRoleId)
                        .collect(Collectors.toList());

                java.util.Set<String> allowedScreens = new com.rbac.dao.RoleScreenDAO().getAccessibleScreens(roleIds);
                session.setAttribute("allowedScreens", allowedScreens);

                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {

                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
