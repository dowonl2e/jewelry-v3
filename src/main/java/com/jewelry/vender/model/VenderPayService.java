package com.jewelry.vender.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.vender.dto.VenderPayDto;
import com.jewelry.vender.dto.VenderPayResponseDto;
import com.jewelry.vender.dto.VenderResponseDto;
import com.jewelry.vender.entity.VenderPayRepository;
import com.jewelry.vender.entity.VenderPayRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class VenderPayService {

  private final VenderPayRepository venderPayRepository;
  private final VenderPayRepositoryImpl venderPayRepositoryImpl;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<VenderPayResponseDto> pays = venderPayRepositoryImpl.getSearchVenderPays(searchDto, pageable);

    if(!ObjectUtils.isEmpty(pays)) {
      searchDto.setTotalPage(pays.getTotalPages());
      searchDto.setHasPrev(pays.hasPrevious());
      searchDto.setHasNext(pays.hasNext());
      searchDto.setTotalCount(pays.getTotalElements());

      response.put("list", pays.getContent());
    }
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final VenderPayDto venderPayDto){
    Long payNo = venderPayRepository.save(venderPayDto.toEntity()).getPayNo();
    payNo = ObjectUtils.isEmpty(payNo) ? 0 : payNo;
    return payNo > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public VenderPayResponseDto findByPayNo(final VenderPayDto venderPayDto){
    return venderPayRepositoryImpl.getVenderPayByPayNo(venderPayDto.getPayNo());
  }

  @MenuAuthAnt
  public String update(final VenderPayDto venderPayDto){
    return venderPayRepositoryImpl.updateVenderPay(venderPayDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateToDelete(final VenderPayDto venderPayDto){
    return venderPayRepositoryImpl.updateVenderPaysToDelete(venderPayDto) > 0 ? "success" : "fail";
  }

}
