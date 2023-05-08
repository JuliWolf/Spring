package com.example.springpatterns.strategy_command_pattern.infra;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@RestController
public class BeanRegistratorController {
  @Autowired
  private GenericApplicationContext context;

  @Autowired
  private CCL ccl;

  @SneakyThrows
  @PostMapping("/regbean")
  public String regBean(@RequestBody BeanMD beanMD) {
    // Получаем класс по полному названию файла
    Class<?> beanClass = ccl.findClass(beanMD.getBeanClassName());
    // Получаем из контекста beanFactory
    BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
    // Создаем тело бина
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    // Настраиваем бин
    beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
    beanDefinition.setBeanClass(beanClass);
    // Регистрируем бин
    beanFactory.registerBeanDefinition(beanMD.getBeanName(), beanDefinition);
    // Вызывваем бин для его активации
    context.getBean(beanMD.getBeanName());

    return "registered";
  }
}
