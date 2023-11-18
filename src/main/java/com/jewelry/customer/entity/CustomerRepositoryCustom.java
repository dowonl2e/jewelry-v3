package com.jewelry.customer.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.customer.dto.CustomerDto;
import com.jewelry.customer.dto.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryCustom {

  Page<CustomerResponseDto> getSearchCustomers(final SearchDto searchDto, final Pageable pageable);

  CustomerResponseDto getCustomer(final Long customerNo);

  long updateCustomer(final CustomerDto customerDto);

  long updateToDelete(final CustomerDto customerDto);

  long updateCustomersToDelete(final CustomerDto customerDto);

}
