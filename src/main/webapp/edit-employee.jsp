<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Edit Employee - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Edit Employee" />
                    </jsp:include>

                    <div class="content-area">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-pencil me-2"></i>Edit Employee: ${employee.name}
                                </h5>
                                <a href="${pageContext.request.contextPath}/employees" class="btn btn-secondary btn-sm">
                                    <i class="bi bi-arrow-left me-1"></i>Back to List
                                </a>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/employees" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${employee.id}">

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="name" class="form-label">Full Name</label>
                                            <input type="text" class="form-control" id="name" name="name"
                                                value="${employee.name}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="username" class="form-label">Username</label>
                                            <input type="text" class="form-control" id="username" name="username"
                                                value="${employee.username}" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="password" class="form-label">
                                                New Password <small class="text-muted">(leave blank to keep
                                                    current)</small>
                                            </label>
                                            <input type="password" class="form-control" id="password" name="password"
                                                minlength="6">
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="roleIds" class="form-label">Assign Roles</label>
                                            <select class="form-select" id="roleIds" name="roleIds" multiple size="4">
                                                <c:forEach var="role" items="${allRoles}">
                                                    <c:set var="isSelected" value="false" />
                                                    <c:forEach var="ur" items="${userRoles}">
                                                        <c:if test="${ur.roleId == role.id}">
                                                            <c:set var="isSelected" value="true" />
                                                        </c:if>
                                                    </c:forEach>
                                                    <option value="${role.id}" ${isSelected ? 'selected' : '' }>
                                                        ${role.name}</option>
                                                </c:forEach>
                                            </select>
                                            <div class="form-text">Hold Ctrl to select multiple roles.</div>
                                        </div>
                                    </div>

                                    <div class="d-flex gap-2">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-circle me-1"></i>Update Employee
                                        </button>
                                        <a href="${pageContext.request.contextPath}/employees"
                                            class="btn btn-secondary">Cancel</a>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <%@ include file="/common/footer.jsp" %>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>