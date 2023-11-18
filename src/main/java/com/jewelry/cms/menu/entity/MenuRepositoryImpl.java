package com.jewelry.cms.menu.entity;

import com.jewelry.cms.menu.dto.MenuDto;
import com.jewelry.cms.menu.dto.MenuResponseDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jewelry.cms.menu.entity.QMenu.menu;
import static com.jewelry.cms.menu.entity.QMenuAuth.menuAuth;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<MenuResponseDto> getMenusByUserId(MenuDto menuDto) {
    QMenu childMenu = new QMenu("childMenu");
    return queryFactory
        .select(
            Projections.fields(MenuResponseDto.class,
                menu.menuId, menu.menuNm, menu.menuLink
                , menu.menuOrd, menu.upMenuId, menu.useYn
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(childMenu.menuId.count())
                        .from(childMenu)
                        .where(
                            childMenu.upMenuId.eq(menu.menuId)
                                .and(childMenu.menuDepth.eq(menu.menuDepth.add(1)))
                                .and(childMenu.useYn.eq("Y"))
                        )
                    , "childCnt"
                )
            )
        )
        .from(menu)
        .innerJoin(menu.menuAuths, menuAuth)
        .on(
            menuAuth.userId.eq(menuDto.getUserId())
                .and(menuAuth.accessAuth.eq("Y"))
        )
        .where(
            menu.menuDepth.eq(menuDto.getMenuDepth())
                .and(useYnEq(menuDto.getUseYn()))
        )
        .orderBy(menu.menuOrd.asc(), menu.menuId.asc())
        .fetch();
  }

  @Override
  public List<MenuResponseDto> getMenusByDepth(MenuDto menuDto) {
    QMenu childMenu = new QMenu("childMenu");
    return queryFactory
        .select(
            Projections.fields(MenuResponseDto.class,
                menu.menuId, menu.menuNm, menu.menuLink
                , menu.menuOrd, menu.upMenuId, menu.useYn
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(childMenu.menuId.count())
                        .from(childMenu)
                        .where(
                            childMenu.upMenuId.eq(menu.menuId)
                                .and(childMenu.menuDepth.eq(menu.menuDepth.add(1)))
                                .and(childMenu.useYn.eq("Y"))
                        )
                    , "childCnt"
                )
            )
        )
        .from(menu)
        .where(
            menu.menuDepth.eq(menuDto.getMenuDepth())
                .and(useYnEq(menuDto.getUseYn()))
        )
        .orderBy(menu.menuOrd.asc(), menu.menuId.asc())
        .fetch();
  }

  private BooleanExpression useYnEq(String useYn){
    return useYn != null ? menu.useYn.eq(useYn) : null;
  }

}
