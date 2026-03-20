package ru.itis.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;
import ru.itis.service.MessageService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MyControllerTest {
    @Spy
    private MessageService messageService;

    @Mock
    private ModelMap map;

    @InjectMocks
    private MyController myController;

    @Test
    void messageServiceTest() {
        myController.simple(map);
        verify(messageService).getMessage();
        verify(map).put(eq("viewVariable"), any());
    }
}
