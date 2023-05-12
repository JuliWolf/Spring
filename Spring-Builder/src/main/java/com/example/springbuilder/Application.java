package com.example.springbuilder;

import com.example.springbuilder.config.JavaConfig;
import com.example.springbuilder.factory.ObjectFactory;

import java.util.Map;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public class Application {
  public static ApplicationContext run (String packageToScan, Map<Class, Class> ifc2ImplClass) {
    JavaConfig config = new JavaConfig(packageToScan, ifc2ImplClass);
    ApplicationContext context = new ApplicationContext(config);
    ObjectFactory objectFactory = new ObjectFactory(context);
    // todo - init all singletons which are not lazy
    context.setFactory(objectFactory);
    return context;
  }
}
