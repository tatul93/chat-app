package org.service.chatapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.service.chatapp.model.domain.ChatRoom;
import org.service.chatapp.repository.ChatRoomRepository;
import org.service.chatapp.service.impl.ChatRoomServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTests {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @InjectMocks
    private ChatRoomServiceImpl chatRoomService;


    @Test
    public void testFindChatRoomByMembersContaining() {
        ChatRoom chatRoom = new ChatRoom();

        chatRoom.setId("1");
        chatRoom.setName("ChatName");

        when(chatRoomRepository.findChatRoomByMembersContaining(1L)).thenReturn(Optional.of(chatRoom));

        Optional<ChatRoom> chatRoomByMembersContaining = chatRoomService.findChatRoomByMembersContaining(1L);

        assertTrue(chatRoomByMembersContaining.isPresent());
        assertEquals("1", chatRoomByMembersContaining.get().getId());
        assertEquals("ChatName", chatRoomByMembersContaining.get().getName());
    }


    @Test
    public void testDeleteAll() {
        chatRoomService.deleteAll();

        verify(chatRoomRepository).deleteAll();
    }


    @Test
    public void testSaveMessage() {
        ChatRoom chatRoom = new ChatRoom();

        chatRoomService.saveMessage(chatRoom);

        verify(chatRoomRepository).save(chatRoom);
    }

    @Test
    public void testUpdate() {
        ChatRoom chatRoom = new ChatRoom();

        when(chatRoomRepository.findById("1")).thenReturn(Optional.of(chatRoom));

        chatRoomService.update("1", 1L);

        verify(chatRoomRepository).findById("1");
        verify(chatRoomRepository).save(chatRoom);
    }
}
