package com.rest1.get1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest1.get1.entity.Get1Entity;
import com.rest1.get1.repo.Get1Repository;

@RestController
@RequestMapping("get1")
public class get1Controller {

  private Get1Repository get1Repo;

  public get1Controller(Get1Repository get1Repo) {
    this.get1Repo = get1Repo;
  }

  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<Get1Entity> get1List() {
    return this.get1Repo.get1List();
  }


}
