package com.example.unsafe_starter.aspect;

import com.example.unsafe_starter.FirstLevelCacheService;
import com.example.unsafe_starter.LazySparkList;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
@Aspect
public class LazyCollectionAspectHandler {
  @Autowired
  private FirstLevelCacheService cacheService;

  @Autowired
  private ConfigurableApplicationContext context;
  // Для всех методов, которые были унаследованы от List
  @Before("execution(* com.example.unsafe_starter.LazySparkList.*(..)) && execution(* java.util.List.*(..))")
  public void setLazyCollections (JoinPoint jp) {
    LazySparkList lazyList = (LazySparkList) jp.getTarget();

    if (!lazyList.initialized()) {
      List<Object> content = cacheService.readDataFor(
          lazyList.getOwnerId(),
          lazyList.getModelClass(),
          lazyList.getPathToSource(),
          lazyList.getForeignKeyName(),
          context
      );
      lazyList.setContent(content);
    }
  }
}
