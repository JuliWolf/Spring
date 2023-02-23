package com.example.demo.ManyToMany;

public class Test {

  public static void main(String[] args) {
    // max swimming pool capacity
    int volume = 1200;

    // current swimming pool water volume
    int currentVolume = 0;

    // filling speed per minute
    int fillingSpeed = 30;

    // water devastation speed per minute
    int devastationSpeed = 10;

    // swimming pool filling time
    int fillingCycles = 0;

    while(currentVolume < volume) {
      currentVolume = currentVolume + fillingSpeed - devastationSpeed;

      fillingCycles++;
    }

    System.out.println(currentVolume);
    System.out.println(fillingCycles);
  }
}
