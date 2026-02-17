<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Edit Role - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Edit Role & Permissions" />
                    </jsp:include>

                    <div class="content-area">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-pencil me-2"></i>Edit Role: ${role.name}
                                </h5>
                                <a href="${pageContext.request.contextPath}/roles" class="btn btn-secondary btn-sm">
                                    <i class="bi bi-arrow-left me-1"></i>Back to Roles
                                </a>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/roles" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${role.id}">
                                    <input type="hidden" name="roleId" value="${role.id}">

                                    <div class="row mb-4">
                                        <div class="col-md-6 mb-3">
                                            <label for="name" class="form-label">Role Name</label>
                                            <input type="text" class="form-control" id="name" name="name"
                                                value="${role.name}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="description" class="form-label">Description</label>
                                            <input type="text" class="form-control" id="description" name="description"
                                                value="${role.description}">
                                        </div>
                                    </div>

                                    <h6 class="mb-3"><i class="bi bi-shield-check me-2"></i>Screen Permissions</h6>
                                    <div class="table-responsive border rounded">
                                        <table class="table table-sm mb-0">
                                            <thead class="table-light">
                                                <tr>
                                                    <th>Screen / Page</th>
                                                    <th class="text-center" style="width: 100px;">Read</th>
                                                    <th class="text-center" style="width: 100px;">Write</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="perm" items="${permissions}">
                                                    <tr>
                                                        <td>
                                                            <div class="fw-medium">${perm.screenName}</div>
                                                            <small class="text-muted">${perm.screenUrl}</small>
                                                        </td>
                                                        <td class="text-center align-middle">
                                                            <div class="form-check d-inline-block">
                                                                <input class="form-check-input" type="checkbox"
                                                                    name="read_${perm.screenId}"
                                                                    id="read_${perm.screenId}" ${perm.readPermission==1
                                                                    ? 'checked' : '' }>
                                                            </div>
                                                        </td>
                                                        <td class="text-center align-middle">
                                                            <div class="form-check d-inline-block">
                                                                <input class="form-check-input" type="checkbox"
                                                                    name="write_${perm.screenId}"
                                                                    id="write_${perm.screenId}"
                                                                    ${perm.writePermission==1 ? 'checked' : '' }>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="form-text mb-3">
                                        <i class="bi bi-info-circle me-1"></i>
                                        Read permission allows viewing the page. Write permission enables
                                        add/edit/delete actions.
                                    </div>

                                    <div class="d-flex gap-2">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-circle me-1"></i>Save Changes
                                        </button>
                                        <a href="${pageContext.request.contextPath}/roles"
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