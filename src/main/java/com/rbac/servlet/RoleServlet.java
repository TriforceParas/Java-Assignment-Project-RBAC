package com.rbac.servlet;

import com.rbac.dao.RoleDAO;
import com.rbac.dao.RoleScreenDAO;
import com.rbac.dao.ScreenDAO;
import com.rbac.model.Role;
import com.rbac.model.RoleScreen;
import com.rbac.model.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoleServlet extends HttpServlet {

    private RoleDAO roleDAO;
    private ScreenDAO screenDAO;
    private RoleScreenDAO roleScreenDAO;

    @Override
    public void init() throws ServletException {
        roleDAO = new RoleDAO();
        screenDAO = new ScreenDAO();
        roleScreenDAO = new RoleScreenDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("edit".equals(action)) {
                int roleId = Integer.parseInt(request.getParameter("id"));
                Role role = roleDAO.getById(roleId);
                List<RoleScreen> permissions = roleScreenDAO.getByRoleId(roleId);

                request.setAttribute("role", role);
                request.setAttribute("permissions", permissions);
                request.getRequestDispatcher("/edit-role.jsp").forward(request, response);
                return;
            }

            List<Role> roles = roleDAO.getAll();
            request.setAttribute("roles", roles);

            List<Screen> allScreens = screenDAO.getAll();
            request.setAttribute("allScreens", allScreens);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading roles: " + e.getMessage());
        }

        request.getRequestDispatcher("/roles.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createRole(request);
                    break;
                case "update":
                    updateRole(request);
                    break;
                case "delete":
                    deleteRole(request);
                    break;
                case "updatePermissions":
                    updatePermissions(request);
                    break;
            }

            request.setAttribute("success", "Operation completed successfully");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/roles");
    }

    private void createRole(HttpServletRequest request) throws Exception {
        Role role = new Role();
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));

        int roleId = roleDAO.create(role);

        roleScreenDAO.initializePermissionsForRole(roleId, 0, 0);

        request.setAttribute("roleId", String.valueOf(roleId));
        updatePermissions(request);
    }

    private void updateRole(HttpServletRequest request) throws Exception {

        int roleId = Integer.parseInt(request.getParameter("id"));
        Role role = new Role();
        role.setId(roleId);
        role.setName(request.getParameter("name"));
        role.setDescription(request.getParameter("description"));
        roleDAO.update(role);

        request.setAttribute("roleId", String.valueOf(roleId));
        updatePermissions(request);
    }

    private void deleteRole(HttpServletRequest request) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        roleScreenDAO.deleteByRoleId(id);
        roleDAO.delete(id);
    }

    private void updatePermissions(HttpServletRequest request) throws Exception {
        String roleIdStr = request.getParameter("roleId");
        if (roleIdStr == null) {
            roleIdStr = (String) request.getAttribute("roleId");
        }

        if (roleIdStr == null)
            return;

        int roleId = Integer.parseInt(roleIdStr);
        List<Screen> screens = screenDAO.getAll();

        for (Screen screen : screens) {
            String readParam = request.getParameter("read_" + screen.getId());
            String writeParam = request.getParameter("write_" + screen.getId());

            int readPerm = "on".equals(readParam) || "1".equals(readParam) ? 1 : 0;
            int writePerm = "on".equals(writeParam) || "1".equals(writeParam) ? 1 : 0;

            if (writePerm == 1) {
                readPerm = 1;
            }
            if (readPerm == 0) {
                writePerm = 0;
            }

            roleScreenDAO.updateByRoleAndScreen(roleId, screen.getId(), readPerm, writePerm);
        }
    }
}
