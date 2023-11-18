package com.jewelry.repair.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.repair.dto.RepairDto;
import com.jewelry.repair.dto.RepairResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RepairRepositoryCustom {

  Page<RepairResponseDto> getSearchRepairs(final SearchDto searchDto, final Pageable pageable);

  RepairResponseDto getRepair(final Long repairNo);

  long updateRepair(final RepairDto repairDto);

  long updateToDelete(final RepairDto repairDto);

  long updateRepairsToDelete(final RepairDto repairDto);
  long updateRepairsToComplete(final RepairDto repairDto);

  Page<RepairResponseDto> getSearchCustomerRepairs(final SearchDto searchDto, final Pageable pageable);

}
