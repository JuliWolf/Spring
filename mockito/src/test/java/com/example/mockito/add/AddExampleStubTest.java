package com.example.mockito.add;

import com.example.mockito.stub.CalculateServiceStub;
import com.example.mockito.stub.CalculateServiceStubEmpty;
import com.example.mockito.stub.CalculateServiceStubSingle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author JuliWolf
 * @date 01.06.2023
 */
public class AddExampleStubTest {

  @Test
  public void getSumServiceTest () {
    AddExample addExample = new AddExample();
    addExample.setCalculateService(new CalculateServiceStub());
    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getSumServiceTestEmpty () {
    AddExample addExample = new AddExample();
    addExample.setCalculateService(new CalculateServiceStubEmpty());
    int actualResult = addExample.getSumService();
    int expectedResult = 0;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getSumServiceTestSingle () {
    AddExample addExample = new AddExample();
    addExample.setCalculateService(new CalculateServiceStubSingle());
    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }
}
