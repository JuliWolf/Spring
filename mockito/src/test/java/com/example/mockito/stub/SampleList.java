package com.example.mockito.stub;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author JuliWolf
 * @date 02.06.2023
 */
public class SampleList {
  @Test
  public void listSizeTest () {
    List mock = mock(List.class);
    when(mock.size()).thenReturn(10).thenReturn(20);
    assertEquals(10, mock.size());
    assertEquals(20, mock.size());
  }

  @Test
  public void listValueTest () {
    List mock = mock(List.class);
    when(mock.get(0)).thenReturn("Hello");
    assertEquals("Hello", mock.get(0));
  }
}
