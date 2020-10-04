package com.rest1.postcode.jsonform.postcode;

import lombok.Data;

@Data
public class CityResult {

  String town;

  String townKana;

  String link;

  public void setCity(String town) {
    this.town = (town != null) ? town : "";
  }

  public void setCityKana(String townKana) {
    this.townKana = (townKana != null) ? townKana : "";
  }

  public void setLink(String prefecture, String city, String town) {
    link = String.format( "http://localhost:8080/address/%s/%s/%s", prefecture, city, town);
  }

}
