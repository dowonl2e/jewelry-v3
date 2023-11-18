package com.jewelry.order.entity;

import com.jewelry.common.domain.SearchDto;
import com.jewelry.order.dto.OrderDto;
import com.jewelry.order.dto.OrderResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

  Page<OrderResponseDto> getSearchOrders(final SearchDto searchDto, final Pageable pageable);

  OrderResponseDto getOrder(final Long orderNo);

  long updateOrder(final OrderDto orderDto);

  long updateOrdersStep(final OrderDto orderDto);

  long updateOrdersToDelete(final OrderDto orderDto);

  long updateOrdersCustomer(final OrderDto orderDto);

  long updateOrdersVender(final OrderDto orderDto);

  long updateOrdersSaleDate(final OrderDto orderDto);

  List<OrderResponseDto> getOrdersByNos(final OrderDto orderDto);

  Page<OrderResponseDto> getSearchCustomerOrders(final SearchDto searchDto, final Pageable pageable);

  List<OrderResponseDto> getNumOfOrdersPerMaterial(final SearchDto searchDto);
}
