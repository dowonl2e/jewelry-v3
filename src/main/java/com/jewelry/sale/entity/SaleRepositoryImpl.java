package com.jewelry.sale.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.sale.dto.QSaleResponseDto;
import com.jewelry.sale.dto.SaleDto;
import com.jewelry.sale.dto.SaleResponseDto;
import com.jewelry.util.Utils;
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
import java.util.List;

import static com.jewelry.sale.entity.QSale.sale;
import static com.jewelry.stock.entity.QStock.stock;
import static com.jewelry.vender.entity.QVenderPay.venderPay;

@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<SaleResponseDto> getSearchSales(SearchDto searchDto, Pageable pageable) {
    List<SaleResponseDto> content = getSales(searchDto, pageable);
    JPAQuery<Long> countQuery = getSaleCount(searchDto);
    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<SaleResponseDto> getSales(SearchDto searchDto, Pageable pageable) {
    return queryFactory
        .select(new QSaleResponseDto(
            sale.saleNo, sale.storeCd, sale.saleDt
            , sale.saleType2, sale.saleDay, sale.saleType
            , sale.customerNo, sale.customerNm, sale.catalogNo
            , sale.modelId, sale.realPchGoldPrice, sale.materialCd
            , sale.perWeightGram, sale.saleDesc, sale.mainStoneType
            , sale.subStoneType, sale.quantity, sale.purchasePrice
            , sale.consumerPrice, sale.salePrice, sale.recPayTypeCd
            , sale.cardPrice, sale.cashPrice, sale.maintPrice
            , sale.pntPrice, sale.etcPrice, sale.accuPnt
        ))
        .from(sale)
        .where(
            expressionTrue()
                .and(storeEq(searchDto.getSearchStore()))
                .and(saleTypeEq(searchDto.getSearchSaleType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(saleDtGoe(searchDto.getSearchStdt()))
                .and(saleDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(sale.saleDt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getSaleCount(SearchDto searchDto){
    return queryFactory
        .select(sale.saleNo.count())
        .from(sale)
        .where(
            expressionTrue()
                .and(storeEq(searchDto.getSearchStore()))
                .and(saleTypeEq(searchDto.getSearchSaleType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(saleDtGoe(searchDto.getSearchStdt()))
                .and(saleDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression expressionTrue(){
    return Expressions.asNumber(1).eq(1);
  }
  private BooleanExpression storeEq(String storeCd){
    return storeCd != null ? sale.storeCd.eq(storeCd) : null;
  }
  private BooleanExpression saleTypeEq(String stockTypeCd){
    return stockTypeCd != null ? sale.saleType.eq(stockTypeCd) : null;
  }
  private BooleanExpression materialEq(String materialCd){
    return materialCd != null ? sale.materialCd.eq(materialCd) : null;
  }
  private BooleanExpression saleDtGoe(String searchStdt){
    StringExpression formattter = Expressions.stringTemplate(
        "FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')",
        sale.saleDt
    );
    return searchStdt != null ? formattter.goe(searchStdt) : null;
  }
  private BooleanExpression saleDtLoe(String searchEddt){
    StringExpression formattter = Expressions.stringTemplate(
        "FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')",
        sale.saleDt
    );
    return searchEddt != null ?formattter.loe(searchEddt) : null;
  }
  private BooleanExpression customerNmLike(String word){
    return word != null ? sale.customerNm.like("%"+word+"%") : null;
  }
  private BooleanExpression modelIdLike(String word){
    return word != null ? sale.modelId.like("%"+word+"%") : null;
  }
  private BooleanExpression wordLike(String word){
    BooleanExpression expression = customerNmLike(word);
    return expression == null ? modelIdLike(word) : expression.or(modelIdLike(word));
  }
  @Override
  public List<SaleResponseDto> getMonthlySalePriceStats(SearchDto searchDto) {
    return queryFactory
        .select(new QSaleResponseDto(
            stock.saleDt.month(), stock.salePrice.sum())
        )
        .from(stock)
        .where(
            stock.saleYn.eq("Y")
                .and(stock.delYn.eq("N"))
                .and(yearEq(searchDto.getSearchYear()))
        )
        .groupBy(stock.saleDt.month())
        .orderBy(stock.saleDt.month().asc())
        .fetch();
  }
  private BooleanExpression yearEq(String searchYear){
    return searchYear != null ? stock.saleDt.year().eq(Integer.parseInt(searchYear)) : null;
  }

  @Override
  public long updateSalesToStock(SaleDto saleDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.saleYn, "N")
        .set(stock.saleDt, LocalDateTime.now())
        .setNull(stock.salePrice)
        .setNull(stock.recPayTypeCd)
        .setNull(stock.cardPrice)
        .setNull(stock.cashPrice)
        .setNull(stock.maintPrice)
        .setNull(stock.etcPrice)
        .setNull(stock.pntPrice)
        .setNull(stock.accuPnt)
        .setNull(stock.customerNo)
        .setNull(stock.customerNm)
        .where(stock.stockNo.in(saleDto.getSaleNoArr()))
        .execute();
  }
}
