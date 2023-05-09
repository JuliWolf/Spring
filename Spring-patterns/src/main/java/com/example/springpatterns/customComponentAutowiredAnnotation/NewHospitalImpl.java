package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.example.springpatterns.customComponentAutowiredAnnotation.healers.DefaultHealer;
import com.example.springpatterns.customComponentAutowiredAnnotation.healers.Healer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Service
public class NewHospitalImpl implements Hospital {
//  // 1.
//  //  String - заданный id бина
//  //  Healer - лекарь
//  @Autowired
//  private Map<String, Healer> map;
//

//  // 2.
//  // String - тип полученный из метода myType
//  // Healer - лекарь
//  private Map<String, Healer> map;

//  public NewHospitalImpl (List<Healer> healerList) {
//    map = healerList.stream().collect(toMap(Healer::myType, Function.identity()));
//  }

  // 4
  // String - тип полученный из метода myType
  // Healer - лекарь
  private Map<String, Healer> map = new HashMap<>();

  @Override
  public void register(String type, Healer healer) {
    map.put(type, healer);
  }

  @Override
  public void processPatient(Patient patient) {
    map.getOrDefault(patient.getMethod(), new DefaultHealer()).treat(patient);
  }
}
