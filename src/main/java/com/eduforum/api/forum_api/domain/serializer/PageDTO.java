package com.eduforum.api.forum_api.domain.serializer;

import java.util.List;

public record PageDTO<T>(
    List<T> data,
    PageMetadata<T> page
) {
}
