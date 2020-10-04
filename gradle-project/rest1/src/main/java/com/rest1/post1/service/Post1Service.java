package com.rest1.post1.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest1.cons.DbStatus;
import com.rest1.post1.entity.Post1Entity;
import com.rest1.post1.exceptions.InvalidParamsException;
import com.rest1.post1.repository.Post1Repository;

@Service
public class Post1Service {

  private Post1Repository post1Repo;

  private static final String FORMAT_STYLE = "yyyy-MM-dd'T'HH:mm:ss'Z'";


  public Post1Service(Post1Repository post1Repo) {
    this.post1Repo = post1Repo;
  }

  public List<Post1Entity> listPost1Info() {
    return post1Repo.getPost1List();
  }

  public Post1Entity getPostOne(Integer id) {
    return post1Repo.getPost1Info(id);
  }

  @Transactional
  public Post1Entity getPost1Info(Integer id, String postInfo) throws Exception {

    Post1Entity postEntity = null;
    try {
      postEntity = getPost1Entity(id, postInfo);
    } catch (Exception e) {
      throw new InvalidParamsException("Invalid request：" + postInfo);
    }


    Post1Entity dbPostInfo = post1Repo.getPost1Info(id);
    post1Repo.save(postEntity);

    if (dbPostInfo != null) {
      postEntity.setDbStatus(DbStatus.UPDATE);
    }
    else {
      postEntity.setDbStatus(DbStatus.INSERT);
    }
    return postEntity;
  }

  private Post1Entity getPost1Entity(Integer id, String postInfo) throws Exception {

    String[] postColumn = postInfo.split(",");
    postColumn = Arrays.stream(postColumn).map(value -> value.trim()).toArray(String[]::new);

    LocalDateTime dateTime = LocalDateTime.parse(postColumn[0], DateTimeFormatter.ofPattern(FORMAT_STYLE));
    String method = postColumn[1];
    Short statusCode = Short.parseShort(postColumn[2]);


    return new Post1Entity(id, dateTime, method, statusCode);
  }

}
