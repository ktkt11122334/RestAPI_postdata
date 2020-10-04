package com.rest1.postcode.jsonform.postcode;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCodeResource {

  @NotNull
  @Pattern(regexp = "^[0-9]{3}[0-9]{4}$")
  String zipcode;

  @Valid
  Address address;

  @Valid
  Yomi yomi;

}
