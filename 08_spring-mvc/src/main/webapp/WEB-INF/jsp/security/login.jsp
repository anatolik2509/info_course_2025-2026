<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
</head>
<body>
<h1>Вход в систему</h1>

<c:if test="${param.error != null}">
    <p style="color: red;">Неверный логин или пароль</p>
</c:if>
<c:if test="${param.logout != null}">
    <p style="color: green;">Вы вышли из системы</p>
</c:if>
<c:if test="${param.registered != null}">
    <p style="color: green;">Регистрация прошла успешно — войдите в систему</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div>
        <label for="username">Логин:</label><br/>
        <input type="text" id="username" name="username" required autofocus/>
    </div>
    <br/>
    <div>
        <label for="password">Пароль:</label><br/>
        <input type="password" id="password" name="password" required/>
    </div>
    <br/>
    <button type="submit">Войти</button>
</form>

<p><a href="${pageContext.request.contextPath}/register">Нет аккаунта? Зарегистрироваться</a></p>
</body>
</html>
