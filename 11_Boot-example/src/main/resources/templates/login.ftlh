<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход - Приложение Заметок</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-container">
        <h2>Вход в систему</h2>

        <#if error??>
            <div class="alert alert-error">${error}</div>
        </#if>

        <#if message??>
            <div class="alert alert-success">${message}</div>
        </#if>

        <form action="/login" method="post">
            <div class="form-group">
                <label for="username">Имя пользователя:</label>
                <input type="text" id="username" name="username" required autofocus>
            </div>

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn btn-primary">Войти</button>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <div class="auth-link">
            Нет аккаунта? <a href="/register">Зарегистрироваться</a>
        </div>
    </div>
</body>
</html>
