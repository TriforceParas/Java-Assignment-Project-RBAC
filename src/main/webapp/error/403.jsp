<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Access Denied - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
                rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <div class="auth-container">
                <div class="auth-card text-center">
                    <div class="auth-logo" style="background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);">
                        <i class="bi bi-shield-x"></i>
                    </div>
                    <h1 class="auth-title">Access Denied</h1>
                    <p class="auth-subtitle">You don't have permission to access this resource.</p>

                    <div class="alert alert-danger mt-4">
                        <i class="bi bi-exclamation-triangle me-2"></i>
                        Error 403: Forbidden
                    </div>

                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary mt-3">
                        <i class="bi bi-arrow-left me-2"></i>Return to Dashboard
                    </a>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>