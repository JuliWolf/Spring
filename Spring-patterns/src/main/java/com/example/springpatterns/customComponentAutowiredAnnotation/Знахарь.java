package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.example.springpatterns.customComponentAutowiredAnnotation.annotations.Treatment;
import com.injectList.starter.InjectList;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Component
public class Знахарь implements Целитель {
  @Treatment(type = Лечение.АЛКОГОЛЬ)
  private Лечение водка;

  @InjectList({Баня.class, Аспирин.class})
  private List<Лечение> лечениеs;

  @Override
  public void исцелять(Patient patient) {
    System.out.println("Определяю лечение...");
//    водка.применить(patient);
    лечениеs.forEach(лечение -> лечение.применить(patient));
  }
}
