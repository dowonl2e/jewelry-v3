package com.jewelry.repair.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.repair.dto.QRepairResponseDto;
import com.jewelry.repair.dto.RepairDto;
import com.jewelry.repair.dto.RepairResponseDto;
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

import static com.jewelry.repair.entity.QRepair.repair;
import static com.jewelry.file.entity.QFile.file;

@RequiredArgsConstructor
public class RepairRepositoryImpl implements RepairRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<RepairResponseDto> getSearchRepairs(final SearchDto searchDto, final Pageable pageable){
    List<RepairResponseDto> content = getRepairs(searchDto, pageable);
    JPAQuery<Long> countQuery = getRepairCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<RepairResponseDto> getRepairs(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QRepairResponseDto(
            repair.repairNo, repair.repairNm, repair.repairReqDt
            , repair.repairDueDt, repair.repairResDt, repair.customerNo
            , repair.customerNm, file.filePath, file.originNm, file.fileNm)
        )
        .from(repair)
        .leftJoin(file)
        .on(
            repair.repairNo.eq(file.refNo)
                .and(file.refInfo.eq("REPAIR"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(repairReqDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(
            repair.repairReqDt.desc()
            , repair.repairNo.desc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }
  private JPAQuery<Long> getRepairCount(final SearchDto searchDto){
    return queryFactory
        .select(repair.count())
        .from(repair)
        .where(
            delYnEq(searchDto.getSearchDelYn())
                .and(repairReqDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? repair.delYn.eq(delYn) : null;
  }
  private BooleanExpression repairReqDtGoe(String searchStdt){
    return searchStdt != null ? repair.repairReqDt.goe(Utils.convertLocalDateTime(searchStdt)) : null;
  }
  private BooleanExpression repairReqDtLoe(String searchEddt){
    return searchEddt != null ? repair.repairReqDt.loe(Utils.convertLocalDateTime(searchEddt)) : null;
  }
  private BooleanExpression repairReqDtGoeLoe(String searchStdt, String searchEddt){
    BooleanExpression expression = repairReqDtGoe(searchStdt);
    return expression == null ? repairReqDtLoe(searchEddt) : expression.and(repairReqDtLoe(searchEddt));
  }

  private BooleanExpression repairNmLike(String word){
    return word != null ? repair.repairNm.like("%"+word+"%") : null;
  }
  private BooleanExpression customerNmLike(String word){
    return word != null ? repair.customerNm.like("%"+word+"%") : null;
  }

  private BooleanExpression wordLike(String word){
    BooleanExpression expression = repairNmLike(word);
    return expression == null ? customerNmLike(word) : expression.or(customerNmLike(word));
  }
  @Override
  public RepairResponseDto getRepair(final Long repairNo) {
    return queryFactory
        .select(new QRepairResponseDto(
            repair.repairNo, repair.repairNm, repair.repairReqDt
            , repair.repairDueDt, repair.repairResDt, repair.repairDesc
            , repair.customerNo, repair.customerNm, repair.customerCel
            , file.filePath, file.fileNm)
        )
        .from(repair)
        .leftJoin(file)
        .on(
            repair.repairNo.eq(file.refNo)
                .and(file.refInfo.eq("REPAIR"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(repair.repairNo.eq(repairNo))
        .fetchOne();
  }

  @Override
  public long updateRepair(final RepairDto repairDto) {
    return queryFactory
        .update(repair)
        .set(repair.updtDt, LocalDateTime.now())
        .set(repair.updtId, repairDto.getUpdtId())
        .set(repair.repairNm, repairDto.getRepairNm())
        .set(repair.repairReqDt, Utils.convertLocalDateTime(repairDto.getRepairReqDt()))
        .set(repair.repairDueDt, Utils.convertLocalDateTime(repairDto.getRepairDueDt()))
        .set(repair.repairResDt, Utils.convertLocalDateTime(repairDto.getRepairResDt()))
        .set(repair.repairDesc, repairDto.getRepairDesc())
        .set(repair.customerNo, repairDto.getCustomerNo())
        .set(repair.customerNm, repairDto.getCustomerNm())
        .set(repair.customerCel, repairDto.getCustomerCel())
        .where(repair.repairNo.eq(repairDto.getRepairNo()))
        .execute();
  }

  @Override
  public long updateToDelete(final RepairDto repairDto) {
    return queryFactory
        .update(repair)
        .set(repair.updtDt, LocalDateTime.now())
        .set(repair.updtId, repairDto.getUpdtId())
        .set(repair.delYn, "Y")
        .where(repair.repairNo.eq(repairDto.getRepairNo()))
        .execute();
  }

  @Override
  public long updateRepairsToDelete(final RepairDto repairDto) {
    return queryFactory
        .update(repair)
        .set(repair.updtDt, LocalDateTime.now())
        .set(repair.updtId, repairDto.getUpdtId())
        .set(repair.delYn, "Y")
        .where(repair.repairNo.in(repairDto.getRepairNoArr()))
        .execute();
  }

  @Override
  public long updateRepairsToComplete(RepairDto repairDto) {
    return queryFactory
        .update(repair)
        .set(repair.updtDt, LocalDateTime.now())
        .set(repair.updtId, repairDto.getUpdtId())
        .set(repair.repairResDt, LocalDateTime.now())
        .where(repair.repairNo.in(repairDto.getRepairNoArr()))
        .execute();
  }
  @Override
  public Page<RepairResponseDto> getSearchCustomerRepairs(final SearchDto searchDto, final Pageable pageable){
    List<RepairResponseDto> content = getCustomerRepairs(searchDto, pageable);
    JPAQuery<Long> countQuery = getCustomerRepairCount(searchDto);

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private List<RepairResponseDto> getCustomerRepairs(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QRepairResponseDto(
            repair.repairNo, repair.repairNm, repair.repairReqDt
            , repair.repairDueDt, repair.repairResDt, repair.customerNo
            , repair.customerNm, file.filePath, file.originNm, file.fileNm)
        )
        .from(repair)
        .leftJoin(file)
        .on(
            repair.repairNo.eq(file.refNo)
                .and(file.refInfo.eq("REPAIR"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            repair.customerNo.eq(searchDto.getCustomerNo())
                .and(delYnEq(searchDto.getSearchDelYn()))
                .and(repairReqDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        )
        .orderBy(
            repair.repairReqDt.desc()
            , repair.repairNo.desc()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }
  private JPAQuery<Long> getCustomerRepairCount(final SearchDto searchDto){
    return queryFactory
        .select(repair.count())
        .from(repair)
        .where(
            repair.customerNo.eq(searchDto.getCustomerNo())
                .and(delYnEq(searchDto.getSearchDelYn()))
                .and(repairReqDtGoeLoe(searchDto.getSearchStdt(), searchDto.getSearchEddt()))
                .and(wordLike(searchDto.getSearchWord()))
        );
  }

}
