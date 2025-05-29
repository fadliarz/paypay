package com.store.service.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private final UUID id;
  private String image;
  private String name;
  private String description;
  private BigDecimal price;

  public UUID getId() {
    return id;
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

  private Product(Builder builder) {
    id = builder.id;
    image = builder.image;
    name = builder.name;
    description = builder.description;
    price = builder.price;
  }

  public static final class Builder {

    private UUID id;
    private String image;
    private String name;
    private String description;
    private BigDecimal price;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setId(UUID val) {
      id = val;
      return this;
    }

    public Builder setImage(String val) {
      image = val;
      return this;
    }

    public Builder setName(String val) {
      name = val;
      return this;
    }

    public Builder setDescription(String val) {
      description = val;
      return this;
    }

    public Builder setPrice(BigDecimal val) {
      price = val;
      return this;
    }

    public Product build() {
      return new Product(this);
    }
  }
}
