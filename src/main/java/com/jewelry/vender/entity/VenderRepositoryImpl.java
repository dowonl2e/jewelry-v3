package com.jewelry.vender.entity;

import static com.jewelry.vender.entity.QVender.vender;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.vender.dto.QVenderResponseDto;
import com.jewelry.vender.dto.VenderDto;
import com.jewelry.vender.dto.VenderResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class VenderRepositoryImpl implements VenderRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<VenderResponseDto> getSearchVenders(final SearchDto searchDto, final Pageable pageable) {
    List<VenderResponseDto> content = getVenders(searchDto, pageable);
    JPAQuery<Long> countQuery = getVenderCount(searchDto);

    /**
     * new PageImpl<>(content, pageable, countQuery.fetchOne());
     * -> 전체 개수를 구하는 쿼리를 무조건 실행함.
     *
     * PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
     * -> 필요에 의해서만 카운트 쿼리를 실행함
     *  - 페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
     *  - 마지막 페이지일 때 (offset + 컨텐츠 사이즈를 더해서 전체 사이즈 구함)
     */
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<VenderResponseDto> getVenders(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QVenderResponseDto(
            vender.venderNo, vender.venderNm, vender.businessNm
            , vender.agentCel, vender.vatCd, vender.meltCd
            , vender.venderFax, vender.venderCel1
            , vender.managerNm, vender.managerCel, vender.etc
            , vender.inptDt)
        )
        .from(vender)
        .where(
            delYnEq(searchDto.getSearchDelYn()),
            wordLike(searchDto.getSearchWord())
        )
        .orderBy(vender.venderNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getVenderCount(final SearchDto searchDto){
    return queryFactory
        .select(vender.count())
        .from(vender)
        .where(
            delYnEq(searchDto.getSearchDelYn()),
            wordLike(searchDto.getSearchWord())
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? vender.delYn.eq(delYn) : null;
  }
  private BooleanExpression venderNmLike(String word){
    return word != null ? vender.venderNm.like("%"+word+"%") : null;
  }
  private BooleanExpression businessNmLike(String word){
    return word != null ? vender.businessNm.like("%"+word+"%") : null;
  }

  private BooleanExpression wordLike(String word){
    BooleanExpression expression = venderNmLike(word);
    return expression == null ? businessNmLike(word) : expression.or(businessNmLike(word));
  }

  @Override
  public VenderResponseDto getVenderByVenderNo(final Long venderNo) {
    return queryFactory
        .select(new QVenderResponseDto(
            vender.venderNo, vender.venderNm, vender.businessNm
            , vender.agentCel, vender.vatCd, vender.meltCd
            , vender.venderFax, vender.venderCel1, vender.venderCel2
            , vender.managerNm, vender.managerCel, vender.managerEmail
            , vender.etc, vender.commerce)
        )
        .from(vender)
        .where(
            vender.venderNo.eq(venderNo)
                .and(vender.delYn.eq("N"))
        )
        .fetchOne();
  }

  @Override
  public long updateVender(VenderDto venderDto) {
    return queryFactory
        .update(vender)
        .set(vender.updtDt, LocalDateTime.now())
        .set(vender.updtId, venderDto.getUpdtId())
        .set(vender.venderNm, venderDto.getVenderNm())
        .set(vender.businessNm, venderDto.getBusinessNm())
        .set(vender.agentCel, venderDto.getAgentCel())
        .set(vender.vatCd, venderDto.getVatCd())
        .set(vender.meltCd, venderDto.getMeltCd())
        .set(vender.venderFax, venderDto.getVenderFax())
        .set(vender.venderCel1, venderDto.getVenderCel1())
        .set(vender.venderCel2, venderDto.getVenderCel2())
        .set(vender.managerNm, venderDto.getManagerNm())
        .set(vender.managerCel, venderDto.getManagerCel())
        .set(vender.managerEmail, venderDto.getManagerEmail())
        .set(vender.etc, venderDto.getEtc())
        .set(vender.commerce, venderDto.getCommerce())
        .where(vender.venderNo.eq(venderDto.getVenderNo()))
        .execute();
  }

  @Override
  public long updateVendersToDelete(VenderDto venderDto) {
    return queryFactory
        .update(vender)
        .set(vender.updtDt, LocalDateTime.now())
        .set(vender.updtId, venderDto.getUpdtId())
        .set(vender.delYn, venderDto.getDelYn())
        .where(vender.venderNo.in(venderDto.getVenderNoArr()))
        .execute();
  }
}
