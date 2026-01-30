<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Dashboard - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
                rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <%@ include file="/common/sidebar.jsp" %>

                <div class="main-content">
                    <jsp:include page="/common/header.jsp">
                        <jsp:param name="title" value="Dashboard" />
                    </jsp:include>

                    <div class="content-area">

                        <div class="row g-4 mb-4">
                            <div class="col-md-6 col-lg-4 col-xl-2">
                                <div class="stat-card">
                                    <div class="stat-card-icon purple">
                                        <i class="bi bi-people-fill"></i>
                                    </div>
                                    <div class="stat-card-value">${employeeCount}</div>
                                    <div class="stat-card-label">Employees</div>
                                </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-2">
                                <div class="stat-card">
                                    <div class="stat-card-icon blue">
                                        <i class="bi bi-person-badge-fill"></i>
                                    </div>
                                    <div class="stat-card-value">${roleCount}</div>
                                    <div class="stat-card-label">Roles</div>
                                </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-2">
                                <div class="stat-card">
                                    <div class="stat-card-icon teal">
                                        <i class="bi bi-mortarboard-fill"></i>
                                    </div>
                                    <div class="stat-card-value">${studentCount}</div>
                                    <div class="stat-card-label">Students</div>
                                </div>
                            </div>
                            <div class="col-md-6 col-lg-4 col-xl-2">
                                <div class="stat-card">
                                    <div class="stat-card-icon orange">
                                        <i class="bi bi-person-workspace"></i>
                                    </div>
                                    <div class="stat-card-value">${teacherCount}</div>
                                    <div class="stat-card-label">Teachers</div>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title">
                                    <i class="bi bi-info-circle me-2"></i>Current Session
                                </h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong><i class="bi bi-person me-2"></i>Logged in as:</strong>
                                            ${sessionScope.userName}</p>
                                        <p>
                                            <strong><i class="bi bi-shield-check me-2"></i>Role(s):</strong>
                                            <c:forEach var="role" items="${sessionScope.userRoles}" varStatus="status">
                                                <span class="badge badge-role">${role}</span>
                                            </c:forEach>
                                        </p>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <%@ include file="/common/footer.jsp" %>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>