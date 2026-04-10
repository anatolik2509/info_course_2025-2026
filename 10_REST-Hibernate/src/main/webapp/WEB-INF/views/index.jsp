<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE l>
<headhtml>
<htm>
    <title>Library Catalog</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        h1, h2 {
            color: #333;
        }
        .search-section, .add-section {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f5f5f5;
            border-radius: 5px;
        }
        input[type="text"], input[type="number"] {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
        button {
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .form-group {
            margin-bottom: 10px;
        }
        .form-group label {
            display: inline-block;
            width: 120px;
        }
        select {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 3px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
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

    <script>
        // Load all books on page load
        window.onload = function() {
            loadAllBooks();
            loadCabinets();
        };

        // Search books
        function searchBooks() {
            const query = document.getElementById('searchQuery').value;
            if (!query) {
                showMessage('searchMessage', 'Введите запрос для поиска', 'error');
                return;
            }

            fetch('/api/books/search?query=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(data => {
                    displayBooks(data);
                    showMessage('searchMessage', 'Найдено книг: ' + data.length, 'success');
                })
                .catch(error => {
                    console.error('Error:', error);
                    showMessage('searchMessage', 'Ошибка при поиске', 'error');
                });
        }

        // Load all books
        function loadAllBooks() {
            fetch('/api/books')
                .then(response => response.json())
                .then(data => {
                    displayBooks(data);
                    document.getElementById('searchMessage').innerHTML = '';
                })
                .catch(error => {
                    console.error('Error:', error);
                    showMessage('searchMessage', 'Ошибка при загрузке книг', 'error');
                });
        }

        // Display books in table
        function displayBooks(books) {
            const tbody = document.getElementById('booksTableBody');
            tbody.innerHTML = '';

            books.forEach(book => {
                const row = tbody.insertRow();
                row.insertCell(0).textContent = book.id;
                row.insertCell(1).textContent = book.title;
                row.insertCell(2).textContent = book.author || '-';
                row.insertCell(3).textContent = book.isbn || '-';
                row.insertCell(4).textContent = book.cabinetName || '-';
                row.insertCell(5).textContent = book.cabinetId || '-';
            });
        }

        // Load cabinets for dropdown
        function loadCabinets() {
            fetch('/api/cabinets')
                .then(response => response.json())
                .then(data => {
                    const select = document.getElementById('bookCabinet');
                    select.innerHTML = '<option value="">Выберите шкаф</option>';
                    data.forEach(cabinet => {
                        const option = document.createElement('option');
                        option.value = cabinet.id;
                        option.textContent = cabinet.name + ' (' + cabinet.location + ')';
                        select.appendChild(option);
                    });
                })
                .catch(error => console.error('Error:', error));
        }

        // Add cabinet
        document.getElementById('cabinetForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const data = {
                name: document.getElementById('cabinetName').value,
                location: document.getElementById('cabinetLocation').value,
                capacity: parseInt(document.getElementById('cabinetCapacity').value) || null
            };

            fetch('/api/cabinets', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                showMessage('cabinetMessage', 'Шкаф успешно добавлен!', 'success');
                document.getElementById('cabinetForm').reset();
                loadCabinets();
            })
            .catch(error => {
                console.error('Error:', error);
                showMessage('cabinetMessage', 'Ошибка при добавлении шкафа', 'error');
            });
        });

        // Add book
        document.getElementById('bookForm').addEventListener('submit', function(e) {
            e.preventDefault();

            const data = {
                title: document.getElementById('bookTitle').value,
                author: document.getElementById('bookAuthor').value,
                isbn: document.getElementById('bookIsbn').value,
                cabinetId: parseInt(document.getElementById('bookCabinet').value)
            };

            fetch('/api/books', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                showMessage('bookMessage', 'Книга успешно добавлена!', 'success');
                document.getElementById('bookForm').reset();
                loadAllBooks();
            })
            .catch(error => {
                console.error('Error:', error);
                showMessage('bookMessage', 'Ошибка при добавлении книги', 'error');
            });
        });

        // Show message
        function showMessage(elementId, message, type) {
            const element = document.getElementById(elementId);
            element.innerHTML = '<div class="message ' + type + '">' + message + '</div>';
            setTimeout(() => {
                element.innerHTML = '';
            }, 5000);
        }
    </script>
</body>
</html>
