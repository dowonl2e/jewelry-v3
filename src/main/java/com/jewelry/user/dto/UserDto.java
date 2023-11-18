package com.jewelry.user.dto;

import com.jewelry.authentication.jwt.values.Role;
import com.jewelry.common.dto.CommonDto;
import com.jewelry.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends CommonDto {

  private String userPwd;
  private String userName;
  private Role userRole;
  private String email;
  private String celnum;
  private String gender;
  private String useYn;

  public UserDto(String userId){
    super.setTgtUserId(userId);
  }

  @Builder
  public UserEntity toEntity(){
    return UserEntity.builder()
        .userId(super.getTgtUserId())
        .userPwd(this.userPwd)
        .userName(this.userName)
        .email(this.email)
        .celnum(this.celnum)
        .gender(this.gender)
        .useYn(this.useYn)
        .userRole(this.userRole)
        .inptId(super.getInptId())
        .build();
  }
}
