package com.rest1.postcode.jsonform.postcode;

import javax.validation.constraints.NotNull;

import lombok.Setter;

@Setter
public class Yomi {

  @NotNull
  String prefecture;

  @NotNull
  String city;

  @NotNull
  String town;



  public String getPrefecture() {
    return (prefecture != null) ? prefecture : "";
  }

  public String getCity() {
    return (city != null) ? city : "";
  }

  public String getTown() {
    return (town != null) ? town : "";
  }

}
