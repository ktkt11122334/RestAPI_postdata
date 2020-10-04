package com.rest1.postcode.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest1.postcode.jsonform.postcode.CityResource;
import com.rest1.postcode.jsonform.postcode.PostCodeResource;
import com.rest1.postcode.jsonform.postcode.PrefectureResource;
import com.rest1.postcode.service.PostCodeService;

@RestController
@Validated
public class PostCodeController {

  private PostCodeService postCodeService;

  public PostCodeController (PostCodeService postCodeService) {
    this.postCodeService = postCodeService;

  }



  @RequestMapping( value = "postcode/{postcode}", method = RequestMethod.GET)
  public ResponseEntity<Object> getPostInfoByPostCode(@PathVariable @NotNull @Pattern(regexp = "^[0-9]{3}[0-9]{4}$") String postcode) {

    PostCodeResource resource = postCodeService.searchByPostCode(postcode);
    if ( resource == null ) {
      return new ResponseEntity<>("postcode not exist", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }



  @RequestMapping( value = "address/{prefecture}", method = RequestMethod.GET)
  public ResponseEntity<Object> getPostInfoByPrefecture(
      @PathVariable @NotNull String prefecture
    ) {

    PrefectureResource resource = postCodeService.searchPostInfoByPrefecture(prefecture);
    if ( resource == null ) {
      return new ResponseEntity<>("prefecture not exist", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }



  @RequestMapping( value = "address/{prefecture}/{city}", method = RequestMethod.GET)
  public ResponseEntity<Object> getPostInfoByPrefecture(
      @PathVariable @NotNull String prefecture,
      @PathVariable @NotNull String city
    ) {

    CityResource resource = postCodeService.searchPostInfoByCity(prefecture, city);
    if ( resource == null ) {
      return new ResponseEntity<>("town not exist", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }



  @RequestMapping( value = "address/{prefecture}/{city}/{town}", method = RequestMethod.GET)
  public ResponseEntity<Object> getPostInfoByAddress(
      @PathVariable @NotNull String prefecture,
      @PathVariable @NotNull String city,
      @PathVariable @NotNull String town
    ) {

    PostCodeResource resource = postCodeService.searchPostInfoByAddress(prefecture, city, town);
    if ( resource == null ) {
      return new ResponseEntity<>("address not exist", HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(resource, HttpStatus.OK);
  }

}
