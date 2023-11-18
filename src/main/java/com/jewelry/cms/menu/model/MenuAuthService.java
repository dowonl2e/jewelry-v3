package com.jewelry.cms.menu.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.cms.menu.dto.MenuAuthDto;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;
import com.jewelry.cms.menu.entity.MenuAuth;
import com.jewelry.cms.menu.entity.MenuAuthBulkRepositoryImpl;
import com.jewelry.cms.menu.entity.MenuAuthRepository;
import com.jewelry.cms.menu.entity.MenuAuthRepositoryImpl;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.user.dto.UserResponseDto;
import com.jewelry.user.entity.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuAuthService {

  private final UserRepositoryImpl userRepositoryImpl;
  private final MenuAuthRepository menuAuthRepository;
  private final MenuAuthRepositoryImpl menuAuthRepositoryImpl;

  private final MenuAuthBulkRepositoryImpl bulkRepository;

  @MenuAuthAnt
  public Map<String, Object> findAllUsers(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<UserResponseDto> users = userRepositoryImpl.getSearchManagers(searchDto, pageable);

    searchDto.setTotalPage(users.getTotalPages());
    searchDto.setHasPrev(users.hasPrevious());
    searchDto.setHasNext(users.hasNext());
    searchDto.setTotalCount(users.getTotalElements());

    response.put("list", users.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public Map<String, Object> findAll(final MenuAuthDto menuAuthDto){
    Map<String, Object> response = new HashMap<>();

    response.put("list", menuAuthRepositoryImpl.getMenuAuths(menuAuthDto.getTgtUserId()));
    response.put("params", menuAuthDto);

    return response;
  }

  @MenuAuthAnt
  public String bulkInsert(final MenuAuthDto menuAuthDto){
    long res = 0;
    if(!ObjectUtils.isEmpty(menuAuthDto.getTgtUserId())
        && !ObjectUtils.isEmpty(menuAuthDto.getMenuIdArr())){

      List<MenuAuth> insertList = new ArrayList<>();

      LocalDateTime nowDate = LocalDateTime.now();
      int idx = 0;
      for(String menuId: menuAuthDto.getMenuIdArr()){
        MenuAuthDto authDto = new MenuAuthDto();
        authDto.setUserId(menuAuthDto.getTgtUserId());
        authDto.setMenuId(menuId);
        authDto.setAccessAuth(
            !ObjectUtils.nullSafeEquals(menuAuthDto.getAccessAuthArr()[idx], "Y") ? "N" : menuAuthDto.getAccessAuthArr()[idx]
        );
        authDto.setWriteAuth(
            !ObjectUtils.nullSafeEquals(menuAuthDto.getWriteAuthArr()[idx], "Y") ? "N" : menuAuthDto.getWriteAuthArr()[idx]
        );
        authDto.setViewAuth(
            !ObjectUtils.nullSafeEquals(menuAuthDto.getViewAuthArr()[idx], "Y") ? "N" : menuAuthDto.getViewAuthArr()[idx]
        );
        authDto.setModifyAuth(
            !ObjectUtils.nullSafeEquals(menuAuthDto.getModifyAuthArr()[idx], "Y") ? "N" : menuAuthDto.getModifyAuthArr()[idx]
        );
        authDto.setRemoveAuth(
            !ObjectUtils.nullSafeEquals(menuAuthDto.getRemoveAuthArr()[idx], "Y") ? "N" : menuAuthDto.getRemoveAuthArr()[idx]
        );

        authDto.setInptId(menuAuthDto.getInptId());
        insertList.add(authDto.toEntity());
        idx++;
      }

      if(!CollectionUtils.isEmpty(insertList)){
        menuAuthRepositoryImpl.removeByUserId(menuAuthDto.getTgtUserId());
        res = menuAuthRepository.saveAll(insertList).size();
      }
    }
    return res > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String save(final MenuAuthDto menuAuthDto){
    menuAuthRepositoryImpl.remove(menuAuthDto.getTgtUserId(), menuAuthDto.getMenuId());
    boolean res = !ObjectUtils.isEmpty(menuAuthRepository.save(menuAuthDto.toEntity()).getMenuId()) ? true : false;
    return res == true ? "success" : "fail";
  }

  public MenuAuthResponseDto find(final MenuAuthDto menuAuthDto){
    return menuAuthRepositoryImpl.getMenuAuth(menuAuthDto);
  }
}
