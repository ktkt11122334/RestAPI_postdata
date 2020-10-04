package com.rest1.postdata.imp;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rest1.postdata.imp.pathcomponent.CsvPathComponent;

@Service
public class PostDataImportService {

  @Value("${mysql.connection.hostname}")
  private String mysqlHostName = "";

  @Value("${mysql.connection.username}")
  private String mysqlUserName = "";

  @Value("${mysql.connection.password}")
  private String mysqlPassword = "";

  private static final String DATA_FILE_NAME = "/data/KEN_ALL.CSV";

  private static final int POST_CODE_COLUMN_ORDER = 2;
  private static final int PREFECTURE_COLUMN_ORDER = 6;
  private static final int CITY_COLUMN_ORDER = 7;
  private static final int TOWN_COLUMN_ORDER = 8;
  private static final int PREFECTURE_KANA_COLUMN_ORDER = 3;
  private static final int CITY_KANA_COLUMN_ORDER = 4;
  private static final int TOWN_KANA_COLUMN_ORDER= 5;

  private static final int PARTITION_LENGTH = 10000;

  private CsvPathComponent csvPathComponent;

  public PostDataImportService(CsvPathComponent csvPathComponent) {
    this.csvPathComponent = csvPathComponent;
  }

  public void importPostData() throws Exception {

    final String URL = "jdbc:mysql://" + mysqlHostName + ":3306/rest";
    final String USERNAME = mysqlUserName;
    final String PASSWORD = mysqlPassword;

    try (
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = conn.createStatement();
        ) {

      List<String> bulkData = getBulkInsertData();
      insertPostData(conn, statement, bulkData);

    }

  }



  private List<String> getBulkInsertData() throws Exception {

    URI postResource = this.getClass().getResource(DATA_FILE_NAME).toURI();

    List<String> bulkData = new ArrayList<String>();
    try ( Stream<String> csvData = Files.lines(csvPathComponent.getCsvDataPath(postResource), Charset.forName("MS932")) ) {

      csvData.forEach( row -> {

        String[] columns = row.split(",");
        String[] insertColumns = {
            columns[POST_CODE_COLUMN_ORDER],
            columns[PREFECTURE_COLUMN_ORDER],
            columns[CITY_COLUMN_ORDER],
            columns[TOWN_COLUMN_ORDER],
            columns[PREFECTURE_KANA_COLUMN_ORDER],
            columns[CITY_KANA_COLUMN_ORDER],
            columns[TOWN_KANA_COLUMN_ORDER]
        };

        String[] formattedDatas = Arrays.stream(insertColumns)
            .map(column -> !column.isEmpty() ? column : "\'\'" )
            .toArray(String[]::new);
        String sqlInsertData = String.join(",", formattedDatas);

        bulkData.add("(" + sqlInsertData + ")");
      });

    }

    return bulkData;
  }


  private static void insertPostData(Connection conn, Statement statement, List<String> bulkData) throws Exception {

    try {

      conn.setAutoCommit(false);
      for( List<String> partionedDbRecord : Lists.partition(bulkData, PARTITION_LENGTH) ) {

        String dbRecords = String.join(",", partionedDbRecord);
        String sql = "INSERT INTO post_data(post_code, prefecture, city, town, prefecture_kana, city_kana, town_kana) "
                   + "VALUES " + dbRecords;

        statement.executeUpdate(sql);
      }
      conn.commit();

    }
    catch (Exception e) {
      conn.rollback();
      throw new Exception(e);
    }

  }


}
