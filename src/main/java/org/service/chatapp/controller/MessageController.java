package org.service.chatapp.controller;

import org.service.chatapp.model.response.MessagePageResponse;
import org.service.chatapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing messages.
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    /**
     * Service for managing messages.
     */
    private final MessageService messageService;

    /**
     * Constructor for MessageController, initializes messageService.
     *
     * @param messageService Service for managing messages.
     */
    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Endpoint for retrieving messages in a chat room.
     * It returns a page of messages in the specified chat room.
     *
     * @param chatRoomId The ID of the chat room.
     * @param page       The page number.
     * @param size       The page size.
     * @return The page of messages.
     */
    @GetMapping("/chatroom/{chatRoomId}")
    public MessagePageResponse getMessage(@PathVariable("chatRoomId") String chatRoomId, @RequestParam int page, @RequestParam int size) {
        return messageService.getMessages(chatRoomId, page, size);
    }
}
