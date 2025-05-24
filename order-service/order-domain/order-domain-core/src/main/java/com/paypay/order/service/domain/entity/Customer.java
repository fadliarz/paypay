package com.paypay.order.service.domain.entity;

import java.util.UUID;

public class Customer {

  private final UUID id;
  private final String username;
  private String firstName;
  private String lastName;

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  private Customer(Builder builder) {
    id = builder.id;
    username = builder.username;
    firstName = builder.firstName;
    lastName = builder.lastName;
  }

  public static final class Builder {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;

    private Builder() {}

    public static Builder builder() {
      return new Builder();
    }

    public Builder setId(UUID val) {
      id = val;
      return this;
    }

    public Builder setUsername(String val) {
      username = val;
      return this;
    }

    public Builder setFirstName(String val) {
      firstName = val;
      return this;
    }

    public Builder setLastName(String val) {
      lastName = val;
      return this;
    }

    public Customer build() {
      return new Customer(this);
    }
  }
}
