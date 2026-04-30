<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><#if noteDto.id??>Редактировать<#else>Создать</#if> заметку</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body class="notes-page">
    <div class="header">
        <div class="header-content" style="max-width: 800px;">
            <h1><#if noteDto.id??>Редактировать заметку<#else>Создать новую заметку</#if></h1>
        </div>
    </div>

    <div class="container" style="max-width: 800px;">
        <div class="form-card">
            <form action="/notes/save" method="post">
                <#if noteDto.id??>
                    <input type="hidden" name="id" value="${noteDto.id}"/>
                </#if>

                <@spring.bind "noteDto.title"/>
                <div class="form-group">
                    <label for="title">Заголовок:</label>
                    <input type="text" id="title" name="${spring.status.expression}"
                           value="${spring.status.value!''}"
                           class="${spring.status.errorMessages?has_content?then('error', '')}"
                           autofocus/>
                    <#list spring.status.errorMessages as msg>
                        <span class="field-error">${msg}</span>
                    </#list>
                </div>

                <@spring.bind "noteDto.content"/>
                <div class="form-group">
                    <label for="content">Содержание:</label>
                    <textarea id="content" name="${spring.status.expression}"
                             class="${spring.status.errorMessages?has_content?then('error', '')}">${spring.status.value!''}</textarea>
                    <#list spring.status.errorMessages as msg>
                        <span class="field-error">${msg}</span>
                    </#list>
                </div>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="form-actions">
                    <a href="/notes" class="btn btn-secondary">Отмена</a>
                    <button type="submit" class="btn btn-primary" style="width: auto;">Сохранить</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
