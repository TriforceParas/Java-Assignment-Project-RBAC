<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Students - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap"
                rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Student Management" />
                    </jsp:include>

                    <div class="content-area">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger"><i class="bi bi-exclamation-circle me-2"></i>${error}</div>
                        </c:if>

                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0"><i class="bi bi-mortarboard-fill me-2"></i>All Students</h5>
                                <c:if test="${canWrite}">
                                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                                        <i class="bi bi-plus-circle me-2"></i>Add Student
                                    </button>
                                </c:if>
                            </div>
                            <div class="card-body p-0">
                                <table class="table table-hover mb-0">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Grade</th>
                                            <c:if test="${canWrite}">
                                                <th>Actions</th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s" items="${students}">
                                            <tr>
                                                <td>${s.id}</td>
                                                <td>${s.name}</td>
                                                <td>${s.email}</td>
                                                <td><span class="badge bg-secondary">${s.grade}</span></td>
                                                <c:if test="${canWrite}">
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/students?action=edit&id=${s.id}"
                                                            class="btn btn-sm btn-warning btn-icon" title="Edit">
                                                            <i class="bi bi-pencil"></i>
                                                        </a>
                                                        <form action="${pageContext.request.contextPath}/students"
                                                            method="post" class="d-inline"
                                                            onsubmit="return confirm('Delete?')">
                                                            <input type="hidden" name="action" value="delete">
                                                            <input type="hidden" name="id" value="${s.id}">
                                                            <button type="submit"
                                                                class="btn btn-sm btn-danger btn-icon">
                                                                <i class="bi bi-trash"></i>
                                                            </button>
                                                        </form>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty students}">
                                            <tr>
                                                <td colspan="5" class="text-center py-4">
                                                    <i class="bi bi-mortarboard" style="font-size: 2rem;"></i>
                                                    <p class="mb-0 mt-2">No students found</p>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <%@ include file="/common/footer.jsp" %>
                    </div>
                </div>

                <div class="modal fade" id="addModal" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title"><i class="bi bi-person-plus me-2"></i>Add Student</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <form action="${pageContext.request.contextPath}/students" method="post">
                                <input type="hidden" name="action" value="create">
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label class="form-label">Name</label>
                                        <input type="text" class="form-control" name="name" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control" name="email" required>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Grade</label>
                                        <input type="text" class="form-control" name="grade" required>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">Cancel</button>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-check-circle me-1"></i>Create
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>