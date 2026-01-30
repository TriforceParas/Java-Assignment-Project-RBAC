package com.rbac.servlet;

import com.rbac.dao.TeacherDAO;
import com.rbac.model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TeacherServlet extends HttpServlet {

    private TeacherDAO teacherDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Teacher teacher = teacherDAO.getById(id);
                request.setAttribute("teacher", teacher);
                request.getRequestDispatcher("/edit-teacher.jsp").forward(request, response);
                return;
            }

            List<Teacher> teachers = teacherDAO.getAll();
            request.setAttribute("teachers", teachers);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading teachers: " + e.getMessage());
        }

        request.getRequestDispatcher("/teachers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    Teacher newTeacher = new Teacher();
                    newTeacher.setName(request.getParameter("name"));
                    newTeacher.setEmail(request.getParameter("email"));
                    newTeacher.setDepartment(request.getParameter("department"));
                    teacherDAO.create(newTeacher);
                    break;

                case "update":
                    Teacher updateTeacher = new Teacher();
                    updateTeacher.setId(Integer.parseInt(request.getParameter("id")));
                    updateTeacher.setName(request.getParameter("name"));
                    updateTeacher.setEmail(request.getParameter("email"));
                    updateTeacher.setDepartment(request.getParameter("department"));
                    teacherDAO.update(updateTeacher);
                    break;

                case "delete":
                    int id = Integer.parseInt(request.getParameter("id"));
                    teacherDAO.delete(id);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/teachers");
    }
}
