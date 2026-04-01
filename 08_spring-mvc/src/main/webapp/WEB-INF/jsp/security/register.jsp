<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация</h1>

<c:if test="${usernameError != null}">
    <p style="color: red;">${usernameError}</p>
</c:if>

<form:form method="post" action="${pageContext.request.contextPath}/register" modelAttribute="form">
    <div>
        <label for="username">Логин:</label><br/>
        <form:input path="username" id="username" required="true" autofocus="true"/>
        <form:errors path="username" style="color: red;"/>
    </div>
    <br/>
    <div>
        <label for="password">Пароль:</label><br/>
        <form:password path="password" id="password" required="true"/>
        <form:errors path="password" style="color: red;"/>
    </div>
    <br/>
    <button type="submit">Зарегистрироваться</button>
</form:form>

<p><a href="${pageContext.request.contextPath}/login">Уже есть аккаунт? Войти</a></p>
</body>
</html>
