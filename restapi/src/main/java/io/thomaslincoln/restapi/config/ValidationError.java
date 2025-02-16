package io.thomaslincoln.restapi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationError {
  private final String field;
  private final String message;
}
