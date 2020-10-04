package com.rest1.postdata.imp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "rest")
public class ImportPostDataController {

  private PostDataImportService postDataImportService;

  public ImportPostDataController(PostDataImportService postDataImportService) {
    this.postDataImportService = postDataImportService;
  }

  @RequestMapping(value = "import/postdata", method = RequestMethod.POST)
  public ResponseEntity<String> importPostData() {

    try {
      postDataImportService.importPostData();
    }
    catch(Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>("Created new post data", HttpStatus.CREATED);
  }

}
