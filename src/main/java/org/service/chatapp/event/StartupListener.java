package org.service.chatapp.event;

import org.service.chatapp.model.domain.ChatRoom;
import org.service.chatapp.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component

public class StartupListener {

	private final ChatRoomService chatRoomService;

	@Autowired
	public StartupListener(ChatRoomService chatRoomService) {
		this.chatRoomService = chatRoomService;
	}

	/**
	 * Handles the application ready event.
	 * It checks if there are any chat rooms in the database and creates a default one if none exist.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationEvent() {
		if (chatRoomService.findAll().isEmpty()) {
			ChatRoom chatRoom = new ChatRoom();
			chatRoom.setName("DEFAULT_CHAT");
			chatRoomService.saveMessage(chatRoom);
		}
	}
}
