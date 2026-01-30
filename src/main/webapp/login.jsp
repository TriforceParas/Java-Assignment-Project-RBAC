<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Login - RBAC Project</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
            <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
        </head>

        <body>
            <div class="auth-container">

                <div class="auth-left d-none d-lg-flex">
                    <div class="auth-left-content">
                        <div class="auth-credit-surtitle">JAVA FOR ENTERPRISE TECHNOLOGY</div>
                        <div class="auth-credit-title">RBAC System</div>
                        <div class="auth-credit-subtitle">Java JSP & Servlet Project</div>

                        <div class="auth-credit-box">
                            <div class="auth-credit-label">Developed By</div>
                            <div class="auth-credit-id">23030124225</div>
                            <div class="auth-credit-name">PARAS KUMAR SHARMA</div>
                        </div>
                    </div>
                </div>

                <div class="auth-right">
                    <div class="auth-card">
                        <div class="d-flex justify-content-center">
                            <div class="auth-logo">
                                <i class="bi bi-shield-lock-fill"></i>
                            </div>
                        </div>
                        <h3 class="auth-title">Welcome</h3>
                        <p class="auth-subtitle">Sign in to access your account</p>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="login" method="post">
                            <div class="mb-3">
                                <label class="form-label fw-bold">Username</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white border-end-0"><i
                                            class="bi bi-person text-muted"></i></span>
                                    <input type="text" class="form-control border-start-0 ps-0" name="username"
                                        placeholder="e.g. admin" required>
                                </div>
                            </div>
                            <div class="mb-4">
                                <label class="form-label fw-bold">Password</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white border-end-0"><i
                                            class="bi bi-lock text-muted"></i></span>
                                    <input type="password" class="form-control border-start-0 ps-0" name="password"
                                        placeholder="••••••••" required>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary w-100 py-2 mb-3 fw-bold">
                                Login
                            </button>
                        </form>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>