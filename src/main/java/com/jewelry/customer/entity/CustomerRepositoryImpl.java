package com.jewelry.customer.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.customer.dto.CustomerDto;
import com.jewelry.customer.dto.CustomerResponseDto;
import com.jewelry.customer.dto.QCustomerResponseDto;
import com.jewelry.util.Utils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.jewelry.customer.entity.QCustomer.customer;
import static com.jewelry.order.entity.QOrderCustomerCnt.orderCustomerCnt;
import static com.jewelry.repair.entity.QRepairCustomerCnt.repairCustomerCnt;
import static com.jewelry.sale.entity.QSaleCustomerCnt.saleCustomerCnt;


@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<CustomerResponseDto> getSearchCustomers(final SearchDto searchDto, final Pageable pageable) {
    List<CustomerResponseDto> content = getCustomers(searchDto, pageable);
    JPAQuery<Long> countQuery = getCustomerCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<CustomerResponseDto> getCustomers(final SearchDto searchDto, final Pageable pageable) {
    return queryFactory
        .select(new QCustomerResponseDto(
            customer.customerNo, customer.contractCd, customer.etc
            , customer.contractorNm, customer.contractorCel, customer.regDt
            , orderCustomerCnt.orderCnt.coalesce(0).as("orderCnt"), repairCustomerCnt.repairCnt.coalesce(0).as("repairCnt"), orderCustomerCnt.reserveCnt.coalesce(0).as("reserveCnt")
            , saleCustomerCnt.saleCnt.coalesce(0).as("saleCnt"), saleCustomerCnt.salePrice.coalesce(0).as("salePrice"))
        )
        .from(customer)
        .leftJoin(orderCustomerCnt)
        .on(customer.customerNo.eq(orderCustomerCnt.customerNo))
        .leftJoin(repairCustomerCnt)
        .on(customer.customerNo.eq(repairCustomerCnt.customerNo))
        .leftJoin(saleCustomerCnt)
        .on(customer.customerNo.eq(saleCustomerCnt.customerNo))
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(regDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(customer.regDt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getCustomerCount(final SearchDto searchDto){
    return queryFactory
        .select(customer.count())
        .from(customer)
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(regDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? customer.delYn.eq(delYn) : null;
  }
  private BooleanExpression regDtGoe(String searchStdt){
    return searchStdt != null ? customer.regDt.goe(Utils.convertLocalDateTime(searchStdt)) : null;
  }
  private BooleanExpression regDtLoe(String searchEddt){
    return searchEddt != null ? customer.regDt.loe(Utils.convertLocalDateTime(searchEddt)) : null;
  }
  private BooleanExpression regDtGoeLoe(String searchStdt, String searchEddt){
    BooleanExpression expression = regDtGoe(searchStdt);
    return expression == null ? regDtLoe(searchEddt) : expression.and(regDtLoe(searchEddt));
  }

  private BooleanExpression contractorNmLike(String word){
    return word != null ? customer.contractorNm.like("%"+word+"%") : null;
  }
  private BooleanExpression contractorCelLike(String word){
    return word != null ? customer.contractorCel.like("%"+word+"%") : null;
  }

  private BooleanExpression wordLike(String word){
    BooleanExpression expression = contractorNmLike(word);
    return expression == null ? contractorCelLike(word) : expression.or(contractorCelLike(word));
  }

  @Override
  public CustomerResponseDto getCustomer(final Long customerNo) {
    return queryFactory
        .select(new QCustomerResponseDto(
            customer.customerNo, customer.storeCd, customer.contractCd
            , customer.zipcode, customer.address1, customer.address2
            , customer.etc, customer.contractorNm, customer.contractorGen
            , customer.contractorCel, customer.contractorBirth, customer.contractorLunar
            , customer.contractorEmail, customer.regDt, customer.inptId)
        )
        .from(customer)
        .where(customer.customerNo.eq(customerNo))
        .fetchOne();
  }

  @Override
  public long updateCustomer(final CustomerDto customerDto) {
    return queryFactory
        .update(customer)
        .set(customer.updtDt, LocalDateTime.now())
        .set(customer.storeCd, customerDto.getStoreCd())
        .set(customer.contractCd, customerDto.getContractCd())
        .set(customer.zipcode, customerDto.getZipcode())
        .set(customer.address1, customerDto.getAddress1())
        .set(customer.address2, customerDto.getAddress2())
        .set(customer.etc, customerDto.getEtc())
        .set(customer.contractorNm, customerDto.getContractorNm())
        .set(customer.contractorCel, customerDto.getContractorCel())
        .set(customer.contractorGen, customerDto.getContractorGen())
        .set(customer.contractorBirth, customerDto.getContractorBirth())
        .set(customer.contractorLunar, customerDto.getContractorLunar())
        .set(customer.contractorEmail, customerDto.getContractorEmail())
        .where(customer.customerNo.eq(customerDto.getCustomerNo()))
        .execute();
  }

  @Override
  public long updateToDelete(final CustomerDto customerDto) {
    return queryFactory
        .update(customer)
        .set(customer.updtDt, LocalDateTime.now())
        .set(customer.updtId, customerDto.getUpdtId())
        .set(customer.delYn, "Y")
        .where(customer.customerNo.eq(customerDto.getCustomerNo()))
        .execute();
  }

  @Override
  public long updateCustomersToDelete(final CustomerDto customerDto) {
    return queryFactory
        .update(customer)
        .set(customer.updtDt, LocalDateTime.now())
        .set(customer.updtId, customerDto.getUpdtId())
        .set(customer.delYn, "Y")
        .where(customer.customerNo.in(customerDto.getCustomerNoArr()))
        .execute();
  }
}
