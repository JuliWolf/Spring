package spring.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.mvc.models.Test;

import java.util.List;

/**
 * @author JuliWolf
 * @date 15.08.2023
 */

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private List<Test> testList;

  @GetMapping("/")
  public String getTestData () {
    StringBuilder stringBuilder = new StringBuilder();

    for (Test test : testList) {
      stringBuilder.append(test.sayHello());
    }

    return stringBuilder.toString();
  }
}
