package com.paypay.order.service.domain.mapper;

import com.paypay.order.service.client.store.entity.ProductDetails;
import com.paypay.order.service.domain.features.create.order.dto.OrderItemDto;
import com.paypay.order.service.domain.features.create.order.dto.ProductDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class OrderIntegrationTestDataMapper {

  public static List<OrderItemDto> productDetailsListToOrderItemDtoList(
      List<ProductDetails> productDetailsList) {
    return IntStream.range(0, productDetailsList.size())
        .mapToObj(
            (index) -> {
              ProductDetails productDetails = productDetailsList.get(index);
              OrderItemDto.OrderItemDtoBuilder orderItemDtoBuilder = OrderItemDto.builder();
              orderItemDtoBuilder.product(productDetailsToProductDto(productDetails));
              int quantity = index + 1;
              orderItemDtoBuilder.quantity(quantity);
              orderItemDtoBuilder.subTotalPrice(
                  productDetails.getPrice().multiply(BigDecimal.valueOf(quantity)));
              return orderItemDtoBuilder.build();
            })
        .toList();
  }

  public static ProductDto productDetailsToProductDto(ProductDetails productDetails) {
    ProductDto.ProductDtoBuilder productDtoBuilder = ProductDto.builder();
    productDtoBuilder.id(productDetails.getId());
    productDtoBuilder.image(productDetails.getImage());
    productDtoBuilder.name(productDetails.getName());
    productDtoBuilder.description(productDetails.getDescription());
    productDtoBuilder.price(productDetails.getPrice());
    return productDtoBuilder.build();
  }
}
