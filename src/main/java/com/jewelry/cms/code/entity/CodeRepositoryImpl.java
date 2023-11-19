package com.jewelry.cms.code.entity;

import com.jewelry.cms.code.dto.CodeDto;
import com.jewelry.cms.code.dto.CodeResponseDto;
import com.jewelry.cms.code.dto.QCodeResponseDto;
import com.jewelry.cms.code.value.CodeEnum;
import com.jewelry.common.domain.SearchDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.jewelry.cms.code.entity.QCode.code;
import static com.jewelry.user.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<CodeResponseDto> getSearchCodes(final SearchDto searchDto, final Pageable pageable) {
    List<CodeResponseDto> content = getCodes(searchDto, pageable);
    JPAQuery<Long> countQuery = getCodesCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }
  private List<CodeResponseDto> getCodes(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QCodeResponseDto(
            code.cdId, code.cdNm, code.cdOrd
            , code.upCdId, code.cdDepth, code.useYn
            , code.inptId, userEntity.userName
            , code.inptDt)
        )
        .from(code)
        .leftJoin(code.userEntity, userEntity)
        .where(
            code.cdDepth.eq(searchDto.getCdDepth())
                .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        )
        .orderBy(code.cdOrd.asc(), code.cdId.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getCodesCount(final SearchDto searchDto){
    return queryFactory
        .select(code.count())
        .from(code)
        .where(
            code.cdDepth.eq(searchDto.getCdDepth())
                .and(wordLike(searchDto.getSearchType(), searchDto.getSearchWord()))
        );
  }
  private BooleanExpression cdIdLike(String word){
    return word != null ? code.cdId.like("%"+word+"%") : null;
  }
  private BooleanExpression cdNmLike(String word){
    return word != null ? code.cdNm.like("%"+word+"%") : null;
  }

  private BooleanExpression wordLike(String type, String word){
    if(type == null){
      BooleanExpression expression = cdIdLike(word);
      return expression == null ? cdNmLike(word) : expression.or(cdNmLike(word));
    }
    else if(type.equals(CodeEnum.ID))
      return cdIdLike(word);
    else if(type.equals(CodeEnum.NAME))
      return cdNmLike(word);
    else
      return null;
  }

  @Override
  public Integer getMaxOrder(final String upCdId, final Integer cdDepth){
    return queryFactory
        .select(code.cdOrd.max())
        .from(code)
        .where(
            code.upCdId.eq(upCdId)
                .and(code.cdDepth.eq(cdDepth))
        )
        .fetchOne();
  }

  @Override
  public CodeResponseDto getCode(final String cdId) {
    QCode subCode = new QCode("subCode");
    return queryFactory
        .select(
            Projections.fields(CodeResponseDto.class,
              code.cdId, code.cdNm, code.cdOrd
              , code.upCdId, code.cdDepth, code.cdDesc
              , code.useYn, code.inptId, code.inptDt
              , ExpressionUtils.as(
                  JPAExpressions
                      .select(subCode.cdId.count())
                      .from(subCode)
                      .where(subCode.upCdId.eq(code.cdId))
                  , "childCnt"
              )
            )
        )
        .from(code)
        .where(code.cdId.eq(cdId))
        .fetchOne();
  }

  @Override
  public List<CodeResponseDto> getLowCodes(final CodeDto codeDto){
    return queryFactory
        .select(new QCodeResponseDto(
            code.cdId, code.cdNm, code.cdOrd
            , code.upCdId, code.cdDepth, code.useYn
            , code.inptId, userEntity.userName, code.inptDt)
        )
        .from(code)
        .leftJoin(code.userEntity, userEntity)
        .where(
          code.upCdId.eq(codeDto.getUpCdId())
              .and(code.cdDepth.eq(codeDto.getCdDepth()))
        )
        .orderBy(code.cdOrd.asc())
        .fetch();
  }

  @Override
  public long updateCode(final CodeDto codeDto) {
    return queryFactory
        .update(code)
        .set(code.updtDt, LocalDateTime.now())
        .set(code.updtId, codeDto.getUpdtId())
        .set(code.cdNm, codeDto.getCdNm())
        .set(code.cdDesc, codeDto.getCdDesc())
        .set(code.useYn, codeDto.getUseYn())
        .where(code.cdId.eq(codeDto.getCdId()))
        .execute();
  }

  @Override
  public long deleteCode(String cdId) {
    return queryFactory
        .delete(code)
        .where(code.cdId.eq(cdId))
        .execute();
  }

  @Override
  public long deleteLowCodeByCdId(String cdId) {
    return queryFactory
        .delete(code)
        .where(code.upCdId.substring(0, cdId.length()).eq(cdId))
        .execute();
  }

  @Override
  public List<CodeResponseDto> getCodesByUpCdId(CodeDto codeDto) {
    return queryFactory
        .select(new QCodeResponseDto(
            code.cdId, code.cdNm, code.cdOrd)
        )
        .from(code)
        .where(
            code.upCdId.in(codeDto.getUpCdIdArr())
                .and(code.cdDepth.eq(codeDto.getCdDepth()))
        )
        .orderBy(code.cdOrd.asc())
        .fetch();
  }
}
