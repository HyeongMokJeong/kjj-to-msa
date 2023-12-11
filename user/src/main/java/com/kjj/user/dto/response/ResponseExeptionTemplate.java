package com.kjj.user.dto.response;

public record ResponseExeptionTemplate(
        String date,
        String message,
        String uri,
        int status
) {
}
