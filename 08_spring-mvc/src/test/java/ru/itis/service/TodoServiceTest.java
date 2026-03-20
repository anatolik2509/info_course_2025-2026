package ru.itis.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itis.exception.TodoAlreadyExistsException;
import ru.itis.model.Todo;
import ru.itis.repository.TodoRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Captor
    private ArgumentCaptor<Todo> todoCaptor;

    @InjectMocks
    private TodoService todoService;

    private static int counter;

    @BeforeAll
    static void beforeAll() {
        counter = 0;
    }

    @BeforeEach
    void setUp() {
        System.out.println("Running test " + counter);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finished test " + counter);
        counter++;
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Total test count = " + counter);
    }

    @Test
    void getTodosTest() {
        // given
        List<Todo> returnedTodos = List.of(
                generateTodo(),
                generateTodo(),
                generateTodo()
        );
        when(todoRepository.findAll()).thenReturn(returnedTodos);

        // when
        List<Todo> result = todoService.getAllTodos();

        // then
        assertIterableEquals(returnedTodos, result);
    }

    @Test
    void createTodosTest() {
        // given
        String todoText = RandomStringUtils.randomAlphabetic(10);
        when(todoRepository.findByText(anyString())).thenReturn(Optional.empty());

        // when
        todoService.createTodo(todoText);

        // then
        verify(todoRepository).save(todoCaptor.capture());
        Todo capturedTodo = todoCaptor.getValue();
        assertNull(capturedTodo.getId());
        assertEquals(todoText, capturedTodo.getText());
        assertNull(capturedTodo.getCreatedAt());
    }

    @Test
    void createDuplicatedTodoTest() {
        // given
        String todoText = RandomStringUtils.randomAlphabetic(10);
        when(todoRepository.findByText(todoText)).thenReturn(Optional.of(generateTodo()));

        // when
        var ex = assertThrows(TodoAlreadyExistsException.class, () -> todoService.createTodo(todoText));

        // then
        assertEquals("Todo with text '%s' already exists".formatted(todoText), ex.getMessage());
    }

    private Todo generateTodo() {
        return new Todo(
                RandomUtils.nextLong(),
                RandomStringUtils.randomAlphabetic(10),
                LocalDateTime.now()
        );
    }
}
