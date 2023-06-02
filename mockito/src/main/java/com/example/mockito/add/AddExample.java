package com.example.mockito.add;

import com.example.mockito.service.CalculateService;

/**
 * @author JuliWolf
 * @date 01.06.2023
 */
public class AddExample {
  private CalculateService calculateService;

  public void setCalculateService(CalculateService calculateService) {
    this.calculateService = calculateService;
  }

  public int addMethod (int a, int b, int c) {
    int sum = a + b + c;
    return sum;
  }

  public int calculateSum (int a[]) {
    int sum = 0;

    for (int i : a) {
      sum += i;
    }

    return sum;
  }

  public int getSumService () {
    int sum = 0;
    int a[] = calculateService.retrieveCalculateSum();

    for (int i : a) {
      sum += i;
    }

    return sum;
  }
}
