<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        .todo-form {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f5f5f5;
            border-radius: 5px;
        }
        .todo-form input[type="text"] {
            width: 70%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
        .todo-form button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .todo-form button:hover {
            background-color: #45a049;
        }
        .todo-list {
            list-style-type: none;
            padding: 0;
        }
        .todo-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            margin-bottom: 10px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .todo-text {
            flex-grow: 1;
            font-size: 16px;
        }
        .todo-date {
            color: #888;
            font-size: 14px;
            margin-right: 15px;
        }
        .delete-btn {
            padding: 8px 15px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        .delete-btn:hover {
            background-color: #da190b;
        }
        .error-message {
            color: #f44336;
            padding: 10px;
            margin-bottom: 20px;
            background-color: #ffebee;
            border: 1px solid #f44336;
            border-radius: 5px;
        }
        .empty-message {
            text-align: center;
            color: #888;
            padding: 20px;
            font-style: italic;
        }
    <body>
</style>
</head>
    <h1>Todo List</h1>

    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>

    <div class="todo-form">
        <form action="${pageContext.servletContext.contextPath}/todos" method="post">
            <input type="text" name="text" placeholder="Enter new todo..." required />
            <button type="submit">Add Todo</button>
        </form>
    </div>

    <c:choose>
        <c:when test="${empty todos}">
            <div class="empty-message">
                No todos yet. Add your first todo above!
            </div>
        </c:when>
        <c:otherwise>
            <ul class="todo-list">
                <c:forEach var="todo" items="${todos}">
                    <li class="todo-item">
                        <span class="todo-text">${todo.text}</span>
                        <span class="todo-date">${todo.createdAt}</span>
                        <form action="${pageContext.servletContext.contextPath}/todos/${todo.id}/delete" method="post" style="display: inline;">
                            <button type="submit" class="delete-btn">Delete</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</body>
</html>
