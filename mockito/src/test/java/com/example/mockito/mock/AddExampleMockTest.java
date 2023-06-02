package com.example.mockito.mock;

import com.example.mockito.add.AddExample;
import com.example.mockito.service.CalculateService;
import com.example.mockito.stub.CalculateServiceStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author JuliWolf
 * @date 01.06.2023
 */
public class AddExampleMockTest {

  @Test
  public void getSumServiceTest () {
    AddExample addExample = new AddExample();

    // Создаем мок для сервиса
    CalculateService calculateService = mock(CalculateService.class);
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1,2,3});

    addExample.setCalculateService(calculateService);

    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getSumServiceTestSingle () {
    AddExample addExample = new AddExample();

    // Создаем мок для сервиса
    CalculateService calculateService = mock(CalculateService.class);
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1});

    addExample.setCalculateService(calculateService);

    int actualResult = addExample.getSumService();
    int expectedResult = 1;
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void getSumServiceTestEmpty () {
    AddExample addExample = new AddExample();

    // Создаем мок для сервиса
    CalculateService calculateService = mock(CalculateService.class);
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {});

    addExample.setCalculateService(calculateService);

    int actualResult = addExample.getSumService();
    int expectedResult = 0;
    Assertions.assertEquals(expectedResult, actualResult);
  }
}
