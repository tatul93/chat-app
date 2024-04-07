package org.service.chatapp.service.impl;

import org.service.chatapp.exception.ResourceAlreadyExistException;
import org.service.chatapp.exception.ResourceNotFoundException;
import org.service.chatapp.model.domain.ChatRoom;
import org.service.chatapp.repository.ChatRoomRepository;
import org.service.chatapp.service.ChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing chat rooms.
 * It provides methods for saving, deleting, updating chat rooms and finding a chat room by its members.
 */
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	/**
	 * Logger for the ChatRoomServiceImpl class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ChatRoomServiceImpl.class);


	/**
	 * Constructor for ChatRoomServiceImpl, initializes chatRoomRepository.
	 *
	 * @param chatRoomRepository Repository for managing chat rooms in the database.
	 */
	public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}

	/**
	 * Saves a chat room to the database.
	 *
	 * @param chatRoom The chat room to save.
	 */
	@Override
	public void saveMessage(ChatRoom chatRoom) {
		chatRoomRepository.save(chatRoom);
	}

	 /**
	 * Deletes all chat rooms from the database.
	 */
	@Override
	public void deleteAll() {
		chatRoomRepository.deleteAll();
	}

	/**
	 * Updates a chat room with a new member.
	 * If the chat room does not exist, it throws a ResourceNotFoundException.
	 *
	 * @param chatRoomId The ID of the chat room to update.
	 * @param userId The ID of the user to add to the chat room.
	 */
	@Override
	public void update(String chatRoomId, Long userId) {
		Optional<ChatRoom> optional = chatRoomRepository.findById(chatRoomId);
		if (optional.isPresent()) {
			ChatRoom chatRoom = optional.get();
			if (chatRoom.getMembers() == null) {
				chatRoom.setMembers(new ArrayList<>());
			}
			if (chatRoom.getMembers().contains(userId)) {
				logger.info("User {} is already in the chat room {}", userId, chatRoomId);
				throw new ResourceAlreadyExistException("User is already in the chat room");
			}
			chatRoom.getMembers().add(userId);
			chatRoomRepository.save(chatRoom);
			logger.info("User {} joined the chat room {}", userId, chatRoomId);
		}
		else {
			throw new ResourceNotFoundException("ChatRoom not found");
		}
	}

	/**
	 * Finds a chat room by its members.
	 *
	 * @param memberId The ID of the member to find the chat room by.
	 * @return The chat room, if it exists.
	 */
	@Override
	public Optional<ChatRoom> findChatRoomByMembersContaining(Long memberId) {
		return chatRoomRepository.findChatRoomByMembersContaining(memberId);
	}

	/**
	 * Retrieves all chat rooms from the database.
	 *
	 * @return A list of all chat rooms.
	 */
	@Override
	public List<ChatRoom> findAll() {
		return chatRoomRepository.findAll();
	}


	//Added for testing purposes in the postman
	@Override
	public String findDefaultChatRoomId() {
		return chatRoomRepository.findAll().stream().map(ChatRoom::getId).findFirst().orElse(null);
	}

}
