<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Employees - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Employee Management" />
                    </jsp:include>

                    <div class="content-area">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="bi bi-exclamation-circle me-2"></i>${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-people-fill me-2"></i>All Employees
                                </h5>
                                <c:if test="${canWrite}">
                                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                                        <i class="bi bi-plus-circle me-2"></i>Add Employee
                                    </button>
                                </c:if>
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Username</th>
                                                <th>Roles</th>
                                                <c:if test="${canWrite}">
                                                    <th style="width: 120px;">Actions</th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="emp" items="${employees}">
                                                <tr>
                                                    <td>${emp.id}</td>
                                                    <td>
                                                        <div class="d-flex align-items-center gap-2">
                                                            <div class="user-avatar-circle"
                                                                style="width: 32px; height: 32px; font-size: 0.875rem;">
                                                                ${emp.name.substring(0, 1).toUpperCase()}
                                                            </div>
                                                            ${emp.name}
                                                        </div>
                                                    </td>
                                                    <td><code>${emp.username}</code></td>
                                                    <td>
                                                        <c:forEach var="ur"
                                                            items="${requestScope['roles_'.concat(emp.id)]}">
                                                            <span class="badge bg-primary">${ur.roleName}</span>
                                                        </c:forEach>
                                                        <c:if test="${empty requestScope['roles_'.concat(emp.id)]}">
                                                            <span class="text-muted fst-italic">No role assigned</span>
                                                        </c:if>
                                                    </td>
                                                    <c:if test="${canWrite}">
                                                        <td>
                                                            <a href="${pageContext.request.contextPath}/employees?action=edit&id=${emp.id}"
                                                                class="btn btn-sm btn-outline-primary btn-icon me-1"
                                                                title="Edit">
                                                                <i class="bi bi-pencil"></i>
                                                            </a>
                                                            <form action="${pageContext.request.contextPath}/employees"
                                                                method="post" class="d-inline"
                                                                onsubmit="return confirm('Delete this employee?')">
                                                                <input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="id" value="${emp.id}">
                                                                <button type="submit"
                                                                    class="btn btn-sm btn-outline-danger btn-icon"
                                                                    title="Delete">
                                                                    <i class="bi bi-trash"></i>
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty employees}">
                                                <tr>
                                                    <td colspan="${canWrite ? 5 : 4}"
                                                        class="text-center py-4 text-muted">
                                                        <i class="bi bi-people" style="font-size: 2rem;"></i>
                                                        <p class="mb-0 mt-2">No employees found</p>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <%@ include file="/common/footer.jsp" %>
                    </div>
                </div>

                <div class="modal fade" id="addModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title"><i class="bi bi-person-plus me-2"></i>Add New Employee</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <form action="${pageContext.request.contextPath}/employees" method="post">
                                <input type="hidden" name="action" value="create">
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="name" class="form-label">Full Name</label>
                                        <input type="text" class="form-control" id="name" name="name" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="username" class="form-label">Username</label>
                                        <input type="text" class="form-control" id="username" name="username" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="password" class="form-label">Password</label>
                                        <input type="password" class="form-control" id="password" name="password"
                                            required minlength="6">
                                    </div>
                                    <div class="mb-3">
                                        <label for="roleIds" class="form-label">Assign Roles</label>
                                        <select class="form-select" id="roleIds" name="roleIds" multiple size="4">
                                            <c:forEach var="role" items="${allRoles}">
                                                <option value="${role.id}">${role.name}</option>
                                            </c:forEach>
                                        </select>
                                        <div class="form-text">Hold Ctrl to select multiple roles.</div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-check-circle me-1"></i>Create Employee
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>