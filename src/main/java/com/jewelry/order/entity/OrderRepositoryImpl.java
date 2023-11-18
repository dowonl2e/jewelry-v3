package com.jewelry.order.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.dto.OrderResponseDto;
import com.jewelry.order.dto.QOrderResponseDto;
import com.jewelry.util.Utils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.jewelry.customer.entity.QCustomer.customer;
import static com.jewelry.order.entity.QOrder.order;
import static com.jewelry.file.entity.QFile.file;
import static com.jewelry.repair.entity.QRepair.repair;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<OrderResponseDto> getSearchOrders(final SearchDto searchDto, final Pageable pageable) {
    List<OrderResponseDto> content = getOrders(searchDto, pageable);
    JPAQuery<Long> countQuery = getOrderCount(searchDto);
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
  private List<OrderResponseDto> getOrders(final SearchDto searchDto, final Pageable pageable) {
    return queryFactory
        .select(new QOrderResponseDto(
            order.orderNo, order.orderType, order.receiptDt
            , order.expectedOrdDt, order.storeCd, order.customerNo
            , order.customerNm, order.orderStep, order.catalogNo
            , order.modelId, order.venderNo, order.venderNm
            , order.materialCd, order.size, order.colorCd
            , order.quantity, order.mainStoneType, order.subStoneType
            , order.orderDesc
            , file.filePath, file.fileNm)
        )
        .from(order)
        .leftJoin(file)
        .on(
            order.orderNo.eq(file.refNo)
                .and(file.refInfo.eq("ORDER"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(storeEq(searchDto.getSearchStore()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(receiptDtGoe(searchDto.getSearchStdt()))
                .and(receiptDtLoe(searchDto.getSearchEddt()))
                .and(orderStepEq(searchDto.getSearchStep()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(order.orderNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getOrderCount(final SearchDto searchDto){
    return queryFactory
        .select(order.orderNo.count())
        .from(order)
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(storeEq(searchDto.getSearchStore()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(orderStepEq(searchDto.getSearchStep()))
                .and(receiptDtGoe(searchDto.getSearchStdt()))
                .and(receiptDtLoe(searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? order.delYn.eq(delYn) : null;
  }
  private BooleanExpression storeEq(String storeCd){
    return storeCd != null ? order.storeCd.eq(storeCd) : null;
  }
  private BooleanExpression materialEq(String materialCd){
    return materialCd != null ? order.materialCd.eq(materialCd) : null;
  }
  private BooleanExpression orderStepEq(String orderStep){
    return orderStep != null ? order.orderStep.eq(orderStep) : null;
  }
  private BooleanExpression receiptDtGoe(String searchStdt){
    return searchStdt != null ? order.receiptDt.goe(Utils.convertLocalDateTime(searchStdt)) : null;
  }
  private BooleanExpression receiptDtLoe(String searchEddt){
    return searchEddt != null ? order.receiptDt.loe(Utils.convertLocalDateTime(searchEddt)) : null;
  }
  private BooleanExpression customerNmLike(String word){
    return word != null ? order.customerNm.like("%"+word+"%") : null;
  }
  private BooleanExpression contractorNmLike(String word){
    return word != null ? customer.contractorNm.like("%"+word+"%") : null;
  }
  private BooleanExpression modelIdLike(String word){
    return word != null ? order.modelId.like("%"+word+"%") : null;
  }
  private BooleanExpression venderNmLike(String word){
    return word != null ? order.venderNm.like("%"+word+"%") : null;
  }
  private BooleanExpression wordLike(String word){
    BooleanExpression expression = customerNmLike(word);
    expression = expression == null ? modelIdLike(word) : expression.or(modelIdLike(word));
    return expression == null ? venderNmLike(word) : expression.or(venderNmLike(word));
  }
  private BooleanExpression wordWithCustomerLike(String word){
    BooleanExpression expression = contractorNmLike(word);
    expression = expression == null ? modelIdLike(word) : expression.or(modelIdLike(word));
    return expression == null ? venderNmLike(word) : expression.or(venderNmLike(word));
  }

  @Override
  public OrderResponseDto getOrder(final Long orderNo){
    return queryFactory
        .select(new QOrderResponseDto(
            order.orderNo, order.orderType, order.receiptDt
            , order.expectedOrdDt, order.storeCd, order.customerNo
            , order.customerNm, order.customerCel, order.orderStep
            , order.serialId, order.catalogNo, order.modelId
            , order.venderNo, order.venderNm, order.materialCd
            , order.size, order.colorCd, order.quantity
            , order.mainStoneType, order.subStoneType, order.orderDesc
            , file.filePath, file.fileNm)
        )
        .from(order)
        .leftJoin(file)
        .on(
            order.orderNo.eq(file.refNo)
                .and(file.refInfo.eq("ORDER"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            order.orderNo.eq(orderNo)
                .and(order.delYn.eq("N"))
        )
        .fetchOne();
  }

  @Override
  public long updateOrder(final OrderDto orderDto) {
    return queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId())
        .set(order.storeCd, orderDto.getStoreCd())
        .set(order.receiptDt, Utils.convertLocalDateTime(orderDto.getReceiptDt()))
        .set(order.expectedOrdDt, Utils.convertLocalDateTime(orderDto.getExpectedOrdDt()))
        .set(order.customerNo, orderDto.getCustomerNo())
        .set(order.customerNm, orderDto.getCustomerNm())
        .set(order.customerCel, orderDto.getCustomerCel())
        .set(order.serialId, orderDto.getSerialId())
        .set(order.catalogNo, orderDto.getCatalogNo())
        .set(order.modelId, orderDto.getModelId())
        .set(order.venderNo, orderDto.getVenderNo())
        .set(order.venderNm, orderDto.getVenderNm())
        .set(order.materialCd, orderDto.getMaterialCd())
        .set(order.colorCd, orderDto.getColorCd())
        .set(order.quantity, orderDto.getQuantity())
        .set(order.mainStoneType, orderDto.getMainStoneType())
        .set(order.subStoneType, orderDto.getSubStoneType())
        .set(order.size, orderDto.getSize())
        .set(order.orderDesc, orderDto.getOrderDesc())
        .where(order.orderNo.eq(orderDto.getOrderNo()))
        .execute();
  }

  @Override
  public long updateOrdersStep(final OrderDto orderDto) {
    return queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId())
        .set(order.orderStep, orderDto.getOrderStep())
        .where(order.orderNo.in(orderDto.getOrderNoArr()))
        .execute();
  }

  @Override
  public long updateOrdersToDelete(final OrderDto orderDto) {
    return queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId())
        .set(order.delYn, "Y")
        .where(order.orderNo.in(orderDto.getOrderNoArr()))
        .execute();
  }

  @Override
  public long updateOrdersCustomer(final OrderDto orderDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId());

    if(orderDto.getCustomerNo() != null && orderDto.getCustomerNo() > 0) {
      updateClause.set(order.customerNo, orderDto.getCustomerNo())
          .set(order.customerNm, orderDto.getCustomerNm())
          .set(order.customerCel, orderDto.getCustomerCel());
    }

    updateClause
        .where(order.orderNo.in(orderDto.getOrderNoArr()));

    return updateClause.execute();
  }

  @Override
  public long updateOrdersVender(final OrderDto orderDto) {
    JPAUpdateClause updateClause = queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId());

    if(orderDto.getVenderNo() != null && orderDto.getVenderNo() > 0){
      updateClause
          .set(order.venderNo, orderDto.getVenderNo())
          .set(order.venderNm, orderDto.getVenderNm());
    }

    updateClause
        .where(order.orderNo.in(orderDto.getOrderNoArr()));

    return updateClause.execute();
  }

  @Override
  public long updateOrdersSaleDate(final OrderDto orderDto) {
    return queryFactory
        .update(order)
        .set(order.updtDt, LocalDateTime.now())
        .set(order.updtId, orderDto.getUpdtId())
        .set(order.receiptDt, Utils.convertLocalDateTime(orderDto.getReceiptDt()))
        .where(order.orderNo.in(orderDto.getOrderNoArr()))
        .execute();
  }

  @Override
  public List<OrderResponseDto> getOrdersByNos(final OrderDto orderDto) {
    return queryFactory
        .select(new QOrderResponseDto(
            order.orderNo, order.catalogNo, order.modelId
            , order.venderNo, order.venderNm, order.materialCd
            , order.colorCd, order.mainStoneType, order.subStoneType
            , order.size, order.quantity, order.orderDesc
            , order.customerNo, order.customerNm, order.customerCel)
        )
        .from(order)
        .leftJoin(file)
        .where(
            order.orderNo.in(orderDto.getOrderNoArr())
        )
        .orderBy(order.orderNo.desc())
        .fetch();
  }

  @Override
  public Page<OrderResponseDto> getSearchCustomerOrders(SearchDto searchDto, Pageable pageable) {
    List<OrderResponseDto> content = getCustomerOrders(searchDto, pageable);
    JPAQuery<Long> countQuery = getCustomerOrderCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }


  private List<OrderResponseDto> getCustomerOrders(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QOrderResponseDto(
            order.orderNo, order.orderType, order.storeCd
            , order.receiptDt, order.expectedOrdDt, order.customerNo
            , customer.contractorNm, customer.contractorCel, order.orderStep
            , order.catalogNo, order.modelId, order.venderNo
            , order.venderNm, order.serialId, order.materialCd
            , order.colorCd, order.quantity, order.mainStoneType
            , order.subStoneType, order.size, order.orderDesc)
        )
        .from(order)
        .leftJoin(order.customer, customer)
        .where(
            order.customerNo.eq(searchDto.getCustomerNo())
                .and(orderStepEq(searchDto.getOrderStep()))
                .and(delYnEq(searchDto.getSearchDelYn()))
                .and(orderStepEq(searchDto.getSearchStep()))
                .and(storeEq(searchDto.getSearchStore()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(wordWithCustomerLike(searchDto.getSearchWord()))
        )
        .orderBy(order.orderNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getCustomerOrderCount(final SearchDto searchDto){
    return queryFactory
        .select(order.orderNo.count())
        .from(order)
        .leftJoin(order.customer, customer)
        .where(
            order.customerNo.eq(searchDto.getCustomerNo())
                .and(orderStepEq(searchDto.getOrderStep()))
                .and(delYnEq(searchDto.getSearchDelYn()))
                .and(orderStepEq(searchDto.getSearchStep()))
                .and(storeEq(searchDto.getSearchStore()))
                .and(materialEq(searchDto.getSearchMaterial()))
                .and(wordWithCustomerLike(searchDto.getSearchWord()))
        );
  }

  @Override
  public List<OrderResponseDto> getNumOfOrdersPerMaterial(SearchDto searchDto) {
    return queryFactory
        .select(new QOrderResponseDto(
            order.materialCd, order.orderNo.count())
        )
        .from(order)
        .where(
            order.delYn.eq("N")
                .and(receiptYearEq(searchDto.getSearchYear()))
        )
        .groupBy(order.materialCd)
        .orderBy(order.materialCd.asc())
        .fetch();
  }
  private BooleanExpression receiptYearEq(String searchYear){
    return searchYear != null ? order.receiptDt.year().eq(Integer.parseInt(searchYear)) : null;
  }
}
