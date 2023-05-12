package com.example.springbuilder;

import com.example.springbuilder.annotations.InjectByType;
import com.example.springbuilder.models.*;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
@Deprecated
public class CoronaDesinfector {
  @InjectByType
  private Announcer announcer;

  @InjectByType
  private Policeman policeman;

  public void start (Room room) {
    // todo сообщить всем присутствующим в комнате, о начале дезинфекции, и попросить всех свалить
    announcer.announce("Начинаем дезинфекцию, все вон!");
    // todo разогнать всех кто не вышел после объявления
    policeman.makePeopleLeaveRoom();
    desinfect(room);
    // todo сообщить всем присутсвующим в комнате, что они могут вернуться обратно
    announcer.announce("Рискрине зайти обратно");
  }

  public void desinfect(Room room) {
    System.out.println("зачитывается молитва: 'корона изыди!' - молитва прочитана, корона низвергнута в ад");
  }
}
