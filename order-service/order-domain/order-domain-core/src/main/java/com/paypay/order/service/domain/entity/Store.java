package com.paypay.order.service.domain.entity;

import java.util.List;
import java.util.UUID;

public class Store {

  private UUID id;
  private List<Product> products;

  private Store(Builder builder) {
    id = builder.storeId;
    products = builder.products;
  }

  public UUID getId() {
    return id;
  }

  public List<Product> getProducts() {
    return products;
  }

  public static final class Builder {

    private UUID storeId;
    private List<Product> products;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder storeId(UUID val) {
      storeId = val;
      return this;
    }

    public Builder products(List<Product> val) {
      products = val;
      return this;
    }

    public Store build() {
      return new Store(this);
    }
  }
}
