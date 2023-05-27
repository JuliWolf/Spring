package com.example.unsafe_starter;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.List;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
@Data
public class LazySparkList implements List {


  @Delegate
  private List content;

  private long ownerId;

  private Class<?> modelClass;

  private String foreignKeyName;

  private String pathToSource;

  public boolean initialized(){
    return content != null /*&& !content.isEmpty()*/;
  }

}
