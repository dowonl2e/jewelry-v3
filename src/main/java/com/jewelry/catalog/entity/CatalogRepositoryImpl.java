package com.jewelry.catalog.entity;

import static com.jewelry.catalog.entity.QCatalog.catalog;
import static com.jewelry.catalog.entity.QCatalogStone.catalogStone;
import static com.jewelry.file.entity.QFile.file;
import static com.jewelry.stock.entity.QStock.stock;
import static com.jewelry.vender.entity.QVender.vender;

import com.jewelry.catalog.dto.*;
import com.jewelry.catalog.value.CatalogEnum;
import com.jewelry.common.domain.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.LiteralExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class CatalogRepositoryImpl implements CatalogRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<CatalogResponseDto> getSearchCatalogs(final SearchDto searchDto, final Pageable pageable) {
    List<CatalogResponseDto> content = getCatalogs(searchDto, pageable);
    JPAQuery<Long> countQuery = getCatalogCount(searchDto);
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

  private List<CatalogResponseDto> getCatalogs(final SearchDto searchDto, final Pageable pageable){
    return queryFactory
        .select(new QCatalogResponseDto(
            catalog.catalogNo, catalog.venderNo, catalog.modelId, catalog.modelNm, catalog.stddMaterialCd
            , catalog.stddWeight, catalog.stddColorCd, catalog.stddSize, catalog.basicIdst
            , vender.venderNm
            , file.filePath, file.originNm, file.fileNm)
        )
        .from(catalog)
        .leftJoin(catalog.vender, vender)
        .on(
            vender.delYn.eq("N")
        )
        .leftJoin(file)
        .on(
            catalog.catalogNo.eq(file.refNo)
                .and(file.refInfo.eq("CATALOG"))
                .and(file.fileOrd.eq(1))
                .and(file.delYn.eq("N"))
        )
        .where(
            delYnEq(searchDto.getSearchDelYn()),
            venderNoEq(searchDto.getSearchVender()),
            wordLike(searchDto.getSearchWord())
        )
        .orderBy(catalog.regDt.desc(), catalog.catalogNo.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private JPAQuery<Long> getCatalogCount(final SearchDto searchDto){
    return queryFactory
        .select(catalog.count())
        .from(catalog)
        .where(
            delYnEq(searchDto.getSearchDelYn()),
            venderNoEq(searchDto.getSearchVender()),
            wordLike(searchDto.getSearchWord())
        );
  }

  private BooleanExpression delYnEq(String delYn){
    return delYn != null ? catalog.delYn.eq(delYn) : null;
  }
  private BooleanExpression venderNoEq(Long venderNo){
    return venderNo != null ? catalog.venderNo.eq(venderNo) : null;
  }
  private BooleanExpression wordLike(String word){
    return ObjectUtils.isEmpty(word) ? null :
        Expressions.numberTemplate(
            Double.class,
            "function('match2',{0},{1},{2})",
            catalog.modelId,catalog.modelNm,word
        ).gt(0);
  }

  @Override
  public CatalogResponseDto getCatalog(final Long catalogNo) {
    return queryFactory
        .select(new QCatalogResponseDto(
            catalog.catalogNo, catalog.venderNo, catalog.modelId
            , catalog.modelNm, catalog.stddMaterialCd, catalog.stddWeight
            , catalog.stddColorCd, catalog.stddSize, catalog.odrNotice
            , catalog.regDt, catalog.basicIdst, catalog.mainPrice
            , catalog.subPrice, catalog.totalPrice, vender.venderNm)
        )
        .from(catalog)
        .leftJoin(catalog.vender, vender)
        .where(
            catalog.catalogNo.eq(catalogNo)
        )
        .fetchOne();
  }

  @Override
  public List<CatalogStoneResponseDto> getCatalogStones(Long catalogNo) {
    return queryFactory
        .select(new QCatalogStoneResponseDto(
            catalogStone.stoneNo, catalogStone.catalogNo, catalogStone.stoneTypeCd
            , catalogStone.stoneNm, catalogStone.beadCnt, catalogStone.purchasePrice
            , catalogStone.stoneDesc)
        )
        .from(catalogStone)
        .where(
            catalogStone.catalogNo.eq(catalogNo)
        )
        .orderBy(
            catalogStone.stoneNo.asc()
        )
        .fetch();
  }

  @Override
  public Long updateCatalog(final CatalogDto catalogDto) {
    return queryFactory
        .update(catalog)
        .set(catalog.updtDt, LocalDateTime.now())
        .set(catalog.updtId, catalogDto.getUpdtId())
        .set(catalog.venderNo, catalogDto.getVenderNo())
        .set(catalog.modelId, catalogDto.getModelId())
        .set(catalog.modelNm, catalogDto.getModelNm())
        .set(catalog.stddMaterialCd, catalogDto.getStddMaterialCd())
        .set(catalog.stddWeight, catalogDto.getStddWeight())
        .set(catalog.stddColorCd, catalogDto.getStddColorCd())
        .set(catalog.stddSize, catalogDto.getStddSize())
        .set(catalog.odrNotice, catalogDto.getOdrNotice())
        .set(catalog.regDt, catalogDto.getRegDt())
        .set(catalog.basicIdst, catalogDto.getBasicIdst())
        .set(catalog.mainPrice, catalogDto.getMainPrice())
        .set(catalog.subPrice, catalogDto.getSubPrice())
        .set(catalog.totalPrice, catalogDto.getTotalPrice())
        .where(catalog.catalogNo.eq(catalogDto.getCatalogNo()))
        .execute();
  }

  @Override
  public Long deleteCatalogStonesByCatalogNo(final Long catalogNo) {
    return queryFactory
        .delete(catalogStone)
        .where(
            catalogStone.catalogNo.eq(catalogNo)
        )
        .execute();
  }

  @Override
  public Long updateCatalogsToDelete(final CatalogDto catalogDto) {
    return queryFactory
        .update(catalog)
        .set(catalog.updtDt, LocalDateTime.now())
        .set(catalog.updtId, catalogDto.getUpdtId())
        .set(catalog.delYn, "Y")
        .where(catalog.catalogNo.in(catalogDto.getCatalogNoArr()))
        .execute();
  }

}
