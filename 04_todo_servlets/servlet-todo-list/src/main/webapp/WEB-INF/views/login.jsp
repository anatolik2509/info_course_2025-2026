<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<t:layout title="Login">
    <form method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email">

        <label for="password">Password:</label>
        <input type="password" id="password" name="password">

        <label>
            <input type="submit" value="Registration">
        </label>
    </form>
    <a href="${pageContext.servletContext.contextPath}/registration">I dont have account</a>
    <c:if test="${not empty error}">
        <p>Ошибка: ${error}</p>
    </c:if>
</t:layout>
