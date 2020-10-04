package com.rest1.get1.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="get1")
public class Get1Entity {

  @Id
  @Column(name = "uri")
  public String uri;

}
