package com.rest1.post1.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.rest1.config.LocalDateTimeToTimestampConverter;
import com.rest1.cons.DbStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "post1")
@NoArgsConstructor
public class Post1Entity implements Cloneable {

  @Transient
  private DbStatus dbStatus;

  public Post1Entity(Integer id, LocalDateTime dateTime, String method, Short statusCode) {
    this.id = id;
    this.dateTime = dateTime;
    this.method = method;
    this.statusCode = statusCode;
  }

  @Id
  @Column(name = "id")
  Integer id;

  @Column(name = "date_time", nullable = false)
  @Convert(converter = LocalDateTimeToTimestampConverter.class)
  LocalDateTime dateTime;

  @Column(name = "method")
  String method;

  @Column(name = "status_code")
  Short statusCode;


  /**
   * For deep copy.
   */
  @Override
  @Deprecated
  public Post1Entity clone() {

    Post1Entity copy = new Post1Entity();
    copy.setId(this.id);
    copy.setDateTime(this.dateTime);
    copy.setMethod(this.method);
    copy.setStatusCode(this.statusCode);

    return copy;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(this.getDateTime());
    sb.append(" ");
    sb.append(this.getMethod());
    sb.append(" ");
    sb.append(this.getStatusCode());

    return sb.toString();
  }

}
