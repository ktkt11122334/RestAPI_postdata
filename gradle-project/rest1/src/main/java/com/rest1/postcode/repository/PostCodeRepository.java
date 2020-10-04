package com.rest1.postcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rest1.postcode.entity.PostCodeEntity;

public interface PostCodeRepository extends JpaRepository<PostCodeEntity, Integer> {

  @Query(value = "SELECT * FROM post_data WHERE post_data.post_code = ?", nativeQuery = true)
  PostCodeEntity getPostDataByPostcode(String postcode);

  @Query(value = "SELECT * FROM post_data WHERE post_data.prefecture = ? AND post_data.city = ? AND post_data.town = ?", nativeQuery = true)
  PostCodeEntity getPostDataByAddress(String prefecture, String city, String town);

}
