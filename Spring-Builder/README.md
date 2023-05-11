# Spring builder

## Создаем основнай класс приложения
- Класс будет запускать основую логику приложения
```
public class CoronaDesinfector {
  private Announcer announcer = new AnnouncerImpl();
  private Policeman policeman = new PolicemanImpl();

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
```

* Проблема </br>
Данный класс содержит не 1 responsibility а от 7 до бесконечности
- Создание `announcer`
- выбор `announcer` имплементации
- Настройка `announcer`
- Создание `policeman`
- выбор `policeman` имплементации
- Настройка `policeman`
- Дезинфекция компаны
* Решение</br>
- Создать `ObjectFactory`

## ObjectFactory
1. Это Singleton
2. Основной метод `createObject`, который может принимать как класс так и интерфейс
3. Есть вспомогательный класс `JavaConfig`, который определяет имплементацию интерфейса, если в `createObject` был передан интерфейс
```
public class ObjectFactory {
  private Config config = new JavaConfig("com.example");

  private static ObjectFactory ourInstance = new ObjectFactory();

  public static ObjectFactory getInstance() {
    return ourInstance;
  }

  private ObjectFactory () {

  }

  @SneakyThrows
  public <T> T createObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }
    T t = implClass.getDeclaredConstructor().newInstance();

    // todo

    return t;
  }
}
```

`JavaConfig`
1. Принимает название пакета ддля сканирования
2. Находит все имплементации интерфейса
3. Если имплементаций нет или их больше 1й, то возвращает ошибку, иначе возвращает единственную имплементацию интерфейса
```
public class JavaConfig implements Config {
  private Reflections scanner;

  public JavaConfig(String packageToScan) {
    this.scanner = new Reflections(packageToScan);
  }

  @Override
  public <T> Class<? extends T> getImpClass(Class<T> type) {
    Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);

    if (classes.size() != 1) {
      throw new RuntimeException(type + " has 0 or more than 1 impl");
    }

    return classes.iterator().next();
  }
}
```
*Минус</br>
- Нет кеширования ранее найденных имплементаций
- Нельзя определить какую имплементацию мы хотим

## Добавляем кеширование в `JavaConfig`
```
public class JavaConfig implements Config {
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
```

Передаем имплементацию при инициализации `JavaConfig`</br>
`ObjectFacrory`
```
  private ObjectFactory () {
    config = new JavaConfig("com.example", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
  }
```
*Плюсы
- Мы получили централизованное место для создания всех объектов
  - Если надо менять имплементацию не надо лезть в код (Гибкость)
  - Перед тем как фабрика отдаст объект, она его может настроить согласно нашим конвенциям, которые мы придумаем

## Добавляем обработку объекта
1. После проверки класса и получения итогового класса мы можем добавить обработку свойств
2. Из класса вытаскиваем все свойства
3. Пытаемся получить аннотацию `InjectProperty.class`
4. Получаем все содержимое файла `application.properties` для того чтобы использовать его настройки для настройки объекта
5. Если у аннотации `InjectProperty` имеется значение `value` то используем его для значения поля, иначе используем название поля как значения
```
@SneakyThrows
  public <T> T createObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }
    T t = implClass.getDeclaredConstructor().newInstance();

    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);
      String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
      Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
      Map<String, String> propertiesMap = lines
          .map(line -> line.split("="))
          .collect(toMap(arr -> arr[0], arr -> arr[1]));

      String value;
      if ( annotation != null) {
        if (annotation.value().isEmpty()) {
          value = propertiesMap.get(field.getName());
        } else {
          value = propertiesMap.get(annotation.value());
        }

        field.setAccessible(true);
        // t указывается для того, чтобы засетить пропертю к конкретному объекту
        field.set(t, value);
      }
    }

    return t;
  }
```

*Минус</br>
  - Мы зашили всю логику модификации классов в `ObjectFactory`, что в дальнейшем приведет к ее быстрому увеличению в размерах

## Вынести настройку объекта в отдельный класс

1. Создаем интерфейс `ObjectConfigurator` с одним методом `configure`
```
public interface ObjectConfigurator {
  void configure (Object t);
}
```
2. Имплементируем интерфейс
  - В метод configure перенесена вся логика из `ObjectFactory`
  - Добавилось кеширование значений из файла "application.properties"

```
public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
  private Map<String, String> propertiesMap;

  @SneakyThrows
  public InjectPropertyAnnotationObjectConfigurator () {
    String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
    Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
    propertiesMap = lines
        .map(line -> line.split("="))
        .collect(toMap(arr -> arr[0], arr -> arr[1]));
  }

  @Override
  @SneakyThrows
  public void configure(Object t) {
    Class<?> implClass = t.getClass();
    
    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);
      if ( annotation != null) {
        String value;
        if (annotation.value().isEmpty()) {
          value = propertiesMap.get(field.getName());
        } else {
          value = propertiesMap.get(annotation.value());
        }

        field.setAccessible(true);
        // t указывается для того, чтобы засетить пропертю к конкретному объекту
        field.set(t, value);
      }
    }
  }
}
```

3. В классе `ObjectFactory` получаем все классы, имплементирующие интерфейс `ObjectConfigurator`
```
private List<ObjectConfigurator> configurators = new ArrayList<>();
...

@SneakyThrows
private ObjectFactory () {
  config = new JavaConfig("com.example", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
  for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
    configurators.add(aClass.getDeclaredConstructor().newInstance());
  }
}
```

4. Итерируемся по всем полученным конфигураторам и даем им настроить объект
```
@SneakyThrows
  public <T> T createObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }
    T t = implClass.getDeclaredConstructor().newInstance();
    
    // Настраиваем
    configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));
    
    return t;
  }
```

*Итого</br>
- Теперь мы можем иметь сколько угодно классов для настройки объектов
- Всю логику по настройки объекта мы передаем другим классам
- Класс ObjectFactory занимается только одной задачей - созданиаем объектов

*Минусы
- Мы используем lookup a не инверсию контроля
- Инверсия контроля (don't call up we call you)

## Отделить бизнес логику от конфигурации
1. Создаем аннотацию `InjectByType`
```
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectByType {
}
```
2. Создем конфигуратор для обработки аннотации
- Если у поля есть аннотация `InjectByType` то создаем требуюемый объект через `ObjectFactory`
```
public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
  @SneakyThrows
  @Override
  public void configure(Object t) {
    for (Field field : t.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(InjectByType.class)) {
        field.setAccessible(true);
        Object object = ObjectFactory.getInstance().createObject(field.getType());
        field.set(t, object);
      }
    }
  }
}
```
3. В основном файле приложения заменяем инициализацию полей на поля с аннотацией
```
public class CoronaDesinfector {
  @InjectByType
  private Announcer announcer;

  @InjectByType
  private Policeman policeman;
  
  ...
}
```

*Итого</br>
- Вся инфраструктурная логика у нас вынесена в конфигураторы и передана `ObjectFactory`

