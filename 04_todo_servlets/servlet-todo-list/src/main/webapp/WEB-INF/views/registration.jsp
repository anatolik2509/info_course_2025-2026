<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<t:layout title="Registration">
    <form method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email">

        <label for="password">Password:</label>
        <input type="password" id="password" name="password">

        <label for="passwordRepeat">Password repeat:</label>
        <input type="password" id="passwordRepeat" name="passwordRepeat">

        <label>
            <input type="submit" value="Registration">
        </label>
    </form>
    <a href="${pageContext.servletContext.contextPath}/login">Already have account</a>
    <c:if test="${not empty error}">
        <p>Ошибка: ${error}</p>
    </c:if>
</t:layout>
