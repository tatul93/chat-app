package org.service.chatapp.util;

import org.service.chatapp.model.domain.Message;
import org.service.chatapp.model.request.MessageDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageToMessageDtoConverter {

    public MessageDto convert(Message message) {
        return new MessageDto(
                message.getOwnerId(),
                message.getRoomId(),
                message.getMessageText(),
                message.getSentTime()
        );
    }

}
