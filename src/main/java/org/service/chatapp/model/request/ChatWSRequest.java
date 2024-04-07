package org.service.chatapp.model.request;

import javax.validation.constraints.NotEmpty;

public record ChatWSRequest(
		@NotEmpty String message
) {
}
