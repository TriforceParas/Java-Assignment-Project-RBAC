<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <nav class="sidebar">
            <div class="sidebar-brand">
                <div class="sidebar-brand-icon">
                    <i class="bi bi-shield-lock"></i>
                </div>
                <span class="sidebar-brand-text">RBAC Demo</span>
            </div>

            <c:set var="allowed" value="${sessionScope.allowedScreens}" />

            <div class="sidebar-nav">
                <div class="nav-section-title">Main</div>
                <div class="nav-item">
                    <a href="${pageContext.request.contextPath}/dashboard"
                        class="nav-link ${pageContext.request.servletPath == '/dashboard.jsp' ? 'active' : ''}">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                </div>

                <c:set var="showEmployees"
                    value="${allowed.contains('/employees.jsp') or allowed.contains('/employees')}" />
                <c:set var="showRoles" value="${allowed.contains('/roles.jsp') or allowed.contains('/roles')}" />
                <c:if test="${empty allowed}">

                </c:if>

                <c:if test="${showEmployees or showRoles}">
                    <div class="nav-section-title">Management</div>

                    <c:if test="${showEmployees}">
                        <div class="nav-item">
                            <a href="${pageContext.request.contextPath}/employees"
                                class="nav-link ${pageContext.request.servletPath == '/employees.jsp' ? 'active' : ''}">
                                <i class="bi bi-people-fill"></i> Employees
                            </a>
                        </div>
                    </c:if>

                    <c:if test="${showRoles}">
                        <div class="nav-item">
                            <a href="${pageContext.request.contextPath}/roles"
                                class="nav-link ${pageContext.request.servletPath == '/roles.jsp' ? 'active' : ''}">
                                <i class="bi bi-person-badge-fill"></i> Roles & Permissions
                            </a>
                        </div>
                    </c:if>
                </c:if>

                <c:set var="showStudents"
                    value="${allowed.contains('/students.jsp') or allowed.contains('/students')}" />
                <c:set var="showTeachers"
                    value="${allowed.contains('/teachers.jsp') or allowed.contains('/teachers')}" />

                <c:if test="${showStudents or showTeachers}">
                    <div class="nav-section-title">Sample Tables</div>

                    <c:if test="${showStudents}">
                        <div class="nav-item">
                            <a href="${pageContext.request.contextPath}/students"
                                class="nav-link ${pageContext.request.servletPath == '/students.jsp' ? 'active' : ''}">
                                <i class="bi bi-mortarboard-fill"></i> Students
                            </a>
                        </div>
                    </c:if>

                    <c:if test="${showTeachers}">
                        <div class="nav-item">
                            <a href="${pageContext.request.contextPath}/teachers"
                                class="nav-link ${pageContext.request.servletPath == '/teachers.jsp' ? 'active' : ''}">
                                <i class="bi bi-person-workspace"></i> Teachers
                            </a>
                        </div>
                    </c:if>
                </c:if>
            </div>
        </nav>