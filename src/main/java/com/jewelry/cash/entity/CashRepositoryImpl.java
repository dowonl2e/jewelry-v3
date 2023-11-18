package com.jewelry.cash.entity;

import com.jewelry.cash.dto.CashDto;
import com.jewelry.cash.dto.CashResponseDto;
import com.jewelry.cash.dto.QCashResponseDto;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.util.Utils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jewelry.cash.entity.QCash.cash;

@RequiredArgsConstructor
public class CashRepositoryImpl implements CashRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<CashResponseDto> getSearchCashes(SearchDto searchDto, Pageable pageable) {
    List<CashResponseDto> content = getCashes(searchDto, pageable);
    JPAQuery<Long> countQuery = getCashCount(searchDto);
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

  private List<CashResponseDto> getCashes(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(Projections.bean(CashResponseDto.class,
            cash.cashNo, cash.regDt, cash.storeCd
            , cash.cashTypeCd, cash.cashTypeCd2, cash.bankbookCd
            , cash.venderNm, cash.historyDesc, cash.materialCd
            , cash.weightGram, cash.quantity, cash.unitPrice
            , new CaseBuilder()
                .when(cash.regDt.dayOfWeek().eq(1)).then("일")
                .when(cash.regDt.dayOfWeek().eq(2)).then("월")
                .when(cash.regDt.dayOfWeek().eq(3)).then("화")
                .when(cash.regDt.dayOfWeek().eq(4)).then("수")
                .when(cash.regDt.dayOfWeek().eq(5)).then("목")
                .when(cash.regDt.dayOfWeek().eq(6)).then("금")
                .when(cash.regDt.dayOfWeek().eq(7)).then("토")
                .otherwise("-").as("regDay"))
        )
        .from(cash)
        .where(
            cash.delYn.eq("N")
                .and(storeCdEq(searchDto.getSearchStore()))
                .and(cashTypeEq(searchDto.getSearchCashType()))
                .and(bankbookEq(searchDto.getSearchBankbook()))
                .and(cashType2Eq(searchDto.getSearchCashType2()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(cash.regDt.desc(), cash.cashNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getCashCount(final SearchDto searchDto){
    return queryFactory
        .select(cash.cashNo.count())
        .from(cash)
        .where(
            cash.delYn.eq("N")
                .and(storeCdEq(searchDto.getSearchStore()))
                .and(cashTypeEq(searchDto.getSearchCashType()))
                .and(bankbookEq(searchDto.getSearchBankbook()))
                .and(cashType2Eq(searchDto.getSearchCashType2()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression storeCdEq(String storeCd){
    return storeCd != null ? cash.storeCd.eq(storeCd) : null;
  }
  private BooleanExpression cashTypeEq(String cashTypeCd){
    return cashTypeCd != null ? cash.cashTypeCd.eq(cashTypeCd) : null;
  }
  private BooleanExpression bankbookEq(String bankbookCd){
    return bankbookCd != null ? cash.bankbookCd.eq(bankbookCd) : null;
  }
  private BooleanExpression cashType2Eq(String cashTypeCd2){
    return cashTypeCd2 != null ? cash.cashTypeCd2.eq(cashTypeCd2) : null;
  }
  private BooleanExpression materialEq(String materialCd){
    return materialCd != null ? cash.materialCd.eq(materialCd) : null;
  }
  private BooleanExpression regDtGoe(String stdt){
    return stdt != null ? cash.regDt.goe(Utils.convertLocalDateTime(stdt)) : null;
  }
  private BooleanExpression regDtLoe(String eddt){
    return eddt != null ? cash.regDt.loe(Utils.convertLocalDateTime(eddt)) : null;
  }
  private BooleanExpression wordLike(String word){
    BooleanExpression expression = venderNmLike(word);
    return expression == null ? historyDescLike(word) : expression.or(historyDescLike(word));
  }
  private BooleanExpression venderNmLike(String word){
    return word != null ? cash.venderNm.like("%"+word+"%") : null;
  }
  private BooleanExpression historyDescLike(String word){
    return word != null ? cash.historyDesc.like("%"+word+"%") : null;
  }
  @Override
  public List<CashResponseDto> getCashStatsList(SearchDto searchDto, Pageable pageable) {
    QCash subCash = new QCash("subCash");
    List<CashResponseDto> cashes = queryFactory
        .select(Projections.fields(CashResponseDto.class,
            Expressions.asNumber(1).as("statsOrd"),
            cash.storeCd, cash.bankbookCd.as("statsCd")
            , ExpressionUtils.as(
                JPAExpressions
                    .select(subCash.unitPrice.sum())
                    .from(subCash)
                    .where(
                        subCash.cashTypeCd.eq("RS01")
                            .and(subCash.storeCd.eq(cash.storeCd))
                            .and(subCash.bankbookCd.eq(cash.bankbookCd))
                            .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getBefYesterday())))
                            .and(subCash.delYn.eq("N"))
                    )
                , "befYesterdayPrice"
            )
            , ExpressionUtils.as(
                JPAExpressions
                    .select(subCash.unitPrice.sum())
                    .from(subCash)
                    .where(
                        subCash.cashTypeCd.eq("RS02")
                            .and(subCash.storeCd.eq(cash.storeCd))
                            .and(subCash.bankbookCd.eq(cash.bankbookCd))
                            .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getBefYesterday())))
                            .and(subCash.delYn.eq("N"))
                    )
                , "befYesterdayPrice2"
            )
            , ExpressionUtils.as(
                JPAExpressions
                    .select(subCash.unitPrice.sum())
                    .from(subCash)
                    .where(
                        subCash.cashTypeCd.eq("RS01")
                            .and(subCash.storeCd.eq(cash.storeCd))
                            .and(subCash.bankbookCd.eq(cash.bankbookCd))
                            .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getYesterday())))
                            .and(subCash.delYn.eq("N"))
                    )
                , "yesterdayPrice"
            )
            , ExpressionUtils.as(
                JPAExpressions
                    .select(subCash.unitPrice.sum())
                    .from(subCash)
                    .where(
                        subCash.cashTypeCd.eq("RS02")
                            .and(subCash.storeCd.eq(cash.storeCd))
                            .and(subCash.bankbookCd.eq(cash.bankbookCd))
                            .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getYesterday())))
                            .and(subCash.delYn.eq("N"))
                    )
                , "yesterdayPrice2"
            )
          )
        )
        .from(cash)
        .where(
            cash.delYn.eq("N")
                .and(storeCdEq(searchDto.getSearchStore()))
                .and(cashTypeEq(searchDto.getSearchCashType()))
                .and(bankbookEq(searchDto.getSearchBankbook()))
                .and(cashType2Eq(searchDto.getSearchCashType2()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .groupBy(cash.storeCd, cash.bankbookCd)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();


    if(!ObjectUtils.isEmpty(cashes)){

      List<String> storeBanks = new ArrayList<>();
      for(CashResponseDto cashResponseDto : cashes){
        String storeCd = ObjectUtils.isEmpty(cashResponseDto.getStoreCd()) ? "" : cashResponseDto.getStoreCd();
        String bankbookCd = ObjectUtils.isEmpty(cashResponseDto.getBankbookCd()) ? "" : cashResponseDto.getBankbookCd();
        storeBanks.add(storeCd+bankbookCd);
      }

      List<CashResponseDto> todayCashes = queryFactory
          .select(new QCashResponseDto(
                cash.storeCd, cash.bankbookCd
                , cash.cashTypeCd.when("RS01").then(cash.unitPrice).otherwise(0).as("todayPrice")
                , cash.cashTypeCd.when("RS02").then(cash.unitPrice).otherwise(0).as("todayPrice2")
              )
          )
          .from(cash)
          .where(
              cash.cashTypeCd.in("RS01","RS02")
                  .and(cash.storeCd.concat(cash.bankbookCd).in(storeBanks))
                  .and(cash.bankbookCd.eq(cash.bankbookCd))
                  .and(cash.regDt.eq(Utils.convertLocalDateTime(searchDto.getToday())))
                  .and(cash.delYn.eq("N"))
                  .and(storeCdEq(searchDto.getSearchStore()))
                  .and(cashTypeEq(searchDto.getSearchCashType()))
                  .and(bankbookEq(searchDto.getSearchBankbook()))
                  .and(cashType2Eq(searchDto.getSearchCashType2()))
                  .and(materialEq(searchDto.getSearchMaterial()))
                  .and(wordLike(searchDto.getSearchWord()))
          )
          .orderBy(cash.regDt.desc(), cash.cashNo.desc())
          .offset(pageable.getOffset())
          .limit(pageable.getPageSize())
          .fetch();

      for(CashResponseDto cashResponseDto : cashes){
        if(!ObjectUtils.isEmpty(todayCashes)){
          int todayPriceSum = cashResponseDto.getTodayPrice() == null ? 0 : cashResponseDto.getTodayPrice();
          for(CashResponseDto todayResponseDto : cashes) {
            if(ObjectUtils.nullSafeEquals(cashResponseDto.getStoreCd(), todayResponseDto.getStoreCd())
                && ObjectUtils.nullSafeEquals(cashResponseDto.getBankbookCd(), todayResponseDto.getBankbookCd())) {
              Integer todayPrice = todayResponseDto.getTodayPrice() == null ? 0 : todayResponseDto.getTodayPrice();
              Integer todayPrice2 = todayResponseDto.getTodayPrice2() == null ? 0 : todayResponseDto.getTodayPrice2();
              todayPriceSum += (todayPrice - todayPrice2);
            }
          }
          cashResponseDto.setTodayPrice(todayPriceSum);
        }
        Integer befYesterdayPrice = cashResponseDto.getBefYesterdayPrice() == null ? 0 : cashResponseDto.getBefYesterdayPrice();
        Integer befYesterdayPrice2 = cashResponseDto.getBefYesterdayPrice2() == null ? 0 : cashResponseDto.getBefYesterdayPrice2();
        Integer yesterdayPrice = cashResponseDto.getYesterdayPrice() == null ? 0 : cashResponseDto.getYesterdayPrice();
        Integer yesterdayPrice2 = cashResponseDto.getYesterdayPrice2() == null ? 0 : cashResponseDto.getYesterdayPrice2();
        cashResponseDto.setBefYesterdayPrice(befYesterdayPrice - befYesterdayPrice2);
        cashResponseDto.setYesterdayPrice(yesterdayPrice - yesterdayPrice2);
      }
    }
    return cashes;
  }

  @Override
  public List<CashResponseDto> getCashMaterialStatsList(SearchDto searchDto, Pageable pageable) {
    Long offset = pageable.getOffset();
    int pageSize = pageable.getPageSize();

    QCash subCash = new QCash("subCash");

    List<CashResponseDto> cashes = queryFactory
        .select(Projections.fields(CashResponseDto.class,
                Expressions.asNumber(1).as("statsOrd"),
                cash.storeCd, cash.materialCd.as("statsCd")
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(subCash.unitPrice.sum())
                        .from(subCash)
                        .where(
                            subCash.cashTypeCd.eq("RS01")
                                .and(subCash.storeCd.eq(cash.storeCd))
                                .and(subCash.materialCd.eq(cash.materialCd))
                                .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getBefYesterday())))
                                .and(subCash.delYn.eq("N"))
                        )
                    , "befYesterdayPrice"
                )
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(subCash.unitPrice.sum())
                        .from(subCash)
                        .where(
                            subCash.cashTypeCd.eq("RS02")
                                .and(subCash.storeCd.eq(cash.storeCd))
                                .and(subCash.materialCd.eq(cash.materialCd))
                                .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getBefYesterday())))
                                .and(subCash.delYn.eq("N"))
                        )
                    , "befYesterdayPrice2"
                )
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(subCash.unitPrice.sum())
                        .from(subCash)
                        .where(
                            subCash.cashTypeCd.eq("RS01")
                                .and(subCash.storeCd.eq(cash.storeCd))
                                .and(subCash.materialCd.eq(cash.materialCd))
                                .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getYesterday())))
                                .and(subCash.delYn.eq("N"))
                        )
                    , "yesterdayPrice"
                )
                , ExpressionUtils.as(
                    JPAExpressions
                        .select(subCash.unitPrice.sum())
                        .from(subCash)
                        .where(
                            subCash.cashTypeCd.eq("RS02")
                                .and(subCash.storeCd.eq(cash.storeCd))
                                .and(subCash.materialCd.eq(cash.materialCd))
                                .and(subCash.regDt.eq(Utils.convertLocalDateTime(searchDto.getYesterday())))
                                .and(subCash.delYn.eq("N"))
                        )
                    , "yesterdayPrice2"
                )
            )
        )
        .from(cash)
        .where(
            cash.delYn.eq("N")
                .and(storeCdEq(searchDto.getSearchStore()))
                .and(cashTypeEq(searchDto.getSearchCashType()))
                .and(bankbookEq(searchDto.getSearchBankbook()))
                .and(cashType2Eq(searchDto.getSearchCashType2()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .groupBy(cash.storeCd, cash.materialCd)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<String> storeMaterials = new ArrayList<>();
    for(CashResponseDto cashResponseDto : cashes){
      String storeCd = ObjectUtils.isEmpty(cashResponseDto.getStoreCd()) ? "" : cashResponseDto.getStoreCd();
      String materialCd = ObjectUtils.isEmpty(cashResponseDto.getMaterialCd()) ? "" : cashResponseDto.getMaterialCd();
      storeMaterials.add(storeCd+materialCd);
    }

    List<CashResponseDto> todayCashes = queryFactory
        .select(new QCashResponseDto(
                cash.storeCd, cash.materialCd
                , cash.cashTypeCd.when("RS01").then(cash.unitPrice).otherwise(0).as("todayPrice")
                , cash.cashTypeCd.when("RS02").then(cash.unitPrice).otherwise(0).as("todayPrice2")
            )
        )
        .from(cash)
        .where(
            cash.cashTypeCd.in("RS01","RS02")
                .and(cash.storeCd.concat(cash.materialCd).in(storeMaterials))
                .and(cash.regDt.eq(Utils.convertLocalDateTime(searchDto.getToday())))
                .and(cash.delYn.eq("N"))
                .and(storeCdEq(searchDto.getSearchStore()))
                .and(cashTypeEq(searchDto.getSearchCashType()))
                .and(bankbookEq(searchDto.getSearchBankbook()))
                .and(cashType2Eq(searchDto.getSearchCashType2()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(cash.regDt.desc(), cash.cashNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    if(!ObjectUtils.isEmpty(cashes)){
      for(CashResponseDto cashResponseDto : cashes){
        if(!ObjectUtils.isEmpty(todayCashes)){
          int todayPriceSum = cashResponseDto.getTodayPrice() == null ? 0 : cashResponseDto.getTodayPrice();
          for(CashResponseDto todayResponseDto : cashes) {
            if(ObjectUtils.nullSafeEquals(cashResponseDto.getStoreCd(), todayResponseDto.getStoreCd())
                && ObjectUtils.nullSafeEquals(cashResponseDto.getMaterialCd(), todayResponseDto.getMaterialCd())) {
              Integer todayPrice = todayResponseDto.getTodayPrice() == null ? 0 : todayResponseDto.getTodayPrice();
              Integer todayPrice2 = todayResponseDto.getTodayPrice2() == null ? 0 : todayResponseDto.getTodayPrice2();
              todayPriceSum += (todayPrice - todayPrice2);
            }
          }
          cashResponseDto.setTodayPrice(todayPriceSum);
        }

        Integer befYesterdayPrice = cashResponseDto.getBefYesterdayPrice() == null ? 0 : cashResponseDto.getBefYesterdayPrice();
        Integer befYesterdayPrice2 = cashResponseDto.getBefYesterdayPrice2() == null ? 0 : cashResponseDto.getBefYesterdayPrice2();
        Integer yesterdayPrice = cashResponseDto.getYesterdayPrice() == null ? 0 : cashResponseDto.getYesterdayPrice();
        Integer yesterdayPrice2 = cashResponseDto.getYesterdayPrice2() == null ? 0 : cashResponseDto.getYesterdayPrice2();
        Integer todayPrice = cashResponseDto.getTodayPrice() == null ? 0 : cashResponseDto.getTodayPrice();
        Integer todayPrice2 = cashResponseDto.getTodayPrice2() == null ? 0 : cashResponseDto.getTodayPrice2();
        cashResponseDto.setBefYesterdayPrice(befYesterdayPrice - befYesterdayPrice2);
        cashResponseDto.setYesterdayPrice(yesterdayPrice - yesterdayPrice2);
        cashResponseDto.setTodayPrice(todayPrice - todayPrice2);
      }
    }
    return cashes;
  }

  @Override
  public List<CashResponseDto> getTodayMaterialCashStatsList(SearchDto searchDto, Pageable pageable) {
    return null;
  }

  @Override
  public CashResponseDto getCash(Long cashNo) {
    return queryFactory
        .select(new QCashResponseDto(
            cash.cashNo, cash.regDt, cash.storeCd
            , cash.cashTypeCd, cash.cashTypeCd2, cash.bankbookCd
            , cash.venderNo, cash.venderNm, cash.historyDesc
            , cash.materialCd, cash.weightGram, cash.quantity
            , cash.unitPrice)
        )
        .from(cash)
        .where(
            cash.cashNo.eq(cashNo)
        )
        .fetchOne();
  }

  @Override
  public long updateCash(CashDto cashDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(cash)
        .set(cash.updtDt, LocalDateTime.now())
        .set(cash.updtId, cashDto.getUpdtId());
    if(!ObjectUtils.isEmpty(cashDto.getRegDt()))
      updateClause.set(cash.regDt, Utils.convertLocalDateTime(cashDto.getRegDt()));
    if(!ObjectUtils.isEmpty(cashDto.getStoreCd()))
      updateClause.set(cash.storeCd, cashDto.getStoreCd());
    if(!ObjectUtils.isEmpty(cashDto.getCashTypeCd()))
      updateClause.set(cash.cashTypeCd, cashDto.getCashTypeCd());
    if(!ObjectUtils.isEmpty(cashDto.getCashTypeCd2()))
      updateClause.set(cash.cashTypeCd2, cashDto.getCashTypeCd2());
    if(!ObjectUtils.isEmpty(cashDto.getBankbookCd()))
      updateClause.set(cash.bankbookCd, cashDto.getBankbookCd());
    if(!ObjectUtils.isEmpty(cashDto.getVenderNo()))
      updateClause.set(cash.venderNo, cashDto.getVenderNo());
    updateClause
        .set(cash.venderNm, cashDto.getVenderNm())
        .set(cash.historyDesc, cashDto.getHistoryDesc())
        .set(cash.materialCd, cashDto.getMaterialCd())
        .set(cash.weightGram, cashDto.getWeightGram())
        .set(cash.unitPrice, cashDto.getUnitPrice())
        .where(cash.cashNo.eq(cashDto.getCashNo()));
    return updateClause.execute();
  }

  @Override
  public long updateCashToDelete(CashDto cashDto) {
    return queryFactory
        .update(cash)
        .set(cash.updtDt, LocalDateTime.now())
        .set(cash.updtId, cashDto.getUpdtId())
        .set(cash.delYn, "Y")
        .where(cash.cashNo.eq(cashDto.getCashNo()))
        .execute();
  }

  @Override
  public long updateCashesToDelete(CashDto cashDto) {
    return queryFactory
        .update(cash)
        .set(cash.updtDt, LocalDateTime.now())
        .set(cash.updtId, cashDto.getUpdtId())
        .set(cash.delYn, "Y")
        .where(cash.cashNo.in(cashDto.getCashNoArr()))
        .execute();
  }
}
