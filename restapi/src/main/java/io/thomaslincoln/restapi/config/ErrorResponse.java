package io.thomaslincoln.restapi.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private final int status;
  private final String message;
  private String StackTrace;
  private List<ValidationError> errors;

  public void addValidationError(String field, String message){
    if(Objects.isNull(errors)){
      this.errors = new ArrayList<>();
    }
    this.errors.add(new ValidationError(field, message));
  }

  public String toJson(){
    return "{\"status\": " + getStatus() + ", " + 
    "\"message\": \"" + getMessage() + "\"}";     
  }
}
