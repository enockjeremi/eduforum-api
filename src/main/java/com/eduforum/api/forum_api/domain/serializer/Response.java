package com.eduforum.api.forum_api.domain.serializer;

public record Response(
    Boolean success,
    Record data
) {
}
