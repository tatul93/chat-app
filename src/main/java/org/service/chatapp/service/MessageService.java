package org.service.chatapp.service;

import org.service.chatapp.model.domain.Message;
import org.service.chatapp.model.response.MessagePageResponse;

public interface MessageService {

	void saveMessage(Message message);

	MessagePageResponse getMessages(String chatRoomId, int page, int size);

}
