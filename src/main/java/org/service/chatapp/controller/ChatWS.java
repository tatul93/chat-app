package org.service.chatapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.service.chatapp.exception.ResourceNotFoundException;
import org.service.chatapp.model.domain.ChatRoom;
import org.service.chatapp.model.domain.Message;
import org.service.chatapp.model.request.ChatWSRequest;
import org.service.chatapp.service.ChatRoomService;
import org.service.chatapp.service.MessageService;
import org.service.chatapp.util.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWS extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChatWS.class);

    private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Map<Long, String> userChatRooms = new ConcurrentHashMap<>();

    private final MessageService messageService;
    private final ChatRoomService chatRoomService;
    private final SecurityHelper securityHelper;

    /**
     * Constructor for ChatWS, initializes messageService, chatRoomService and securityHelper.
     *
     * @param messageService  Service for managing messages.
     * @param chatRoomService Service for managing chat rooms.
     * @param securityHelper  Helper for security-related operations.
     */
    @Autowired
    public ChatWS(MessageService messageService, ChatRoomService chatRoomService,
                  SecurityHelper securityHelper) {
        this.messageService = messageService;
        this.chatRoomService = chatRoomService;
        this.securityHelper = securityHelper;
    }

    /**
     * Handles new WebSocket connections.
     * It adds the user session and chat room to the respective maps.
     *
     * @param session The WebSocket session.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (session.getPrincipal() == null) {
            throw new ResourceNotFoundException("Principal not found");

        }

        // get username from session
        Long userId = securityHelper.getUserIdFromPrincipal(session.getPrincipal());

        // get chat room by user id
        Optional<ChatRoom> chatRoom = chatRoomService.findChatRoomByMembersContaining(userId);
        if (chatRoom.isEmpty()) {
            throw new ResourceNotFoundException("ChatRoom not found");
        }

        // add user session and chat room to maps
        userSessions.put(userId, session);
        userChatRooms.put(userId, chatRoom.get().getId());
        logger.info("Established connection with session id {}", session.getId());

    }

    /**
     * Handles closed WebSocket connections.
     * It removes the user session and chat room from the respective maps.
     *
     * @param session The WebSocket session.
     * @param status  The close status.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = securityHelper.getUserIdFromPrincipal(session.getPrincipal());
        userSessions.remove(userId);
        userChatRooms.remove(userId);
    }

    /**
     * Handles incoming WebSocket messages.
     * It parses the message, saves it and broadcasts it to the chat room.
     *
     * @param session The WebSocket session.
     * @param message The incoming message.
     * @throws Exception If an exception occurred.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (session.getPrincipal() == null) {
            throw new ResourceNotFoundException("Principal not found");
        }
        final Long senderId = securityHelper.getUserIdFromPrincipal(session.getPrincipal());
        ChatWSRequest request;
        ObjectMapper mapper = new ObjectMapper();
        try {
            request = mapper.readValue(message.getPayload(), ChatWSRequest.class);
            // TODO validate fields
        } catch (Exception e) {
            logger.error("Error while parsing message: {}", e.getMessage());
            return;
        }

        String roomId = userChatRooms.get(senderId); // Currently are implementation is that each user has a one chat room

        messageService.saveMessage(new Message(senderId, request.message(), roomId, LocalDateTime.now()));

        broadcastMessageToRoom(roomId, request.message(), senderId);
    }

    /**
     * Broadcasts a message to a chat room.
     * It sends the message to all users in the chat room.
     *
     * @param roomId         The ID of the chat room.
     * @param messageContent The content of the message.
     * @param senderId       The ID of the sender.
     */
    private void broadcastMessageToRoom(String roomId, String messageContent, Long senderId) {

        userChatRooms.forEach((userId, userRoomId) -> {
            if (roomId.equals(userRoomId)) {
                WebSocketSession userSession = userSessions.get(userId);
                if (userSession != null && userSession.isOpen()) {
                    try {
                        String responseMessage = String.format("%s: %s", senderId,
                                messageContent);
                        userSession.sendMessage(new TextMessage(responseMessage));
                    } catch (IOException e) {
                        logger.error("Error while sending message to user: {}", e.getMessage());
                    }
                }
            }
        });
    }


    /**
     * Handles WebSocket transport errors.
     *
     * @param session   The WebSocket session.
     * @param exception The exception.
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Server transport error: {}", exception.getMessage());
    }

}
