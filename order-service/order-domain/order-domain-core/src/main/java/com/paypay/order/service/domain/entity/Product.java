package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private UUID productId;
  private String image;
  private String name;
  private String description;
  private BigDecimal price;

  private Product(Builder builder) {
    productId = builder.productId;
    image = builder.image;
    name = builder.name;
    description = builder.description;
    price = builder.price;
  }

  public UUID getProductId() {
    return productId;
  }

  public String getImage() {
    return image;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public static final class Builder {

    private UUID productId;
    private String image;
    private String name;
    private String description;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder productId(UUID val) {
      productId = val;
      return this;
    }

    public Builder image(String val) {
      image = val;
      return this;
    }

    public Builder name(String val) {
      name = val;
      return this;
    }

    public Builder description(String val) {
      description = val;
      return this;
    }

    public Builder price(BigDecimal val) {
      price = val;
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }
}
