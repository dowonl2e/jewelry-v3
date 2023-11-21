package com.jewelry.stock.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.stock.dto.QStockResponseDto;
import com.jewelry.stock.dto.StockDto;
import com.jewelry.stock.dto.StockResponseDto;
import com.jewelry.util.Utils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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

import static com.jewelry.catalog.entity.QCatalog.catalog;
import static com.jewelry.file.entity.QFile.file;
import static com.jewelry.stock.entity.QStock.stock;

@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<StockResponseDto> getSearchStocks(SearchDto searchDto, Pageable pageable) {
    List<StockResponseDto> content = getStocks(searchDto, pageable);
    JPAQuery<Long> countQuery = getStockCount(searchDto);
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

  private List<StockResponseDto> getStocks(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.storeCd, stock.regDt
            , stock.stockTypeCd, stock.size, stock.stockDesc
            , stock.catalogNo, catalog.modelId.coalesce(stock.modelId), stock.materialCd
            , stock.colorCd, stock.mainStoneType, stock.subStoneType
            , stock.quantity, stock.perWeightGram, stock.realPchGoldPrice
            , stock.perPriceBasic, stock.perPriceAdd, stock.perPriceMain
            , stock.perPriceSub, stock.multipleCnt, stock.customerNo
            , stock.customerNm)
        )
        .from(stock)
        .leftJoin(stock.catalog, catalog)
        .on(
            catalog.delYn.eq("N")
        )
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(storeEq(searchDto.getSearchStore()))
                .and(stockTypeEq(searchDto.getSearchStockType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(stock.regDt.desc(), stock.stockNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }
  private JPAQuery<Long> getStockCount(final SearchDto searchDto){
    return queryFactory
        .select(stock.stockNo.count())
        .from(stock)
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(storeEq(searchDto.getSearchStore()))
                .and(stockTypeEq(searchDto.getSearchStockType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  @Override
  public List<StockResponseDto> getPrevStocks(){
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.storeCd, stock.regDt
            , stock.stockTypeCd, stock.catalogNo, stock.modelId
            , stock.venderNo, stock.venderNm, stock.size
            , stock.stockDesc, stock.materialCd
            , stock.colorCd, stock.mainStoneType, stock.subStoneType
            , stock.quantity, stock.perWeightGram, stock.realPchGoldPrice
            , stock.perPriceMain, stock.perPriceSub, stock.perPriceBasic
            , stock.perPriceAdd, stock.multipleCnt)
        )
        .from(stock)
        .where(
            stock.delYn.eq("N")
        )
        .orderBy(stock.regDt.desc(), stock.stockNo.desc())
        .offset(0)
        .limit(3)
        .fetch();
  }

  @Override
  public StockResponseDto getStock(Long stockNo) {
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.storeCd, stock.regDt
            , stock.stockTypeCd, stock.catalogNo, stock.modelId
            , stock.venderNo, stock.venderNm, stock.size
            , stock.stockDesc, stock.materialCd
            , stock.colorCd, stock.mainStoneType, stock.subStoneType
            , stock.quantity, stock.perWeightGram, stock.realPchGoldPrice
            , stock.perPriceMain, stock.perPriceSub, stock.perPriceBasic
            , stock.perPriceAdd, stock.multipleCnt
            , file.filePath, file.fileNm)
        )
        .from(stock)
        .leftJoin(file)
        .on(
            stock.stockNo.eq(file.refNo)
                .and(file.refInfo.eq("STOCK"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            stock.stockNo.eq(stockNo)
                .and(stock.delYn.eq("N"))
        ).fetchOne();
  }

  @Override
  public StockResponseDto getStockCustomer(Long stockNo) {
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.stockTypeCd, stock.customerNo, stock.customerNm)
        )
        .from(stock)
        .where(
            stock.stockNo.eq(stockNo)
        )
        .fetchOne();
  }

  @Override
  public long updateStock(StockDto stockDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId());
    if(!ObjectUtils.isEmpty(stockDto.getRegDt()))
      updateClause.set(stock.regDt, Utils.convertLocalDateTime(stockDto.getRegDt()));
    if(!ObjectUtils.isEmpty(stockDto.getStockTypeCd()))
      updateClause.set(stock.stockTypeCd, stockDto.getStockTypeCd());
    if(!ObjectUtils.isEmpty(stockDto.getStoreCd()))
      updateClause.set(stock.storeCd, stockDto.getStoreCd());
    updateClause.set(stock.realPchGoldPrice, stockDto.getRealPchGoldPrice());
    if(!ObjectUtils.isEmpty(stockDto.getCatalogNo()))
      updateClause.set(stock.catalogNo, stockDto.getCatalogNo());
    if(!ObjectUtils.isEmpty(stockDto.getModelId()))
      updateClause.set(stock.modelId, stockDto.getModelId());
    updateClause
        .set(stock.venderNo, stockDto.getVenderNo())
        .set(stock.venderNm, stockDto.getVenderNm())
        .set(stock.materialCd, stockDto.getMaterialCd())
        .set(stock.colorCd, stockDto.getColorCd())
        .set(stock.mainStoneType, stockDto.getMainStoneType())
        .set(stock.subStoneType, stockDto.getSubStoneType())
        .set(stock.size, stockDto.getSize())
        .set(stock.stockDesc, stockDto.getStockDesc());
    if(!ObjectUtils.isEmpty(stockDto.getQuantity()))
      updateClause.set(stock.quantity, stockDto.getQuantity());
    updateClause
        .set(stock.perWeightGram, stockDto.getPerWeightGram())
        .set(stock.perPriceBasic, stockDto.getPerPriceBasic())
        .set(stock.perPriceAdd, stockDto.getPerPriceAdd())
        .set(stock.perPriceMain, stockDto.getPerPriceMain())
        .set(stock.perPriceSub, stockDto.getPerPriceSub())
        .set(stock.multipleCnt, stockDto.getMultipleCnt())
        .where(stock.stockNo.eq(stockDto.getStockNo()));
    return updateClause.execute();
  }

  @Override
  public long updateStockToDelete(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.delYn, "Y")
        .where(stock.stockNo.eq(stockDto.getStockNo()))
        .execute();
  }

  @Override
  public long updateStocksToDelete(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.delYn, "Y")
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }

  @Override
  public long updateStocksToSale(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.saleYn, "Y")
        .set(stock.salePrice, stockDto.getSalePrice())
        .set(stock.saleDt, LocalDateTime.now())
        .set(stock.recPayTypeCd, stockDto.getRecPayTypeCd())
        .set(stock.cardPrice, stockDto.getCardPrice())
        .set(stock.cashPrice, stockDto.getCashPrice())
        .set(stock.maintPrice, stockDto.getMaintPrice())
        .set(stock.etcPrice, stockDto.getEtcPrice())
        .set(stock.pntPrice, stockDto.getPntPrice())
        .set(stock.accuPnt, stockDto.getAccuPnt())
        .set(stock.customerNo, stockDto.getCustomerNo())
        .set(stock.customerNm, stockDto.getCustomerNm())
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }

  @Override
  public long updateStocksRegDt(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.regDt, Utils.convertLocalDateTime(stockDto.getRegDt()))
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }

  @Override
  public long updateStocksType(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.stockTypeCd, stockDto.getStockTypeCd())
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }

  @Override
  public long updateStocksVender(StockDto stockDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId());
    if(stockDto.getVenderNo() != null && stockDto.getVenderNo() > 0)
      updateClause
          .set(stock.venderNo, stockDto.getVenderNo())
          .set(stock.venderNm, stockDto.getVenderNm());
    updateClause
        .where(stock.stockNo.in(stockDto.getStockNoArr()));
    return updateClause.execute();
  }

  @Override
  public long updateStocksOrder(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.orderYn, "Y")
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }


  @Override
  public long updateStocksCustomer(StockDto stockDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId());
    if(stockDto.getCustomerNo() != null && stockDto.getCustomerNo() > 0)
      updateClause
          .set(stock.customerNo, stockDto.getCustomerNo())
          .set(stock.customerNm, stockDto.getCustomerNm());
    updateClause
        .where(stock.stockNo.in(stockDto.getStockNoArr()));
    return updateClause.execute();
  }

  @Override
  public long updateStocksSaleDate(StockDto stockDto) {
    return queryFactory
        .update(stock)
        .set(stock.updtDt, LocalDateTime.now())
        .set(stock.updtId, stockDto.getUpdtId())
        .set(stock.saleDt, Utils.convertLocalDateTime(stockDto.getSaleDt()))
        .where(stock.stockNo.in(stockDto.getStockNoArr()))
        .execute();
  }
  @Override
  public Page<StockResponseDto> getSearchAccumulationStocks(SearchDto searchDto, Pageable pageable) {
    List<StockResponseDto> content = getAccumulationStocks(searchDto, pageable);
    JPAQuery<Long> countQuery = getAccumulationStockCount(searchDto);
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

  private List<StockResponseDto> getAccumulationStocks(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.storeCd, stock.regDt
            , stock.stockTypeCd, stock.size, stock.stockDesc
            , stock.catalogNo, stock.modelId, stock.materialCd
            , stock.colorCd, stock.mainStoneType, stock.subStoneType
            , stock.quantity, stock.perWeightGram, stock.realPchGoldPrice
            , stock.perPriceBasic, stock.perPriceAdd, stock.perPriceMain
            , stock.perPriceSub, stock.multipleCnt, stock.delYn
            , stock.saleYn, stock.updtDt.coalesce(stock.inptDt))
        )
        .from(stock)
        .where(
            expressionTrue()
                .and(storeEq(searchDto.getSearchStore()))
                .and(stockTypeEq(searchDto.getSearchStockType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(stock.regDt.desc(), stock.stockNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }
  private JPAQuery<Long> getAccumulationStockCount(final SearchDto searchDto){
    return queryFactory
        .select(stock.stockNo.count())
        .from(stock)
        .where(
            expressionTrue()
                .and(storeEq(searchDto.getSearchStore()))
                .and(stockTypeEq(searchDto.getSearchStockType()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(regDtGoe(searchDto.getSearchStdt()))
                .and(regDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  @Override
  public List<StockResponseDto> getStockListByNos(Long[] stockNoArr){
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.storeCd, stock.catalogNo
            , stock.modelId, stock.venderNo, stock.venderNm
            , stock.materialCd, stock.colorCd, stock.mainStoneType
            , stock.subStoneType, stock.size, stock.stockDesc)
        )
        .from(stock)
        .where(
            stock.stockNo.in(stockNoArr)
        )
        .orderBy(stock.stockNo.desc())
        .fetch();
  }

  @Override
  public List<StockResponseDto> getNumOfStocksPerMaterial(SearchDto searchDto){
    return queryFactory
        .select(new QStockResponseDto(
            stock.materialCd, stock.perWeightGram.sum(), stock.stockNo.count())
        )
        .from(stock)
        .where(
            stock.regDt.year().eq(Integer.parseInt(searchDto.getSearchYear()))
        )
        .groupBy(stock.materialCd)
        .orderBy(stock.materialCd.asc())
        .fetch();
  }

  @Override
  public List<StockResponseDto> getStockListByStockNos(Long[] stockNoArr){
    return queryFactory
        .select(new QStockResponseDto(
            stock.stockNo, stock.stockTypeCd, stock.customerNo, stock.customerNm)
        )
        .from(stock)
        .where(
            stock.stockNo.in(stockNoArr)
        )
        .fetch();
  }

  private BooleanExpression expressionTrue(){
    return Expressions.asNumber(1).eq(1);
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? stock.delYn.eq(delYn) : null;
  }
  private BooleanExpression storeEq(String storeCd){
    return storeCd != null ? stock.storeCd.eq(storeCd) : null;
  }
  private BooleanExpression stockTypeEq(String stockTypeCd){
    return stockTypeCd != null ? stock.stockTypeCd.eq(stockTypeCd) : null;
  }
  private BooleanExpression materialEq(String materialCd){
    return materialCd != null ? stock.materialCd.eq(materialCd) : null;
  }
  private BooleanExpression regDtGoe(String searchStdt){
    return searchStdt != null ? stock.regDt.goe(Utils.convertLocalDateTime(searchStdt)) : null;
  }
  private BooleanExpression regDtLoe(String searchEddt){
    return searchEddt != null ? stock.regDt.loe(Utils.convertLocalDateTime(searchEddt)) : null;
  }
  private BooleanExpression wordLike(String word){
    return ObjectUtils.isEmpty(word) ? null :
        Expressions.numberTemplate(
            Double.class,
            "function('match2',{0},{1},{2})",
            stock.customerNm,stock.modelId,word
        ).gt(0);
  }
}
