package com.rest1.postcode.jsonform.postcode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrefectureResult {

  String city;

  String cityKana;

  String link;

  public void setCity(String city) {
    this.city = (city != null) ? city : "";
  }

  public void setCityKana(String cityKana) {
    this.cityKana = (cityKana != null) ? cityKana : "";
  }

  public void setLink(String prefecture, String city) {
    link = String.format( "http://localhost:8080/address/%s/%s", prefecture, city);
  }

}
