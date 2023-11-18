package com.jewelry.repair.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.file.dto.FileDto;
import com.jewelry.file.entity.FileRepository;
import com.jewelry.file.model.AmazonS3Service;
import com.jewelry.repair.dto.RepairDto;
import com.jewelry.repair.dto.RepairResponseDto;
import com.jewelry.repair.entity.RepairRepository;
import com.jewelry.repair.entity.RepairRepositoryImpl;
import lombok.RequiredArgsConstructor;
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
public class RepairService {

  private final RepairRepository repairRepository;

  private final RepairRepositoryImpl repairRepositoryImpl;

  private final AmazonS3Service amazonS3Service;

  private final FileRepository fileRepository;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<RepairResponseDto> repairs = repairRepositoryImpl.getSearchRepairs(searchDto, pageable);
    if(!ObjectUtils.isEmpty(repairs)) {
      searchDto.setTotalPage(repairs.getTotalPages());
      searchDto.setHasPrev(repairs.hasPrevious());
      searchDto.setHasNext(repairs.hasNext());
      searchDto.setTotalCount(repairs.getTotalElements());

      response.put("list", repairs.getContent());
    }
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public RepairResponseDto findByRepairNo(final RepairDto repairDto){
    return repairRepositoryImpl.getRepair(repairDto.getRepairNo());
  }

  @MenuAuthAnt
  public String save(final RepairDto repairDto) {
    String result = "fail";
    try {
      FileDto fileDto = amazonS3Service.uploadFile(repairDto.getRepairFile(), "repair", "REPAIR");

      Long repairNo = repairRepository.save(repairDto.toEntity()).getRepairNo();
      if (repairNo != null && repairNo > 0) {
        if (!ObjectUtils.isEmpty(fileDto.getOriginNm())) {
          fileDto.setRefNo(repairNo);
          fileDto.setInptId(repairDto.getInptId());
          fileRepository.save(fileDto.toEntity());
        }
        result = "success";
      }
    }
    catch (Exception e){
      result = "fail";
    }
    return result;
  }

  @MenuAuthAnt
  public String update(final RepairDto repairDto){
    return repairRepositoryImpl.updateRepair(repairDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateToDelete(final RepairDto repairDto){
    return repairRepositoryImpl.updateToDelete(repairDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateRepairsToDelete(final RepairDto repairDto){
    return repairRepositoryImpl.updateRepairsToDelete(repairDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateRepairsToComplete(final RepairDto repairDto){
    return repairRepositoryImpl.updateRepairsToComplete(repairDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public Map<String, Object> findAllCustomerRepairs(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<RepairResponseDto> repairs = repairRepositoryImpl.getSearchCustomerRepairs(searchDto, pageable);
    if(!ObjectUtils.isEmpty(repairs)) {
      searchDto.setTotalPage(repairs.getTotalPages());
      searchDto.setHasPrev(repairs.hasPrevious());
      searchDto.setHasNext(repairs.hasNext());
      searchDto.setTotalCount(repairs.getTotalElements());

      response.put("list", repairs.getContent());
    }
    response.put("params", searchDto);

    return response;
  }


}
