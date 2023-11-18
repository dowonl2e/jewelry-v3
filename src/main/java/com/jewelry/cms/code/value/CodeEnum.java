package com.jewelry.cms.code.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
  ID("id"), NAME("name");
  String type;
}
