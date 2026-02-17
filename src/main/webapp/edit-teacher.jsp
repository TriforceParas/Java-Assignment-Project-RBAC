<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Edit Teacher - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Edit Teacher" />
                    </jsp:include>

                    <div class="content-area">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">
                                    <i class="bi bi-pencil me-2"></i>Edit Teacher: ${teacher.name}
                                </h5>
                                <a href="${pageContext.request.contextPath}/teachers" class="btn btn-secondary btn-sm">
                                    <i class="bi bi-arrow-left me-1"></i>Back to List
                                </a>
                            </div>
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/teachers" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${teacher.id}">

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="name" class="form-label">Name</label>
                                            <input type="text" class="form-control" id="name" name="name"
                                                value="${teacher.name}" required>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="email" class="form-label">Email</label>
                                            <input type="email" class="form-control" id="email" name="email"
                                                value="${teacher.email}" required>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="department" class="form-label">Department</label>
                                            <input type="text" class="form-control" id="department" name="department"
                                                value="${teacher.department}" required>
                                        </div>
                                    </div>

                                    <div class="d-flex gap-2">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-circle me-1"></i>Update Teacher
                                        </button>
                                        <a href="${pageContext.request.contextPath}/teachers"
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