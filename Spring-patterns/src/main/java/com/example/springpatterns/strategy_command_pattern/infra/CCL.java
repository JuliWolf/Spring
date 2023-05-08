package com.example.springpatterns.strategy_command_pattern.infra;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Service
public class CCL extends ClassLoader {
  @Override
  @SneakyThrows
  public Class<?> findClass (String className) {
    // Ищет в компилированных файлах классы
    String fileName = "target/classes/"+className.replace('.', File.separatorChar)+".class";
    // получаем bytecode
    byte[] bytecode = Files.newInputStream(Path.of(fileName)).readAllBytes();
    return defineClass(className, bytecode, 0, bytecode.length);
  }
}
