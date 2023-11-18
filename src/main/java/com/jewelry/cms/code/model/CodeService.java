package com.jewelry.cms.code.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.cms.code.dto.CodeDto;
import com.jewelry.cms.code.dto.CodeResponseDto;
import com.jewelry.cms.code.entity.CodeRepository;
import com.jewelry.cms.code.entity.CodeRepositoryImpl;
import com.jewelry.common.domain.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CodeService {

  private final CodeRepository codeRespository;

  private final CodeRepositoryImpl codeRepositoryImpl;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<CodeResponseDto> catalogs = codeRepositoryImpl.getSearchCodes(searchDto, pageable);

    searchDto.setTotalPage(catalogs.getTotalPages());
    searchDto.setHasPrev(catalogs.hasPrevious());
    searchDto.setHasNext(catalogs.hasNext());
    searchDto.setTotalCount(catalogs.getTotalElements());

    response.put("list", catalogs.getContent());
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final CodeDto codeDto){
    Integer cdOrd = 1;
    if(codeDto.getCdOrd() == null){
      cdOrd = codeRepositoryImpl.getMaxOrder(codeDto.getUpCdId(), codeDto.getCdDepth());
      cdOrd = cdOrd == null ? 1 : (cdOrd+1);
    }
    codeDto.setCdOrd(cdOrd);
    String cdId = codeRespository.save(codeDto.toEntity()).getCdId();
    return ObjectUtils.isEmpty(cdId) ? "fail" : "success";
  }

  @MenuAuthAnt
  public CodeResponseDto findByCdId(final CodeDto codeDto){
    return codeRepositoryImpl.getCode(codeDto.getCdId());
  }

  @MenuAuthAnt
  public String update(final CodeDto codeDto){
    return codeRepositoryImpl.updateCode(codeDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String remove(final CodeDto codeDto){
    long removeCnt = codeRepositoryImpl.deleteCode(codeDto.getCdId());
    if(removeCnt > 0)
      codeRepositoryImpl.deleteLowCodeByCdId(codeDto.getCdId());
    return removeCnt > 0 ? "success" : "fail";
  }


  @MenuAuthAnt
  public Map<String, Object> findAllSubCode(final CodeDto codeDto){
    Map<String, Object> response = new HashMap<>();

    response.put("vo", codeRepositoryImpl.getCode(codeDto.getUpCdId()));
    response.put("list", codeRepositoryImpl.getLowCodes(codeDto));
    response.put("params", codeDto);

    return response;
  }

  @MenuAuthAnt
  public List<CodeResponseDto> findAllByUpCdId(String upCdId, Integer cdDepth) {
    return findAllByUpCdId(upCdId, cdDepth, "Y");
  }

  @MenuAuthAnt
  public List<CodeResponseDto> findAllByUpCdId(String upCdId, Integer cdDepth, String useYn) {
    CodeDto to = new CodeDto();
    to.setUpCdIdArr(new String[]{upCdId});
    to.setCdDepth(cdDepth);
    to.setUseYn(useYn);
    return codeRepositoryImpl.getCodesByUpCdId(to);
  }

  public List<CodeResponseDto> findAllByUpCdId(String[] upcdid, Integer cddepth) {
    CodeDto to = new CodeDto();
    to.setUpCdIdArr(upcdid);
    to.setCdDepth(cddepth);
    return codeRepositoryImpl.getCodesByUpCdId(to);
  }

}
