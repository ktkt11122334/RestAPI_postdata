package com.rest1.postcode.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest1.common.http.ResponseHeaderBuilder;
import com.rest1.postcode.exception.ExistedDataException;
import com.rest1.postcode.jsonform.postcode.PostCodeResource;
import com.rest1.postcode.service.UpdateService;

@RestController
public class UpdateController {

  private UpdateService updateService;

  public UpdateController(UpdateService updateService) {
    this.updateService = updateService;
  }



  @RequestMapping( value = "/postcode", method = RequestMethod.POST )
  public ResponseEntity<Object> factoryPostData( @RequestBody @Validated PostCodeResource additionPostData, UriComponentsBuilder uriBuilder) throws Exception {

    HttpHeaders header = new ResponseHeaderBuilder(uriBuilder)
        .setLocation("postcode/{postcode}", additionPostData.getZipcode())
        .build();

    try {
      updateService.insertPostData(additionPostData);
    } catch (ExistedDataException e) {
      return new ResponseEntity<>(e.getMessage(), header, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(additionPostData, header, HttpStatus.CREATED);
  }

}
