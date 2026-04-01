<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Панель администратора</title>
</head>
<body>
<h1>Здравствуйте, господин администратор ${username}!</h1>

<p><a href="${pageContext.request.contextPath}/user">Вернуться на страницу пользователя</a></p>

<form method="post" action="${pageContext.request.contextPath}/logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Выйти</button>
</form>
</body>
</html>
