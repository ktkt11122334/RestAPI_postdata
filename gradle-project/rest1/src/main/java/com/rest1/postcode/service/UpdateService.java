package com.rest1.postcode.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest1.postcode.entity.PostCodeEntity;
import com.rest1.postcode.exception.ExistedDataException;
import com.rest1.postcode.jsonform.postcode.Address;
import com.rest1.postcode.jsonform.postcode.PostCodeResource;
import com.rest1.postcode.jsonform.postcode.Yomi;
import com.rest1.postcode.repository.PostCodeRepository;

@Service
public class UpdateService {

  private PostCodeRepository postCodeRepository;

  public UpdateService(PostCodeRepository postCodeRepository) {
    this.postCodeRepository = postCodeRepository;
  }


  /**
   *
   * @param additionPostData
   * @throws Exception
   */
  @Transactional
  public void insertPostData(PostCodeResource additionPostData) throws Exception {

    String postCode = additionPostData.getZipcode();
    PostCodeEntity existedEntity = postCodeRepository.getPostDataByPostcode(postCode);

    if ( existedEntity != null ) {
      throw new ExistedDataException("postcode is existed");
    }



    PostCodeEntity entity = new PostCodeEntity();
    entity.setPostCode(additionPostData.getZipcode());

    Address address = additionPostData.getAddress();
    entity.setPrefecture(address.getPrefecture());
    entity.setCity(address.getCity());
    entity.setTown(address.getTown());

    Yomi yomi = additionPostData.getYomi();
    entity.setPrefectureKana(yomi.getPrefecture());
    entity.setCityKana(yomi.getCity());
    entity.setTown_kana(yomi.getTown());

    postCodeRepository.save(entity);

  }

}
