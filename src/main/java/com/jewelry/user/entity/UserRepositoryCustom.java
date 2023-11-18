package com.jewelry.user.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.user.dto.UserDto;
import com.jewelry.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

  Page<UserResponseDto> getSearchUsers(final SearchDto searchDto, final Pageable pageable);

  UserResponseDto getUser(final UserDto userDto);

  long update(final UserDto userDto);

  Page<UserResponseDto> getSearchManagers(final SearchDto searchDto, final Pageable pageable);


}
