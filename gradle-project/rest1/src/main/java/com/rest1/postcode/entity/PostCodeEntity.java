package com.rest1.postcode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table( name = "post_data")
public class PostCodeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Integer id;

  @Column(name = "post_code")
  String postCode;

  @Column(name = "prefecture")
  String prefecture;

  @Column(name = "city")
  String city;

  @Column(name = "town")
  String town;

  @Column(name = "prefecture_kana")
  String prefectureKana;

  @Column(name = "city_kana")
  String cityKana;

  @Column(name = "town_kana")
  String town_kana;

}
