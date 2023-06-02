package com.example.mockito.mock;

import com.example.mockito.add.AddExample;
import com.example.mockito.service.CalculateService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

/**
 * @author JuliWolf
 * @date 01.06.2023
 */

// Junit 5
@ExtendWith(MockitoExtension.class)
public class AddExampleMockTest {

  @InjectMocks
  AddExample addExample;

  // Создаем мок для сервиса
  @Mock
  CalculateService calculateService;

  @Before
  public void beforeAll () {
    addExample.setCalculateService(calculateService);
  }


  @Test
  public void getSumServiceTest () {
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1,2,3});

    Assertions.assertEquals(6, addExample.getSumService());
  }

  @Test
  public void getSumServiceTestSingle () {
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1});

    Assertions.assertEquals(1, addExample.getSumService());
  }

  @Test
  public void getSumServiceTestEmpty () {
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {});

    Assertions.assertEquals(0, addExample.getSumService());
  }
}
