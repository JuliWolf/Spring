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
