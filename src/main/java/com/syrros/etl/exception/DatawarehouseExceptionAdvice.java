package com.syrros.etl.exception;

import com.syrros.etl.message.ResponseMessage;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatawarehouseExceptionAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
            .body(new ResponseMessage("File too large!"));
  }

  @ExceptionHandler(DataWarehouseException.class)
  public ResponseEntity<Object> handleSimpleDataWarehouseException() {
    return ResponseEntity.badRequest()
            .body(new ResponseMessage("Unable to perform query"));
  }
}