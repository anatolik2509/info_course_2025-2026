<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Мои заметки</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_param" content="${_csrf.parameterName}"/>
    <link rel="stylesheet" href="/css/style.css">
    <script src="/js/websocket.js"></script>
</head>
<body class="notes-page">
    <div class="header">
        <div class="header-content">
            <h1>Мои заметки</h1>
            <div class="header-right">
                <span id="ws-status" class="ws-indicator ws-disconnected" title="WebSocket отключён"></span>
                <span class="user-info">Пользователь: ${username}</span>
                <form action="/logout" method="post" class="logout-form">
                    <button type="submit" class="btn btn-danger">Выйти</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="top-actions">
            <h2>Все заметки (<span id="notes-count">${notes?size}</span>)</h2>
            <a href="/notes/new" class="btn btn-primary" style="width: auto;">+ Создать заметку</a>
        </div>

        <div id="toast-container"></div>

        <div id="empty-state" class="empty-state" style="display: <#if !notes?has_content>block<#else>none</#if>;">
            <h2>У вас пока нет заметок</h2>
            <p>Создайте свою первую заметку, нажав кнопку "Создать заметку"</p>
        </div>

        <div id="notes-grid" class="notes-grid">
            <#list notes as note>
                <div class="note-card" data-note-id="${note.id}">
                    <div class="note-title">${note.title}</div>
                    <div class="note-content">${note.content!''}</div>
                    <div class="note-meta">
                        Обновлено: ${note.updatedAt}
                    </div>
                    <div class="note-actions">
                        <a href="/notes/edit/${note.id}" class="btn btn-small btn-edit">Редактировать</a>
                        <form action="/notes/delete/${note.id}" method="post" style="display: inline;">
                            <button type="submit" class="btn btn-small btn-delete"
                                    onclick="return confirm('Вы уверены, что хотите удалить эту заметку?')">
                                Удалить
                            </button>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</body>
</html>