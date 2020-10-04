package com.rest1.common.http;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

public class ResponseHeaderBuilder {

  private HttpHeaders responseHeader = new HttpHeaders();

  private UriComponentsBuilder uriBuilder;

  private String etag;


  public ResponseHeaderBuilder(UriComponentsBuilder uriBuilder) {
    this.uriBuilder = uriBuilder;
  }



  public ResponseHeaderBuilder setLocation(String basePath, Object... params) {

    UriComponentsBuilder LocationSet = uriBuilder.path(basePath);
    URI uri = LocationSet.buildAndExpand(params).toUri();

    this.responseHeader.setLocation(uri);
    return this;
  }

  public ResponseHeaderBuilder setAllow(HttpMethod... httpMethods) {

    Set<HttpMethod> allows = new LinkedHashSet<>();
    for ( HttpMethod httpMethod : httpMethods) {
      allows.add(httpMethod);
    }

    this.responseHeader.setAllow(allows);
    return this;
  }

  public ResponseHeaderBuilder setEtag(String etag) {
    this.responseHeader.setETag(etag);
    return this;
  }

  public ResponseHeaderBuilder setNewEtag() throws NoSuchAlgorithmException {

    //ハッシュ生成処理
    byte[] bytes = MessageDigest.getInstance("SHA-512").digest(LocalDateTime.now().toString().getBytes());
    String etag = DatatypeConverter.printHexBinary(bytes);
    this.etag = etag;

    this.responseHeader.setETag(etag);
    return this;
  }

  public String getEtag() {
    return this.etag;
  }

  public HttpHeaders build() {
    return this.responseHeader;
  }

}
