package com.rest1.postcode.jsonform.postcode;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrefectureResource {

  String prefecture;

  List<PrefectureResult> results = new ArrayList<>();

  public void setResult(PrefectureResult result) {
    results.add(result);
  }

}
