package org.service.chatapp.model.request;

import java.time.LocalDateTime;

public record MessageDto(
		Long sender,
		String roomId,
		String message,
		LocalDateTime setTime
) {
}
