package com.rest1.common.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /** https://qiita.com/leonis_sk/items/c954face2c5c1cbf3802
  *
  */
@RestController
public class ErrorHandlerController implements ErrorController {

  @RequestMapping("/error")
  public ResponseEntity<String> handleError() {
      return new ResponseEntity<String>("received unexpected request path.", HttpStatus.BAD_REQUEST);
  }

  @Override
  public String getErrorPath() {
      return "/error";
  }

}
