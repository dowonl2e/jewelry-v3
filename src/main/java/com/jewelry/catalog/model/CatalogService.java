package com.jewelry.catalog.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.catalog.dto.CatalogDto;
import com.jewelry.catalog.dto.CatalogResponseDto;
import com.jewelry.catalog.dto.CatalogStoneDto;
import com.jewelry.catalog.entity.CatalogRepository;
import com.jewelry.catalog.entity.CatalogRepositoryImpl;
import com.jewelry.catalog.entity.CatalogStoneRepository;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.file.dto.FileDto;
import com.jewelry.file.entity.FileRepository;
import com.jewelry.file.entity.FileRepositoryImpl;
import com.jewelry.file.model.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogService {

  private final CatalogRepository catalogRepository;
  private final CatalogRepositoryImpl catalogRepositoryImpl;
  private final CatalogStoneRepository catalogStoneRepository;

  private final FileRepository fileRepository;
  private final FileRepositoryImpl fileRepositoryImpl;
  private final AmazonS3Service amazonS3Service;

  @MenuAuthAnt
  public Map<String,Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<CatalogResponseDto> catalogs = catalogRepositoryImpl.getSearchCatalogs(searchDto, pageable);

    searchDto.setTotalPage(catalogs.getTotalPages());
    searchDto.setHasPrev(catalogs.hasPrevious());
    searchDto.setHasNext(catalogs.hasNext());
    searchDto.setTotalCount(catalogs.getTotalElements());

    response.put("list", catalogs.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String saveCatalogAndStones(final CatalogDto catalogDto, final CatalogStoneDto catalogStoneDto) throws Exception {
    String result = "fail";

    FileDto fileDto = amazonS3Service.uploadFile(catalogDto.getCatalogFile(), "catalog", "CATALOG");

    Long catalogNo = catalogRepository.save(catalogDto.toEntity()).getCatalogNo();
    catalogNo = ObjectUtils.isEmpty(catalogNo) ? 0 : catalogNo;
    if(catalogNo > 0) {

      catalogStoneDto.setCatalogNo(catalogNo);
      catalogStoneDto.setInptId(catalogDto.getInptId());
      saveCatalogStones(catalogStoneDto);

      if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
        fileDto.setRefNo(catalogNo);
        fileDto.setInptId(catalogDto.getInptId());
        fileRepository.save(fileDto.toEntity());
      }
      result = "success";
    }
    else {
      result = "fail";
    }
    return result;
  }

  private void saveCatalogStones(final CatalogStoneDto catalogStoneDto) throws Exception {
    if(!ObjectUtils.isEmpty(catalogStoneDto.getStoneNmArr())) {

      int stoneLen = catalogStoneDto.getStoneNmArr().length;
      for (int i = 0; i < stoneLen; i++) {
        if(!ObjectUtils.isEmpty(catalogStoneDto.getStoneNmArr()[i])) {
          catalogStoneDto.setValueByArray(
              catalogStoneDto.getStoneTypeCdArr()[i],
              catalogStoneDto.getStoneNmArr()[i],
              catalogStoneDto.getBeadCntArr()[i],
              catalogStoneDto.getPurchasePriceArr()[i],
              catalogStoneDto.getStoneDescArr()[i]
          );
          catalogStoneRepository.save(catalogStoneDto.toEntity());
        }
      }
    }
  }

  @MenuAuthAnt
  public CatalogResponseDto findByCatalogNo(final CatalogDto catalogDto) throws Exception {
    CatalogResponseDto response = catalogRepositoryImpl.getCatalog(catalogDto.getCatalogNo());
    if(!ObjectUtils.isEmpty(response)){
      response.setFiles(fileRepositoryImpl.getFiles(new FileDto(catalogDto.getCatalogNo(), "CATALOG")));
      response.setCatalogStones(catalogRepositoryImpl.getCatalogStones(catalogDto.getCatalogNo()));
    }
    return response;
  }

  @MenuAuthAnt
  public String modifyCatalogAndStones(final CatalogDto catalogDto, final CatalogStoneDto catalogStoneDto) throws Exception {
    FileDto fileDto = amazonS3Service.uploadFile(catalogDto.getCatalogFile(), "catalog", "CATALOG");

    long updateCnt = catalogRepositoryImpl.updateCatalog(catalogDto);
    if(updateCnt == 0) {
      return "fail";
    }
    catalogRepositoryImpl.deleteCatalogStonesByCatalogNo(catalogDto.getCatalogNo());

    catalogStoneDto.setCatalogNo(catalogDto.getCatalogNo());
    catalogStoneDto.setInptId(catalogDto.getUpdtId());
    saveCatalogStones(catalogStoneDto);

    if(!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
      fileDto.setRefNo(catalogDto.getCatalogNo());
      fileRepositoryImpl.updateToDelete(fileDto);

      fileDto.setInptId(catalogDto.getUpdtId());
      fileRepository.save(fileDto.toEntity());

    }
    return "success";
  }

  @MenuAuthAnt
  public String updateCatalogsToDelete(final CatalogDto catalogDto) throws Exception {
    if(ObjectUtils.isEmpty(catalogDto.getCatalogNoArr()))
      return "fail";

    return catalogRepositoryImpl.updateCatalogsToDelete(catalogDto) > 0 ? "success" : "fail";
  }
}
