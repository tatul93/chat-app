package org.service.chatapp.model.response;

import org.service.chatapp.model.request.MessageDto;

import java.util.List;

public record MessagePageResponse(

		long totalPages,
		long currentPage,
		long totalItems,
		List<MessageDto> messages
) {
}
