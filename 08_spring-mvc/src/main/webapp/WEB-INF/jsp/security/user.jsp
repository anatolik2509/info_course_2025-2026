<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Страница пользователя</title>
</head>
<body>
<h1>Добро пожаловать, ${username}!</h1>

<c:if test="${isAdmin}">
    <p>
        <a href="${pageContext.request.contextPath}/admin">
            Перейти в панель администратора
        </a>
    </p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Выйти</button>
</form>
</body>
</html>
