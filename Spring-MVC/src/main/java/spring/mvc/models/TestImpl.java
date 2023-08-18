package spring.mvc.models;

import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 15.08.2023
 */
@Component
public class TestImpl implements Test {
  @Override
  public String sayHello() {
    return "hello from test 1";
  }
}
