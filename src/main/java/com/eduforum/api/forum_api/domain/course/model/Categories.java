package com.eduforum.api.forum_api.domain.course.model;

import com.eduforum.api.forum_api.infra.errors.ValidateException;

public enum Categories {
  FRONTEND("frontend"),
  BACKEND("backend"),
  DESARROLLO_PERSONAL("desarrollo_personal"),
  IDIOMAS("idiomas"),
  JAVA("java"),
  JAVASCRIPT("javascript"),
  NODEJS("nodejs"),
  SPRING_BOOT("spring_boot");

  private final String categorySelect;

  Categories(String category) {
    this.categorySelect = category;
  }

  public static Categories fromString(String text) {
    for (Categories categories : Categories.values()) {
      if (categories.categorySelect.equalsIgnoreCase(text)) {
        return categories;
      }
    }
    throw new ValidateException("category " + text + " not found");
  }
}
