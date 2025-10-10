<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:useBean id="tasks" scope="request" type="java.util.List"/>
<t:layout title="Todo lists">
    <form method="post" id="task-input">
        <label>
            <input type="text" name="taskText">
        </label>
        <label>
            <input type="submit" value="Create">
        </label>
    </form>
    <c:if test="${empty tasks}">
        <h1>Нет задач!</h1>
    </c:if>
    <c:if test="${not empty tasks}">
        <c:forEach var="task" items="${tasks}">
            <t:task task="${task}"/>
        </c:forEach>
    </c:if>
    <script src="${pageContext.servletContext.contextPath}/js/script.js"></script>
</t:layout>
