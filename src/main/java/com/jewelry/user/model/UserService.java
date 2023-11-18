package com.jewelry.user.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.user.dto.UserDto;
import com.jewelry.user.dto.UserResponseDto;
import com.jewelry.user.entity.UserRepository;
import com.jewelry.user.entity.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserRepositoryImpl userRepositoryImpl;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable) {
    Map<String, Object> response = new HashMap<>();

    Page<UserResponseDto> users = userRepositoryImpl.getSearchUsers(searchDto, pageable);

    searchDto.setTotalPage(users.getTotalPages());
    searchDto.setHasPrev(users.hasPrevious());
    searchDto.setHasNext(users.hasNext());
    searchDto.setTotalCount(users.getTotalElements());

    response.put("list", users.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final UserDto userDto){
    String userId = userRepository.save(userDto.toEntity()).getUserId();
    return ObjectUtils.isEmpty(userId) ? "fail" : "success";
  }

  @MenuAuthAnt
  public UserResponseDto find(final UserDto userDto){
    return userRepositoryImpl.getUser(userDto);
  }

  @MenuAuthAnt
  public UserResponseDto find(final String userId){
    return find(new UserDto(userId));
  }

  @MenuAuthAnt
  public String update(final UserDto userDto){
    return userRepositoryImpl.update(userDto) > 0 ? "success" : "fail";
  }

}
