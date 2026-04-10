<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE <html>
html>
<head>
    <meta charset="UTF-8">
    <title>Library Catalog</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <h1>Каталог библиотеки</h1>

    <!-- Search Section -->
    <div class="search-section">
        <h2>Поиск книг</h2>
        <input type="text" id="searchQuery" placeholder="Введите название книги...">
        <button onclick="searchBooks()">Найти</button>
        <button onclick="loadAllBooks()">Показать все книги</button>
        <div id="searchMessage"></div>
        <table id="booksTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Автор</th>
                    <th>ISBN</th>
                    <th>Шкаф</th>
                    <th>Номер шкафа</th>
                </tr>
            </thead>
            <tbody id="booksTableBody">
            </tbody>
        </table>
    </div>

    <!-- Add Cabinet Section -->
    <div class="add-section">
        <h2>Добавить шкаф</h2>
        <div id="cabinetMessage"></div>
        <form id="cabinetForm">
            <div class="form-group">
                <label>Название:</label>
                <input type="text" id="cabinetName" required>
            </div>
            <div class="form-group">
                <label>Расположение:</label>
                <input type="text" id="cabinetLocation">
            </div>
            <div class="form-group">
                <label>Вместимость:</label>
                <input type="number" id="cabinetCapacity" min="1">
            </div>
            <button type="submit">Добавить шкаф</button>
        </form>
    </div>

    <!-- Add Book Section -->
    <div class="add-section">
        <h2>Добавить книгу</h2>
        <div id="bookMessage"></div>
        <form id="bookForm">
            <div class="form-group">
                <label>Название:</label>
                <input type="text" id="bookTitle" required>
            </div>
            <div class="form-group">
                <label>Автор:</label>
                <input type="text" id="bookAuthor">
            </div>
            <div class="form-group">
                <label>ISBN:</label>
                <input type="text" id="bookIsbn">
            </div>
            <div class="form-group">
                <label>Шкаф:</label>
                <select id="bookCabinet" required>
                    <option value="">Выберите шкаф</option>
                </select>
            </div>
            <button type="submit">Добавить книгу</button>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</body>
</html>
