<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Roles - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Role & Permission Management" />
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
                                    <i class="bi bi-person-badge-fill me-2"></i>Roles
                                </h5>
                                <c:if test="${canWrite}">
                                    <button class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                        data-bs-target="#addRoleModal">
                                        <i class="bi bi-plus-circle me-1"></i>Add Role
                                    </button>
                                </c:if>
                            </div>
                            <div class="card-body p-0">
                                <div class="table-responsive">
                                    <table class="table table-hover mb-0">
                                        <thead>
                                            <tr>
                                                <th>Role Name</th>
                                                <th>Description</th>
                                                <c:if test="${canWrite}">
                                                    <th style="width: 150px;">Actions</th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="role" items="${roles}">
                                                <tr>
                                                    <td><span class="badge badge-role">${role.name}</span></td>
                                                    <td>${role.description}</td>
                                                    <c:if test="${canWrite}">
                                                        <td>
                                                            <a href="${pageContext.request.contextPath}/roles?action=edit&id=${role.id}"
                                                                class="btn btn-sm btn-info btn-icon"
                                                                title="Edit Role & Permissions">
                                                                <i class="bi bi-pencil-square"></i>
                                                            </a>
                                                            <form action="${pageContext.request.contextPath}/roles"
                                                                method="post" class="d-inline"
                                                                onsubmit="return confirm('Delete this role?')">
                                                                <input type="hidden" name="action" value="delete">
                                                                <input type="hidden" name="id" value="${role.id}">
                                                                <button type="submit"
                                                                    class="btn btn-sm btn-danger btn-icon"
                                                                    title="Delete">
                                                                    <i class="bi bi-trash"></i>
                                                                </button>
                                                            </form>
                                                        </td>
                                                    </c:if>
                                                </tr>
                                            </c:forEach>
                                            <c:if test="${empty roles}">
                                                <tr>
                                                    <td colspan="3" class="text-center py-4">
                                                        <i class="bi bi-person-badge" style="font-size: 2rem;"></i>
                                                        <p class="mb-0 mt-2">No roles found</p>
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

                <div class="modal fade" id="addRoleModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Create New Role</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <form action="${pageContext.request.contextPath}/roles" method="post">
                                <input type="hidden" name="action" value="create">
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label class="form-label">Role Name</label>
                                        <input type="text" class="form-control" name="name" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Description</label>
                                        <textarea class="form-control" name="description" rows="2"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" class="btn btn-primary">Create Role</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>