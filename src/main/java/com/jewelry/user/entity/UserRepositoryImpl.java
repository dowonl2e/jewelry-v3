package com.jewelry.user.entity;

import static com.jewelry.user.entity.QUserEntity.userEntity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.user.dto.QUserResponseDto;
import com.jewelry.user.dto.UserDto;
import com.jewelry.user.dto.UserResponseDto;
import com.jewelry.user.value.UserEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<UserResponseDto> getSearchUsers(final SearchDto searchDto, final Pageable pageable) {
    List<UserResponseDto> content = getUsers(searchDto, pageable);
    JPAQuery<Long> countQuery = getUserCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<UserResponseDto> getUsers(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QUserResponseDto(
            userEntity.userId, userEntity.userName, userEntity.userRole.stringValue()
            , userEntity.email, userEntity.celnum, userEntity.gender, userEntity.inptDt
            , userEntity.useYn)
        )
        .from(userEntity)
        .where(
            userEntity.userId.ne("admin")
                .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        )
        .orderBy(userEntity.inptDt.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getUserCount(final SearchDto searchDto){
    return queryFactory
        .select(userEntity.userId.count())
        .where(
            userEntity.userId.ne("admin")
                    .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        )
        .from(userEntity);
  }
  private BooleanExpression userIdLike(final String word){
    return word != null ? userEntity.userId.like("%"+word+"%") : null;
  }
  private BooleanExpression userNameLike(final String word){
    return word != null ? userEntity.userName.like("%"+word+"%") : null;
  }

  private BooleanExpression wordLike(final String type, final String word){
    if(type == null){
      BooleanExpression expression = userIdLike(word);
      return expression == null ? userNameLike(word) : expression.or(userNameLike(word));
    }
    else if(type.equals(UserEnum.ID))
      return userIdLike(word);
    else if(type.equals(UserEnum.NAME))
      return userNameLike(word);
    else
      return null;
  }

  @Override
  public UserResponseDto getUser(UserDto userDto) {
    return queryFactory
        .select(new QUserResponseDto(
            userEntity.userId, userEntity.userName, userEntity.userRole.stringValue()
            , userEntity.email, userEntity.celnum, userEntity.gender
            , userEntity.useYn)
        )
        .from(userEntity)
        .where(userEntity.userId.eq(userDto.getTgtUserId()))
        .fetchOne();
  }

  @Override
  public long update(UserDto userDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(userEntity)
        .set(userEntity.updtDt, LocalDateTime.now())
        .set(userEntity.updtId, userDto.getUpdtId());
    if(!ObjectUtils.isEmpty(userDto.getUserPwd())){
      updateClause
          .set(userEntity.userPwd, userDto.getUserPwd());
    }
    updateClause
        .set(userEntity.userName, userDto.getUserName())
        .set(userEntity.celnum, userDto.getCelnum())
        .set(userEntity.email, userDto.getEmail())
        .set(userEntity.gender, userDto.getGender())
        .set(userEntity.useYn, userDto.getUseYn())
        .where(userEntity.userId.eq(userDto.getTgtUserId()));
    return updateClause.execute();
  }

  @Override
  public Page<UserResponseDto> getSearchManagers(final SearchDto searchDto, final Pageable pageable) {
    List<UserResponseDto> content = getManagers(searchDto, pageable);
    JPAQuery<Long> countQuery = getManagerCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<UserResponseDto> getManagers(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QUserResponseDto(
            userEntity.userId, userEntity.userName, userEntity.userRole.stringValue()
            , userEntity.email, userEntity.celnum, userEntity.gender, userEntity.inptDt
            , userEntity.useYn)
        )
        .from(userEntity)
        .where(
            userEntity.userRole.stringValue().eq(searchDto.getSearchUserRole())
                .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        )
        .orderBy(userEntity.userName.desc(), userEntity.inptDt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getManagerCount(final SearchDto searchDto){
    return queryFactory
        .select(userEntity.userId.count())
        .where(
            userEntity.userRole.stringValue().eq(searchDto.getSearchUserRole())
                .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        )
        .from(userEntity);
  }
}
