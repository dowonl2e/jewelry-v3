package com.jewelry.cms.menu.entity;

import static com.jewelry.user.entity.QUserEntity.userEntity;
import static com.jewelry.cms.menu.entity.QMenuAuth.menuAuth;
import static com.jewelry.cms.menu.entity.QMenu.menu;

import com.jewelry.cms.menu.dto.MenuAuthDto;
import com.jewelry.cms.menu.dto.MenuAuthResponseDto;
import com.jewelry.cms.menu.dto.QMenuAuthResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

@RequiredArgsConstructor
public class MenuAuthRepositoryImpl implements MenuAuthRepositoryCustom {

  private final JPAQueryFactory queryFactory;


  @Override
  public List<MenuAuthResponseDto> getMenuAuths(final String userId) {
    return queryFactory
        .select(new QMenuAuthResponseDto(
            menuAuth.userId, menuAuth.menuId, menu.menuNm
            , menuAuth.accessAuth, menuAuth.writeAuth, menuAuth.viewAuth
            , menuAuth.modifyAuth, menuAuth.removeAuth, menuAuth.inptId
            , menuAuth.inptDt)
        )
        .from(menu)
        .leftJoin(menu.menuAuths, menuAuth)
        .on(menuAuth.userId.eq(userId))
        .orderBy(menu.menuOrd.asc())
        .fetch();
  }

  @Override
  public long removeByUserId(String userId) {
    return queryFactory
        .delete(menuAuth)
        .where(menuAuth.userId.eq(userId))
        .execute();
  }

  @Override
  public long remove(String userId, String menuId) {
    return queryFactory
        .delete(menuAuth)
        .where(
            menuAuth.userId.eq(userId)
                .and(menuAuth.menuId.eq(menuId))
        )
        .execute();
  }

  @Override
  public long updateMenuAuth(final MenuAuthDto menuAuthDto) {
    return queryFactory
        .update(menuAuth)
        .set(menuAuth.accessAuth, menuAuthDto.getAccessAuth())
        .set(menuAuth.writeAuth, menuAuthDto.getWriteAuth())
        .set(menuAuth.viewAuth, menuAuthDto.getViewAuth())
        .set(menuAuth.modifyAuth, menuAuthDto.getModifyAuth())
        .set(menuAuth.removeAuth, menuAuthDto.getRemoveAuth())
        .where(
            menuAuth.userId.eq(menuAuthDto.getUserId())
                .and(menuAuth.menuId.eq(menuAuthDto.getMenuId()))
        )
        .execute();
  }

  @Override
  public MenuAuthResponseDto getMenuAuth(MenuAuthDto menuAuthDto) {
    MenuAuthResponseDto responseDto = queryFactory
        .select(new QMenuAuthResponseDto(
            menuAuth.menuId.coalesce(menuAuthDto.getMenuId()).as("menu_id")
            , userEntity.userRole
            , menuAuth.accessAuth, menuAuth.writeAuth
            , menuAuth.viewAuth, menuAuth.modifyAuth
            , menuAuth.removeAuth)
        )
        .from(userEntity)
        .leftJoin(menuAuth)
        .on(
            userEntity.userId.eq(menuAuth.userId)
                .and(menuAuth.menuId.eq(menuAuthDto.getMenuId()))
        )
        .where(
            userEntity.userId.eq(menuAuthDto.getUserId())
        )
        .fetchOne();

    if(responseDto == null){
      responseDto = new MenuAuthResponseDto(
          menuAuthDto.getMenuId(),
          null,
          null,
          null,
          null,
          null,
          null
      );
    }

    if(ObjectUtils.nullSafeEquals(responseDto.getUserRole().getRole(),"ROLE_ADMIN")){
      responseDto.setAccessAuth("Y");
      responseDto.setWriteAuth("Y");
      responseDto.setViewAuth("Y");
      responseDto.setModifyAuth("Y");
      responseDto.setRemoveAuth("Y");
    }
    else {
      responseDto.setAccessAuth(ObjectUtils.isEmpty(responseDto.getAccessAuth()) ? "N" : responseDto.getAccessAuth());
      responseDto.setWriteAuth(ObjectUtils.isEmpty(responseDto.getWriteAuth()) ? "N" : responseDto.getWriteAuth());
      responseDto.setViewAuth(ObjectUtils.isEmpty(responseDto.getViewAuth()) ? "N" : responseDto.getViewAuth());
      responseDto.setModifyAuth(ObjectUtils.isEmpty(responseDto.getModifyAuth()) ? "N" : responseDto.getModifyAuth());
      responseDto.setRemoveAuth(ObjectUtils.isEmpty(responseDto.getRemoveAuth()) ? "N" : responseDto.getRemoveAuth());
    }
    return responseDto;
  }
}
