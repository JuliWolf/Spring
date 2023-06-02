package com.example.mockito.add;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author JuliWolf
 * @date 01.06.2023
 */
public class AddExampleTest {
  @Test
  public void addMethodTest () {
    AddExample addExample = new AddExample();
    int actualResult = addExample.addMethod(10, 10, 10);
    int expectedResult = 30;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void calculateSumTest () {
    AddExample addExample = new AddExample();
    int actualResult = addExample.calculateSum(new int[] {1, 2, 3});
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void calculateSumTestEmpty () {
    AddExample addExample = new AddExample();
    int actualResult = addExample.calculateSum(new int[] {});
    int expectedResult = 0;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void calculateSumTestOne () {
    AddExample addExample = new AddExample();
    int actualResult = addExample.calculateSum(new int[] {1});
    int expectedResult = 1;
    Assertions.assertEquals(expectedResult, actualResult);
  }

}
