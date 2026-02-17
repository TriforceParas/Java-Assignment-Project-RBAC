package com.rbac.servlet;

import com.rbac.dao.StudentDAO;
import com.rbac.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Student student = studentDAO.getById(id);
                request.setAttribute("student", student);
                request.getRequestDispatcher("/edit-student.jsp").forward(request, response);
                return;
            }

            List<Student> students = studentDAO.getAll();
            request.setAttribute("students", students);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading students: " + e.getMessage());
        }

        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    Student newStudent = new Student();
                    newStudent.setName(request.getParameter("name"));
                    newStudent.setEmail(request.getParameter("email"));
                    newStudent.setGrade(request.getParameter("grade"));
                    studentDAO.create(newStudent);
                    break;

                case "update":
                    Student updateStudent = new Student();
                    updateStudent.setId(Integer.parseInt(request.getParameter("id")));
                    updateStudent.setName(request.getParameter("name"));
                    updateStudent.setEmail(request.getParameter("email"));
                    updateStudent.setGrade(request.getParameter("grade"));
                    studentDAO.update(updateStudent);
                    break;

                case "delete":
                    int id = Integer.parseInt(request.getParameter("id"));
                    studentDAO.delete(id);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/students");
    }
}
