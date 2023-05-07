# Spring patterns

## Singleton
1. Почему синглтоп считается anti-patterns
- Связанность благодаря статическому методу. Появляется привязка к конкретному классу
- На этапе тестирования необходим PowerMock для подмены статических методов. Страдает скорость
- Нарушает принцип Single Responsibility
  - логика singleton
  - логика создания самого себя

2. Lazy Singleton
- Если сервис обозначить как `@Lazy` то он все-равно будет создан и заинджектин во время создания класса, в котором он будет использоваться
- Если же поставить аннотацию `@Lazy` на `@Autowired` свойство, то тогда данный класс действительно будет заинджектин только в тот момент, когда к нему будет обращение
```
@Service
@Lazy
public class Blaster {
  @PostConstruct
  private void init () {
    System.out.println("you paid 100500 for the blaster");
  }

  public void fire () {
    System.out.println("Boom Boom !!!");
  }
}
```
```
@Service
public class Scwarzenegger {
  @Autowired
  @Lazy
  private Blaster blaster;

  private int stamina = 3;

  @Sheduler(fixedDelay = 500)
  public void killEnemies () {
    if (!veryTired()) {
      kickWithLog();
    } else {
      blaster.fire();
    }

    stamina--;
  }

  private void kickWithLog () {
    System.out.println("I'll kill you with my log!");
  }

  private boolean veryTired () {
    return stamina<0;
  }
}
```
  - Когда мы помечаем свойство `@Autowired` аннотацией `@Lazy` из класса создается proxy, которая заменяет реальный класс
  - В момент обращения к классу proxy делегирует метод настоящему бину через контекст

`@Lazy` так же можно поставить и на `@Autowired` через конструктор
```
@Autowired
@Lazy
private LazySingleton lazySingleton;

@Autowired
public MainService (@Lazy LazySingleton lazySingleton) {
  this.lazySingleton = lazySingleton;
}
```

## Компонентное тестирование
1. Во время компонентного тестирования мы хотим протестировать конкретный функционал не инициализируя лишних бинов
2. При дефолтной реализации спринт создаст все бины приложения
```
@Configuration
@ComponentScan
public class MockConfigurationLazy {
}
```
3. Чтобы отключить инициализацию всех бинов и оставить только инициализацию тех, что будут использоваться в тестах необходимо использовать параметр `lazyInit = true` у аннотации `@ComponentScan`
```
@Configuration
@ComponentScan(lazyInit = true)
public class MockConfigurationLazy {
}
```
* Во время сканирования будут созданы все bean definition, но сами бины создаваться не будут так как они будут помечены как lazy</br>
* Хорошо подходит для тестов, но не нужно в проде</br>

## Dependency injection как лучше писать и почему
Constructor injection с помощью @RequiredArgsConstructor</br>

Плюсы Constructor injection
  - Не дает писать большие классы, так как конструкторы слишком разрастаются, что дает понять, что Single responsibility нарушен
  - Тестируемость. При создании конструктора сразу понятно, какие зависимости нужное передать</br>

Минусы
  - Часто используеют lombok для создания конструктора, в связи с чем при написании не будет подсвечивания ошибки при неправильном inject

## Primary

