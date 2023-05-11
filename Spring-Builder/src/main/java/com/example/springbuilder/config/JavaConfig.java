package com.example.springbuilder.config;

import com.example.springbuilder.config.Config;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class JavaConfig implements Config {
  @Getter
  private Reflections scanner;
  private Map<Class, Class> ifc2ImplClass;

  public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
    this.scanner = new Reflections(packageToScan);
    this.ifc2ImplClass = ifc2ImplClass;
  }

  @Override
  public <T> Class<? extends T> getImpClass(Class<T> ifc) {
    return ifc2ImplClass.computeIfAbsent(ifc, aclass -> {
      Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

      if (classes.size() != 1) {
        throw new RuntimeException(ifc + " has 0 or more than 1 impl please update your config");
      }

      return classes.iterator().next();
    });
  }
}
