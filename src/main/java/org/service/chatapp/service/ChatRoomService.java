package org.service.chatapp.service;

import org.service.chatapp.model.domain.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

	void saveMessage(ChatRoom chatRoom);

	void deleteAll();

	void update(String chatRoomId, Long userId);

	Optional<ChatRoom> findChatRoomByMembersContaining(Long memberId);

	List<ChatRoom> findAll();

	String findDefaultChatRoomId();
}
