package com.rest1.postcode.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.rest1.postcode.entity.PostCodeEntity;
import com.rest1.postcode.jsonform.postcode.Address;
import com.rest1.postcode.jsonform.postcode.CityResource;
import com.rest1.postcode.jsonform.postcode.CityResult;
import com.rest1.postcode.jsonform.postcode.PostCodeResource;
import com.rest1.postcode.jsonform.postcode.PrefectureResource;
import com.rest1.postcode.jsonform.postcode.PrefectureResult;
import com.rest1.postcode.jsonform.postcode.Yomi;
import com.rest1.postcode.repository.PostCodeRepository;

@Service
public class PostCodeService {

  private PostCodeRepository postCodeRepository;

  private JdbcTemplate jdbcTemplate;

  public PostCodeService(PostCodeRepository postCodeRepository, JdbcTemplate jdbcTemplate) {
    this.postCodeRepository = postCodeRepository;
    this.jdbcTemplate = jdbcTemplate;
  }



  public PostCodeResource searchByPostCode (String postcode) {

    postcode = postcode.replace("-", "");
    PostCodeEntity entity = postCodeRepository.getPostDataByPostcode(postcode);

    if ( entity == null ) {
      return null;
    }

    Address address = new Address();
    address.setPrefecture(entity.getPrefecture());
    address.setCity(entity.getCity());
    address.setTown(entity.getTown());

    Yomi yomi = new Yomi();
    yomi.setPrefecture(entity.getPrefectureKana());
    yomi.setCity(entity.getCityKana());
    yomi.setTown(entity.getTown_kana());

    return new PostCodeResource(entity.getPostCode(), address, yomi);

  }



  public PrefectureResource searchPostInfoByPrefecture (String prefecture) {

    List<Map<String, Object>> entities = jdbcTemplate.queryForList(
        String.format(
            "SELECT city, city_kana FROM post_data WHERE prefecture = '%s' GROUP BY city, city_kana;",
            prefecture
        )
    );

    if ( entities.size() == 0 ) {
      return null;
    }

    PrefectureResource resource = new PrefectureResource();
    resource.setPrefecture(prefecture);

    entities.stream().forEach(entity -> {

      PrefectureResult result = new PrefectureResult();
      result.setCity((String)entity.get("city"));
      result.setCityKana((String)entity.get("city_kana"));
      result.setLink(prefecture, (String)entity.get("city"));

      resource.setResult(result);
    });

    return resource;
  }



  public CityResource searchPostInfoByCity (String prefecture, String city) {

    List<Map<String, Object>> entities = jdbcTemplate.queryForList(
        String.format(
            "SELECT town, town_kana FROM post_data WHERE prefecture = '%s' AND city = '%s'",
            prefecture, city
        )
    );

    if ( entities.size() == 0 ) {
      return null;
    }

    CityResource resource = new CityResource();
    resource.setPrefecture(prefecture);
    resource.setCity(city);

    entities.stream().forEach(entity -> {

      CityResult result = new CityResult();
      result.setTown((String)entity.get("town"));
      result.setCityKana((String)entity.get("town_kana"));
      result.setLink(prefecture, city, (String)entity.get("town"));

      resource.setResult(result);
    });

    return resource;
  }



  public PostCodeResource searchPostInfoByAddress (String prefecture, String city, String town) {

    PostCodeEntity entity = postCodeRepository.getPostDataByAddress(prefecture, city, town);

    if ( entity == null ) {
      return null;
    }

    Address address = new Address();
    address.setPrefecture(entity.getPrefecture());
    address.setCity(entity.getCity());
    address.setTown(entity.getTown());

    Yomi yomi = new Yomi();
    yomi.setPrefecture(entity.getPrefectureKana());
    yomi.setCity(entity.getCityKana());
    yomi.setTown(entity.getTown_kana());

    return new PostCodeResource(entity.getPostCode(), address, yomi);

  }

}
