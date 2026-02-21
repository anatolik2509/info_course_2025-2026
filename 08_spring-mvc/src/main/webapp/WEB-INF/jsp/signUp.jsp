<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
        }

        .form-container {
            background: #f5f5f5;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background: #0056b3;
        }

        .error-block {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
            padding: 10px;
            margin-bottom: 20px;
        }

        .success-block {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            border-radius: 4px;
            padding: 10px;
            margin-bottom: 20px;
        }

        .field-error {
            color: #dc3545;
            font-size: 14px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Регистрация</h2>

        <c:if test="${not empty errors}">
            <div class="error-block">
                <strong>Ошибки валидации:</strong>
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <c:if test="${success}">
            <div class="success-block">
                <strong>Успешно!</strong> Регистрация прошла успешно.
            </div>
        </c:if>

        <form:form modelAttribute="user" method="post" action="/validation/register">
            <div class="form-group">
                <label for="login">Логин:</label>
                <form:input path="login" id="login" type="text" />
                <form:errors path="login" cssClass="field-error" />
            </div>

            <div class="form-group">
                <label for="email">Логин:</label>
                <form:input path="email" id="email" type="text" />
                <form:errors path="email" cssClass="field-error" />
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <form:input path="password" id="password" type="password" />
                <form:errors path="password" cssClass="field-error" />
            </div>

            <div class="form-group">
                <label for="passwordRepeat">Подтверждение пароля:</label>
                <form:input path="passwordRepeat" id="passwordRepeat" type="password" />
                <form:errors path="passwordRepeat" cssClass="field-error" />
            </div>

            <div class="form-group">
                <input type="submit" value="Зарегистрироваться" />
            </div>
        </form:form>
    </div>
</body>
</html>