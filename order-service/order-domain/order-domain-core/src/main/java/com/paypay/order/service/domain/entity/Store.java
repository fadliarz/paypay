package com.paypay.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

public class Store {

  private UUID id;
  private List<Product> products;

  private Store(Builder builder) {
    id = builder.id;
    products = builder.products;
  }

  public UUID getId() {
    return id;
  }

  public List<Product> getProducts() {
    return products;
  }

  public static final class Builder {

    private UUID id;
    private List<Product> products;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setId(UUID val) {
      id = val;
      return this;
    }

    public Builder setProducts(List<Product> val) {
      products = val;
      return this;
    }

    public Store build() {
      return new Store(this);
    }
  }
}
