package com.paypay.order.service.domain.entity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {

  private final UUID id;
  private final String image;
  private final String name;
  private final String description;
  private final BigDecimal price;

  public boolean matches(Product product) {
    return (this.image.equals(product.getImage())
        && this.name.equals(product.getName())
        && this.description.equals(product.getDescription())
        && this.price.equals(product.getPrice()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id)
        && Objects.equals(image, product.image)
        && Objects.equals(name, product.name)
        && Objects.equals(description, product.description)
        && Objects.equals(price, product.price);
  }

  private Product(Builder builder) {
    id = builder.id;
    image = builder.image;
    name = builder.name;
    description = builder.description;
    price = builder.price;
  }

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
