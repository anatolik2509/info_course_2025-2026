<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<jsp:useBean id="user" scope="request" type="ru.itis.servlet.todo.models.User"/>
<t:layout title="Profile">
    <p>Hello! My email is ${user.email}</p>
</t:layout>
