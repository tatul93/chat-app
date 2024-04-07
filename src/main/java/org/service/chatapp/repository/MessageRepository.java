package org.service.chatapp.repository;

import org.service.chatapp.model.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
    Page<Message> findAllByRoomIdOrderBySentTimeDesc(String roomId, Pageable pageable);

}

