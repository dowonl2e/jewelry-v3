package com.jewelry.user.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEnum {
  ID("id"), NAME("name");
  String type;
}
