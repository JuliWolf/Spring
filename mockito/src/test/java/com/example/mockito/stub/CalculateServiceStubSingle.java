package com.example.mockito.stub;

import com.example.mockito.service.CalculateService;

/**
 * @author JuliWolf
 * @date 02.06.2023
 */
public class CalculateServiceStubSingle implements CalculateService {
  @Override
  public int[] retrieveCalculateSum() {
    return new int[] {6};
  }
}
