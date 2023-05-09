package com.injectList.starter;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */
@ControllerAdvice(annotations = CoronaController.class)
public class CoronaControllerAdvice implements ResponseBodyAdvice {
  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    // фильтруем методы, для которых это должно работать
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    return new CoronaWrapper(body, false);
  }
}
