<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация - Приложение Заметок</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body class="auth-page">
    <div class="auth-container">
        <h2>Регистрация</h2>

        <#if error??>
            <div class="alert alert-error">${error}</div>
        </#if>

        <form action="/register" method="post">
            <@spring.bind "registerDto.username"/>
            <div class="form-group">
                <label for="username">Имя пользователя:</label>
                <input type="text" id="username" name="${spring.status.expression}"
                       value="${spring.status.value!''}"
                       class="${spring.status.errorMessages?has_content?then('error', '')}"
                       autofocus/>
                <#list spring.status.errorMessages as msg>
                    <span class="field-error">${msg}</span>
                </#list>
            </div>

            <@spring.bind "registerDto.password"/>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="${spring.status.expression}"
                       class="${spring.status.errorMessages?has_content?then('error', '')}"/>
                <#list spring.status.errorMessages as msg>
                    <span class="field-error">${msg}</span>
                </#list>
            </div>

            <@spring.bind "registerDto.confirmPassword"/>
            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль:</label>
                <input type="password" id="confirmPassword" name="${spring.status.expression}"
                       class="${spring.status.errorMessages?has_content?then('error', '')}"/>
                <#list spring.status.errorMessages as msg>
                    <span class="field-error">${msg}</span>
                </#list>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
        </form>

        <div class="auth-link">
            Уже есть аккаунт? <a href="/login">Войти</a>
        </div>
    </div>
</body>
</html>
