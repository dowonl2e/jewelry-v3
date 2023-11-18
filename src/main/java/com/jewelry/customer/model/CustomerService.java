package com.jewelry.customer.model;

import com.jewelry.annotation.MenuAuthAnt;
import com.jewelry.common.domain.SearchDto;
import com.jewelry.customer.dto.CustomerDto;
import com.jewelry.customer.dto.CustomerResponseDto;
import com.jewelry.customer.entity.CustomerRepository;
import com.jewelry.customer.entity.CustomerRepositoryImpl;
import com.jewelry.vender.dto.VenderResponseDto;
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
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerRepositoryImpl customerRepositoryImpl;

  @MenuAuthAnt
  public Map<String, Object> findAll(final SearchDto searchDto, final Pageable pageable){
    Map<String, Object> response = new HashMap<>();

    Page<CustomerResponseDto> customers = customerRepositoryImpl.getSearchCustomers(searchDto, pageable);

    if(!ObjectUtils.isEmpty(customers)) {
      searchDto.setTotalPage(customers.getTotalPages());
      searchDto.setHasPrev(customers.hasPrevious());
      searchDto.setHasNext(customers.hasNext());
      searchDto.setTotalCount(customers.getTotalElements());

      response.put("list", customers.getContent());
    }
    response.put("params", searchDto);

    return response;
  }

  @MenuAuthAnt
  public String save(final CustomerDto customerDto){
    Long customerNo = customerRepository.save(customerDto.toEntity()).getCustomerNo();
    customerNo = ObjectUtils.isEmpty(customerNo) ? 0 : customerNo;
    return customerNo > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public CustomerResponseDto findByCustomerNo(final CustomerDto customerDto){
    return customerRepositoryImpl.getCustomer(customerDto.getCustomerNo());
  }

  @MenuAuthAnt
  public String update(final CustomerDto customerDto){
    return customerRepositoryImpl.updateCustomer(customerDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateToDelete(final CustomerDto customerDto){
    return customerRepositoryImpl.updateToDelete(customerDto) > 0 ? "success" : "fail";
  }

  @MenuAuthAnt
  public String updateCustomersToDelete(final CustomerDto customerDto){
    return customerRepositoryImpl.updateCustomersToDelete(customerDto) > 0 ? "success" : "fail";
  }
}
