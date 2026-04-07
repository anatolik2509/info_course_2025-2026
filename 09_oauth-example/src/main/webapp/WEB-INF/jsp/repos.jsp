<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Репозитории — ${username}</title>
</head>
<body>
<h1>Репозитории пользователя <em>${username}</em></h1>
<ul>
    <c:forEach items="${repos}" var="repo">
        <li>
            <a href="${repo.htmlUrl}" target="_blank"><strong>${repo.name}</strong></a>
            <c:if test="${not empty repo.description}">
                — ${repo.description}
            </c:if>
            &nbsp;⭐ ${repo.stargazersCount}
            <c:if test="${not empty repo.language}">(${repo.language})</c:if>
        </li>
    </c:forEach>
</ul>
<a href="/logout">Выйти</a>
</body>
</html>