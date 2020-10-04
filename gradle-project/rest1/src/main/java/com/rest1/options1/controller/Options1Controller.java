package com.rest1.options1.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest1.common.http.ResponseHeaderBuilder;

@RestController
public class Options1Controller {

  /**
   * Reference:
   * https://qiita.com/kazuki43zoo/items/53d2ca25c174ff618331
   */
  @RequestMapping( value = "/list", method = RequestMethod.OPTIONS)
  public HttpHeaders options1(UriComponentsBuilder uriBuilder) {
    HttpMethod[] httpMethods = { HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST };
    return new ResponseHeaderBuilder(uriBuilder).setAllow(httpMethods).build();
  }

  @RequestMapping( value = "/list/item1", method = RequestMethod.OPTIONS)
  public HttpHeaders item1(UriComponentsBuilder uriBuilder) {
    HttpMethod[] httpMethods = { HttpMethod.GET, HttpMethod.HEAD, HttpMethod.PUT, HttpMethod.DELETE };
    return new ResponseHeaderBuilder(uriBuilder).setAllow(httpMethods).build();
  }

}
