package com.eduforum.api.forum_api.domain.serializer;

import org.springframework.data.domain.Page;

public record PageMetadata<T>(
    int current,
    int size,
    boolean first,
    boolean last,
    int total_pages,
    long total_elements
) {
  public PageMetadata(Page<T> page) {
    this(page.getNumber(), page.getSize(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements());
  }
}
