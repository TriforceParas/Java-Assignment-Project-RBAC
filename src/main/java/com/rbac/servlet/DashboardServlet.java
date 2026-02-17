package com.rbac.servlet;

import com.rbac.dao.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {

    private EmployeeDAO employeeDAO;
    private RoleDAO roleDAO;
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        employeeDAO = new EmployeeDAO();
        roleDAO = new RoleDAO();
        studentDAO = new StudentDAO();
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            request.setAttribute("employeeCount", employeeDAO.getCount());
            request.setAttribute("roleCount", roleDAO.getCount());
            request.setAttribute("studentCount", studentDAO.getCount());
            request.setAttribute("teacherCount", teacherDAO.getCount());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard data");
        }

        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}
