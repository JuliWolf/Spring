package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.injectList.starter.Legacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Component
public class Священник implements Целитель {
  @Autowired
  List<Лечение> устаревшиеМетоды;

  @Override
  public void исцелять(Patient patient) {
    устаревшиеМетоды.forEach(лечение -> лечение.применить(patient));
  }
}
