package com.rbac.filter;

import com.rbac.dao.EmployeeDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private EmployeeDAO employeeDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        employeeDAO = new EmployeeDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        if (requestURI.endsWith(".css") || requestURI.endsWith(".js") ||
                requestURI.endsWith(".png") || requestURI.endsWith(".jpg") ||
                requestURI.endsWith(".ico") || requestURI.contains("/css/") ||
                requestURI.contains("/js/") || requestURI.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            boolean hasEmployees = employeeDAO.hasAnyEmployee();

            if (requestURI.endsWith("/register") || requestURI.endsWith("/register.jsp")) {
                if (!hasEmployees) {
                    chain.doFilter(request, response);
                    return;
                } else {

                    httpResponse.sendRedirect(contextPath + "/login");
                    return;
                }
            }

            if (!hasEmployees) {
                httpResponse.sendRedirect(contextPath + "/register");
                return;
            }

            if (requestURI.endsWith("/login") || requestURI.endsWith("/login.jsp")) {

                if (session != null && session.getAttribute("user") != null) {
                    httpResponse.sendRedirect(contextPath + "/dashboard");
                    return;
                }
                chain.doFilter(request, response);
                return;
            }

            if (session == null || session.getAttribute("user") == null) {
                httpResponse.sendRedirect(contextPath + "/login");
                return;
            }

            chain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Database error: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
