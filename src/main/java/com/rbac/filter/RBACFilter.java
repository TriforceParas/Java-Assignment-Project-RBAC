package com.rbac.filter;

import com.rbac.dao.RoleScreenDAO;
import com.rbac.dao.UserRoleDAO;
import com.rbac.model.Employee;
import com.rbac.model.RoleScreen;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RBACFilter implements Filter {

    private UserRoleDAO userRoleDAO;
    private RoleScreenDAO roleScreenDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userRoleDAO = new UserRoleDAO();
        roleScreenDAO = new RoleScreenDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        Employee user = (Employee) session.getAttribute("user");
        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String screenUrl = requestURI.substring(contextPath.length());

        try {

            List<Integer> roleIds = userRoleDAO.getRoleIdsByUserId(user.getId());

            if (roleIds.isEmpty()) {

                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "You don't have any roles assigned. Contact administrator.");
                return;
            }

            boolean canRead = false;
            boolean canWrite = false;

            for (int roleId : roleIds) {
                RoleScreen permission = roleScreenDAO.getByRoleAndUrl(roleId, screenUrl);
                if (permission != null) {

                    if (permission.canRead()) {
                        canRead = true;
                    }
                    if (permission.canWrite()) {
                        canWrite = true;
                    }
                }
            }

            if (!canRead) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "You don't have permission to access this page.");
                return;
            }

            request.setAttribute("canRead", canRead);
            request.setAttribute("canWrite", canWrite);

            chain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error checking permissions: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
