package org.service.chatapp.controller;

import org.service.chatapp.service.ChatRoomService;
import org.service.chatapp.util.SecurityHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller for managing chat rooms.
 * It provides endpoints for joining a chat room.
 */
@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {

	/**
	 * Service for managing chat rooms.
	 */
	private final ChatRoomService chatRoomService;

	/**
	 * Helper for security-related operations.
	 */
	private final SecurityHelper securityHelper;

	/**
	 * Constructor for ChatRoomController, initializes chatRoomService and securityHelper.
	 *
	 * @param chatRoomService Service for managing chat rooms.
	 * @param securityHelper Helper for security-related operations.
	 */
	public ChatRoomController(ChatRoomService chatRoomService, SecurityHelper securityHelper) {
		this.chatRoomService = chatRoomService;
		this.securityHelper = securityHelper;
	}

	/**
	 * Endpoint for joining a chat room.
	 * It updates the chat room with the current user.
	 *
	 * @param chatRoomId The ID of the chat room to join.
	 * @param principal The principal of the current user.
	 */
	@PatchMapping("/{chatRoomId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void joinToChatRoom(@PathVariable("chatRoomId") String chatRoomId, Principal principal) {
		Long currentUser = securityHelper.getUserIdFromPrincipal(principal);
		chatRoomService.update(chatRoomId, currentUser);
	}

	@GetMapping("/defaultid")
	public ResponseEntity<String> getChatRoomId() {
		return ResponseEntity.ok(chatRoomService.findDefaultChatRoomId());
	}
}
