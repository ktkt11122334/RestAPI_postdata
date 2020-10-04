package com.rest1.config;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;

public class LocalDateTimeToTimestampConverter implements AttributeConverter<LocalDateTime, Timestamp> {
  // LocalDateTime → Timestamp
  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
      return Timestamp.valueOf(localDateTime);
  }
  // Timestamp → LocalDateTime
  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
      return timestamp.toLocalDateTime();
  }
}
