package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Treatment;
import com.example.springpatterns.customComponentAutowiredAnnotation.annotations.TreatmentType;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Aspirin;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Sauna;
import com.injectList.starter.InjectList;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Component(Healer.ALCOHOL)
public class AlcoDoctor implements Healer {
  @TreatmentType(type = Treatment.ALCOHOL)
  private Treatment cognac;

  @InjectList({Sauna.class, Aspirin.class})
  private List<Treatment> treatments;

  @Override
  public void treat(Patient patient) {
    System.out.println("AlcoDoctor says:");
//    водка.применить(patient);
    treatments.forEach(treatment -> treatment.use(patient));
  }

  @Override
  public String myType() {
    return ALCOHOL;
  }
}
