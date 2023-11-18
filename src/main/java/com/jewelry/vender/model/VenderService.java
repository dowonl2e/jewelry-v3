package com.jewelry.vender.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.vender.dto.VenderDto;
import com.jewelry.vender.dto.VenderResponseDto;
import com.jewelry.vender.entity.VenderRepository;
import com.jewelry.vender.entity.VenderRepositoryImpl;
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
public class VenderService {

  private final VenderRepositoryImpl venderRepositoryImpl;

  private final VenderRepository venderRepository;

  @MenuAuthAnt
  public Map<String,Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<VenderResponseDto> venders = venderRepositoryImpl.getSearchVenders(searchDto, pageable);

    if(!ObjectUtils.isEmpty(venders)) {
      searchDto.setTotalPage(venders.getTotalPages());
      searchDto.setHasPrev(venders.hasPrevious());
      searchDto.setHasNext(venders.hasNext());
      searchDto.setTotalCount(venders.getTotalElements());

      response.put("list", venders.getContent());
    }
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final VenderDto venderDto){
    Long venderNo = venderRepository.save(venderDto.toEntity()).getVenderNo();
    venderNo = ObjectUtils.isEmpty(venderNo) ? 0 : venderNo;
    return venderNo > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public VenderResponseDto findByVenderNo(final VenderDto venderDto){
    return venderRepositoryImpl.getVenderByVenderNo(venderDto.getVenderNo());
  }

  @MenuAuthAnt
  public String update(final VenderDto venderDto){
    return venderRepositoryImpl.updateVender(venderDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateVendersToDelete(final VenderDto venderDto){
    return venderRepositoryImpl.updateVendersToDelete(venderDto) > 0 ? "success" : "fail";
  }
}
