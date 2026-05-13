document.addEventListener('DOMContentLoaded',() => {
            var csrfToken = document.querySelector('meta[name="_csrf"]').content;
            var csrfParam = document.querySelector('meta[name="_csrf_param"]').content;
            var grid = document.getElementById('notes-grid');
            var emptyState = document.getElementById('empty-state');
            var notesCount = document.getElementById('notes-count');
            var wsStatus = document.getElementById('ws-status');

            // --- Нативный WebSocket (без библиотек) ---

            var ws = null;
            var reconnectDelay = 2000;

            function connect() {
                // Определяем ws:// или wss:// в зависимости от протокола страницы
                var protocol = (location.protocol === 'https:') ? 'wss:' : 'ws:';
                ws = new WebSocket(protocol + '//' + location.host + '/ws/notes');

                ws.onopen = function () {
                    wsStatus.className = 'ws-indicator ws-connected';
                    wsStatus.title = 'Синхронизация активна';
                    console.log('WebSocket подключён');
                };

                ws.onmessage = function (event) {
                    var noteEvent = JSON.parse(event.data);
                    handleNoteEvent(noteEvent);
                };

                ws.onclose = function () {
                    wsStatus.className = 'ws-indicator ws-disconnected';
                    wsStatus.title = 'Переподключение...';
                    console.log('WebSocket отключён, переподключение...');
                    setTimeout(connect, reconnectDelay);
                };

                ws.onerror = function (err) {
                    console.error('WebSocket ошибка:', err);
                    ws.close();
                };
            }

            connect();

            // --- Обработка событий ---

            function handleNoteEvent(event) {
                switch (event.type) {
                    case 'CREATED':
                        addNoteCard(event);
                        showToast('Создана заметка: ' + escapeHtml(event.title));
                        break;
                    case 'UPDATED':
                        updateNoteCard(event);
                        showToast('Обновлена заметка: ' + escapeHtml(event.title));
                        break;
                    case 'DELETED':
                        removeNoteCard(event.id);
                        showToast('Удалена заметка: ' + escapeHtml(event.title));
                        break;
                }
                updateCount();
                toggleEmptyState();
            }

            function addNoteCard(event) {
                if (grid.querySelector('[data-note-id="' + event.id + '"]')) {
                    updateNoteCard(event);
                    return;
                }
                var card = createCardElement(event);
                grid.insertBefore(card, grid.firstChild);
                card.classList.add('note-card-appear');
            }

            function updateNoteCard(event) {
                var card = grid.querySelector('[data-note-id="' + event.id + '"]');
                if (!card) {
                    addNoteCard(event);
                    return;
                }
                card.querySelector('.note-title').textContent = event.title;
                card.querySelector('.note-content').textContent = event.content || '';
                card.querySelector('.note-meta').textContent = 'Обновлено: ' + event.updatedAt;
                card.classList.add('note-card-highlight');
                setTimeout(function () {
                    card.classList.remove('note-card-highlight');
                }, 1500);
            }

            function removeNoteCard(noteId) {
                var card = grid.querySelector('[data-note-id="' + noteId + '"]');
                if (card) {
                    card.classList.add('note-card-remove');
                    setTimeout(function () {
                        card.remove();
                        updateCount();
                        toggleEmptyState();
                    }, 300);
                }
            }

            function createCardElement(event) {
                var card = document.createElement('div');
                card.className = 'note-card';
                card.setAttribute('data-note-id', event.id);
                card.innerHTML =
                    '<div class="note-title">' + escapeHtml(event.title) + '</div>' +
                    '<div class="note-content">' + escapeHtml(event.content || '') + '</div>' +
                    '<div class="note-meta">Обновлено: ' + escapeHtml(event.updatedAt) + '</div>' +
                    '<div class="note-actions">' +
                        '<a href="/notes/edit/' + event.id + '" class="btn btn-small btn-edit">Редактировать</a>' +
                        '<form action="/notes/delete/' + event.id + '" method="post" style="display: inline;">' +
                            '<button type="submit" class="btn btn-small btn-delete" ' +
                                'onclick="return confirm(\'Вы уверены, что хотите удалить эту заметку?\')">Удалить</button>' +
                            '<input type="hidden" name="' + csrfParam + '" value="' + csrfToken + '"/>' +
                        '</form>' +
                    '</div>';
                return card;
            }

            // --- Утилиты ---

            function updateCount() {
                notesCount.textContent = grid.querySelectorAll('.note-card').length;
            }

            function toggleEmptyState() {
                var hasNotes = grid.querySelectorAll('.note-card').length > 0;
                emptyState.style.display = hasNotes ? 'none' : 'block';
            }

            function escapeHtml(text) {
                var div = document.createElement('div');
                div.textContent = text;
                return div.innerHTML;
            }

            function showToast(message) {
                var container = document.getElementById('toast-container');
                var toast = document.createElement('div');
                toast.className = 'toast';
                toast.textContent = message;
                container.appendChild(toast);
                setTimeout(function () {
                    toast.classList.add('toast-hide');
                    setTimeout(function () { toast.remove(); }, 300);
                }, 3000);
            }
});