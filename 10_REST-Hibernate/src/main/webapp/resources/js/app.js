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
document.addEventListener('DOMContentLoaded', function() {
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
});

// Show message
function showMessage(elementId, message, type) {
    const element = document.getElementById(elementId);
    element.innerHTML = '<div class="message ' + type + '">' + message + '</div>';
    setTimeout(() => {
        element.innerHTML = '';
    }, 5000);
}
