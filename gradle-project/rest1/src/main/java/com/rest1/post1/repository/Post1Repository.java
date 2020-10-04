package com.rest1.post1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rest1.post1.entity.Post1Entity;

public interface Post1Repository extends JpaRepository<Post1Entity, Short> {


  @Query(value = "SELECT * FROM post1", nativeQuery = true)
  List<Post1Entity> getPost1List();

  @Query(value = "SELECT * FROM post1 WHERE post1.id = ?", nativeQuery = true)
  Post1Entity getPost1Info(Integer id);

}
