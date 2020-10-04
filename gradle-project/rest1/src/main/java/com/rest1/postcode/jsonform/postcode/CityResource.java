package com.rest1.postcode.jsonform.postcode;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResource {

  String prefecture;

  String city;

  List<CityResult> results = new ArrayList<>();

  public void setResult(CityResult result) {
    results.add(result);
  }

}
