package com.injectList.starter;

import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import javax.inject.Singleton;
import java.beans.Introspector;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
public class LegacyBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
    ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry, importBeanNameGenerator);

    // Сканируем определенный пакет
    Reflections scanner = new Reflections("com.naya.corona.legacy");
    // Получаем все классы, у которых есть аннотация `Singleton`
    Set<Class<?>> classes = scanner.getTypesAnnotatedWith(Singleton.class);
    for (Class<?> aClass : classes) {
      // Создаем новый BeanDefinition, которым мф будем подменять старый BeanDefinition
      GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
      beanDefinition.setBeanClass(aClass);
      beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
      // Добавляем метку Legacy
      beanDefinition.addQualifier(new AutowireCandidateQualifier(Legacy.class));
      // Регистрируем новый BeanDefinition по имени класса
      registry.registerBeanDefinition(Introspector.decapitalize(aClass.getSimpleName()), beanDefinition);
    }
  }
}
