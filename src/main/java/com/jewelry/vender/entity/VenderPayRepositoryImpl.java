package com.jewelry.vender.entity;

import static com.jewelry.vender.entity.QVender.vender;
import static com.jewelry.vender.entity.QVenderPay.venderPay;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.util.Utils;
import com.jewelry.vender.dto.QVenderPayResponseDto;
import com.jewelry.vender.dto.VenderPayDto;
import com.jewelry.vender.dto.VenderPayResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class VenderPayRepositoryImpl implements VenderPayRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<VenderPayResponseDto> getSearchVenderPays(final SearchDto searchDto, final Pageable pageable) {
    List<VenderPayResponseDto> content= getVenderPays(searchDto, pageable);
    JPAQuery<Long> countQuery = getVenderPaysCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }
  private List<VenderPayResponseDto> getVenderPays(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QVenderPayResponseDto(
            venderPay.payNo, venderPay.venderNo, vender.venderNm
            , venderPay.regDt, venderPay.exptGoldGram, venderPay.exptPayPrice
            , venderPay.prgGoldGram, venderPay.prgPayPrice, venderPay.payEtc)
        )
        .from(venderPay)
        .leftJoin(venderPay.vender, vender)
        .where(
            delYnEq(searchDto.getSearchDelYn())
              .and(storeEq(searchDto.getSearchStore()))
              .and(regDtEq(searchDto.getSearchStdt()))
              .and(venderNmLike(searchDto.getSearchWord()))
        )
        .orderBy(venderPay.regDt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getVenderPaysCount(final SearchDto searchDto){
    return queryFactory
        .select(venderPay.count())
        .from(venderPay)
        .leftJoin(venderPay.vender, vender)
        .where(
            delYnEq(searchDto.getSearchDelYn())
              .and(storeEq(searchDto.getSearchStore()))
              .and(regDtEq(searchDto.getSearchStdt()))
              .and(venderNmLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? venderPay.delYn.eq(delYn) : null;
  }
  private BooleanExpression storeEq(String storeCd){
    return storeCd != null ? venderPay.storeCd.eq(storeCd) : null;
  }
  private BooleanExpression regDtEq(String regDt){
    StringExpression formattter = Expressions.stringTemplate(
        "FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')",
        venderPay.regDt
    );
    return regDt != null ? formattter.eq(regDt) : null;
  }
  private BooleanExpression venderNmLike(String word){
    return word != null ? venderPay.vender.venderNm.like("%"+word+"%") : null;
  }

  @Override
  public VenderPayResponseDto getVenderPayByPayNo(Long payNo) {
    return queryFactory
        .select(new QVenderPayResponseDto(
            venderPay.payNo, venderPay.venderNo, vender.venderNm
            , venderPay.regDt, venderPay.storeCd, venderPay.exptGoldGram
            , venderPay.exptPayPrice, venderPay.prgGoldGram, venderPay.prgPayPrice
            , venderPay.payEtc)
        )
        .from(venderPay)
        .leftJoin(venderPay.vender, vender)
        .where(venderPay.payNo.eq(payNo))
        .fetchOne();
  }

  @Override
  public long updateVenderPay(VenderPayDto venderPayDto) {
    return queryFactory
        .update(venderPay)
        .set(venderPay.updtDt, LocalDateTime.now())
        .set(venderPay.updtId, venderPayDto.getUpdtId())
        .set(venderPay.venderNo, venderPayDto.getVenderNo())
        .set(venderPay.regDt, Utils.convertLocalDateTime(venderPayDto.getRegDt()))
        .set(venderPay.storeCd, venderPayDto.getStoreCd())
        .set(venderPay.exptGoldGram, venderPayDto.getExptGoldGram())
        .set(venderPay.exptPayPrice, venderPayDto.getExptPayPrice())
        .set(venderPay.prgGoldGram, venderPayDto.getPrgGoldGram())
        .set(venderPay.prgPayPrice, venderPayDto.getPrgPayPrice())
        .set(venderPay.payEtc, venderPayDto.getPayEtc())
        .where(venderPay.payNo.eq(venderPayDto.getPayNo()))
        .execute();
  }

  @Override
  public long updateVenderPaysToDelete(VenderPayDto venderPayDto) {
    return queryFactory
        .update(venderPay)
        .set(venderPay.updtDt, LocalDateTime.now())
        .set(venderPay.updtId, venderPayDto.getUpdtId())
        .set(venderPay.delYn, venderPayDto.getDelYn())
        .where(venderPay.payNo.in(venderPayDto.getVenderPayNoArr()))
        .execute();
  }
}
