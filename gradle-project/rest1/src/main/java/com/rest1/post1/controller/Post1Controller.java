package com.rest1.post1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest1.common.http.ResponseHeaderBuilder;
import com.rest1.cons.DbStatus;
import com.rest1.post1.entity.Post1Entity;
import com.rest1.post1.exceptions.InvalidParamsException;
import com.rest1.post1.service.Post1Service;

@RestController
@RequestMapping("post1")
public class Post1Controller {

  private Post1Service post1Service;

  public Post1Controller(Post1Service post1Service) {
    this.post1Service = post1Service;
  }

  @RequestMapping(value = "getList", method = RequestMethod.GET)
  public List<String> post1List() throws Exception {
    List<Post1Entity> entitys = post1Service.listPost1Info();

    List<String> tsvList = entitys.stream()
                          .map(entity -> entity.toString())
                          .collect(Collectors.toList());

    return tsvList;
  }

  @RequestMapping(value = "getOne/{id}", method = RequestMethod.GET)
  public String post1One(@PathVariable Integer id) {
    return post1Service.getPostOne(id).toString();
  }

  // Reference
  //   https://stackoverrun.com/ja/q/6591301
  //   https://itneko.com/kotlin-character-encoding-filter/
  //   https://arimodoki.mydns.jp/promenade/json_chart_cntl.html
  @RequestMapping(value = "post/{id}", method = RequestMethod.POST)
  public ResponseEntity<String> postInfo(@PathVariable Integer id, @RequestBody String postInfo, UriComponentsBuilder uriBuilder) {


    Post1Entity res = null;
    try {
      res = this.post1Service.getPost1Info(id, postInfo);
    } catch (InvalidParamsException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    if (DbStatus.UPDATE.equals(res.getDbStatus())) {
      return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }
    else {
      String basePath = "post1/getOne/{id}";
      String idStr = res.getId().toString();

      HttpHeaders headers = new ResponseHeaderBuilder(uriBuilder)
                                  .setLocation(basePath, idStr)
                                  .build();

      return new ResponseEntity<>(res.toString(), headers, HttpStatus.CREATED);
    }

  }

}
