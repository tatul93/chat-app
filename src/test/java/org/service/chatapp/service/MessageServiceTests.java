package org.service.chatapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.service.chatapp.model.domain.Message;
import org.service.chatapp.model.response.MessagePageResponse;
import org.service.chatapp.repository.MessageRepository;
import org.service.chatapp.service.impl.MessageServiceImpl;
import org.service.chatapp.util.MessageToMessageDtoConverter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {

    @Mock
    private MessageRepository messageRepository;

    @Spy
    private MessageToMessageDtoConverter messageToMessageDtoConverter;

    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    public void testLoadUserByUsername() {
        when(messageRepository.findAllByRoomIdOrderBySentTimeDesc("1", PageRequest.of(0, 10))).thenReturn(new PageImpl<Message>(Arrays.asList(new Message(1L, "messageText", "roomId", LocalDateTime.now()))));

        MessagePageResponse messages = messageService.getMessages("1", 0, 10);

        assertNotNull(messages);
        assertEquals(1, messages.messages().size());
        assertEquals(1, messages.totalPages());
    }
}
