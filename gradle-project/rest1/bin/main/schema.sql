CREATE TABLE IF NOT EXISTS get1 (
  uri varchar(255) PRIMARY KEY
);


CREATE TABLE IF NOT EXISTS post1 (
  id INT PRIMARY KEY,
  date_time TIMESTAMP,
  method varchar(255),
  status_code SMALLINT
);

CREATE TABLE IF NOT EXISTS post_data (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  post_code varchar(7),
  prefecture varchar(255),
  city varchar(255),
  town varchar(255),
  prefecture_kana varchar(255),
  city_kana varchar(255),
  town_kana varchar(255),

  INDEX idx_post_code(post_code),
  INDEX idx_prefecture_address_town(prefecture, city, town)
);