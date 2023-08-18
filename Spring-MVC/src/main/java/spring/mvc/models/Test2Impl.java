package spring.mvc.models;

import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 15.08.2023
 */
@Component
public class Test2Impl implements Test {
  @Override
  public String sayHello() {
    return "hello from test 2";
  }
}
