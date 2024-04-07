package org.service.chatapp.service.impl;

import org.service.chatapp.model.domain.Message;
import org.service.chatapp.model.response.MessagePageResponse;
import org.service.chatapp.repository.MessageRepository;
import org.service.chatapp.service.MessageService;
import org.service.chatapp.util.MessageToMessageDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


/**
 * Service implementation for handling message-related operations.
 */
@Service
public class MessageServiceImpl implements MessageService {

	/**
	 * Repository for accessing message data from the database.
	 */
	private final MessageRepository messageRepository;

	/**
	 * Converter for converting message entities to message DTOs.
	 */
	private final MessageToMessageDtoConverter messageToMessageDtoConverter;

	/**
	 * Logger for the MessageServiceImpl class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


	/**
	 * Constructor for MessageServiceImpl, initializes messageRepository.
	 *
	 * @param messageRepository Repository for accessing message data.
	 */
	public MessageServiceImpl(MessageRepository messageRepository, MessageToMessageDtoConverter messageToMessageDtoConverter) {
		this.messageRepository = messageRepository;
        this.messageToMessageDtoConverter = messageToMessageDtoConverter;
    }

	/**
	 * Saves a message to the database.
	 *
	 * @param message The message to be saved.
	 */
	public void saveMessage(Message message) {
		logger.info("Saving message to chat room with ID: {}", message.getRoomId());
		messageRepository.save(message);
	}

	/**
	 * Retrieves a page of messages from a specific chat room.
	 *
	 * @param chatRoomId The ID of the chat room.
	 * @param page The page number to retrieve.
	 * @param size The number of messages per page.
	 * @return A page of messages from the specified chat room.
	 */
	@Override
	public MessagePageResponse getMessages(String chatRoomId, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Message> allMessagesByRoomId = messageRepository.findAllByRoomIdOrderBySentTimeDesc(chatRoomId, paging);
		logger.info("Retrieved messages from chat room with ID: {}", chatRoomId);
		return new MessagePageResponse(allMessagesByRoomId.getTotalPages(), page + 1,
				allMessagesByRoomId.getTotalElements(), allMessagesByRoomId.getContent().stream()
				.map(messageToMessageDtoConverter::convert).collect(Collectors.toList()));
	}
}
