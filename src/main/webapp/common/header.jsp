<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <header class="top-header">
            <h1 class="page-title">${param.title}</h1>
            <div class="user-menu">
                <div class="dropdown">
                    <div class="user-info dropdown-toggle" id="profileDropdown" data-bs-toggle="dropdown"
                        aria-expanded="false" style="cursor: pointer;">
                        <div class="user-avatar-circle">
                            ${sessionScope.userName.substring(0, 1).toUpperCase()}
                        </div>
                        <div class="user-details-text">
                            <div class="user-name-line">${sessionScope.userName}</div>
                            <div class="user-role-line">
                                <c:forEach var="role" items="${sessionScope.userRoles}" varStatus="status">
                                    ${role}${!status.last ? ', ' : ''}
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <ul class="dropdown-menu dropdown-menu-end shadow-sm" aria-labelledby="profileDropdown">
                        <li>
                            <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                <i class="bi bi-box-arrow-right me-2"></i>Logout
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </header>