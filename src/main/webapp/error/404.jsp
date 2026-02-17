<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Page Not Found - RBAC Demo</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
                rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <div class="auth-container">
                <div class="auth-card text-center">
                    <div class="auth-logo" style="background: linear-gradient(135deg, #f7971e 0%, #ffd200 100%);">
                        <i class="bi bi-question-circle"></i>
                    </div>
                    <h1 class="auth-title">Page Not Found</h1>
                    <p class="auth-subtitle">The page you're looking for doesn't exist.</p>

                    <div class="alert alert-warning mt-4">
                        <i class="bi bi-info-circle me-2"></i>
                        Error 404: Not Found
                    </div>

                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary mt-3">
                        <i class="bi bi-arrow-left me-2"></i>Return to Dashboard
                    </a>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>