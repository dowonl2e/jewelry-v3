package com.jewelry.cms.menu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuAuthID implements Serializable {

  private String userId;
  private String menuId;

}
