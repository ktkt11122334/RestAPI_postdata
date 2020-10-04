package com.rest1.get1.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rest1.get1.entity.Get1Entity;

@Repository
public interface Get1Repository extends JpaRepository<Get1Entity, String> {

  @Query(value = "SELECT * FROM get1", nativeQuery = true)
  List<Get1Entity> get1List();

}
