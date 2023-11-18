package com.jewelry.catalog.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatalogEnum {
  ID("id"), NAME("name");
  String type;
}
