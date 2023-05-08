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

## Свои аннотации
```
@Retention(RetentionPolicy.RUNTIME)
@Component
@Qualifier
@Autowired
public @interface Treatment {
  String type();
}
```

```
@Component
public class Знахарь implements Целитель {
  @Treatment(type=Лечение.АЛКОГОЛЬ)
  private Лечение водка;

  @Override
  public void исцелять(Patient patient) {
    System.out.println("Определяю лечение...");
    водка.применить(patient);
  }
}
```
1. Когда мы хотим написать свою аннотацию, которая будет заменять и дополнять аннотацию `@Component`
- id бина все-равно будет вытаскиваться из value `@Component` а не из type, так как type является qualifier
- для данного кейса value для компонента будет являться `водка` то есть заинджектится класс Водка

## Chain of responsibility
Задача:</br>
1. Написать метод handle для определенных объектов
2. Далее просят дополнить метод handle для еще нескольких объектов</br>
Итого: Нарушается принцип `Open Close principle`
```
public class MainHandler {
  public void handle(DataObject t) {
    handle1(t);
    handle2(t);
    handle3(t);
  }
}
```
Решение: </br>
1. Создать интерфейс `Handler`
2. Создать ряд необходимых реализаций данного интерфейса
3. Заинджектить лист `Handler`
4. перебрать через forEach и вызыать у всех методом метод handler или любой другой, входящий в интерфейс `Handler`
```
@Service
public class MainHandler {

  @Autowired
  private List<Handler> handlers;
  
  public void handle (DataObject t) {
    handlers.forEach(handler -> handler.handle(t));
  }
}
```

### Как заинджектить свой лист, кастомный лист
1. Нужна некоторая своя аннотация `@InjectList`, которая в качестве параметра будет принимать список классов
2. Создадим свой стартер</br>
Основой стартера будет аннотация + BeanPostProcessor который будет находить поля с данной аннотацией и добавлять данные
```
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectList {
  Class[] value();
}
```

```
public class InjectListBPP implements BeanPostProcessor {
  @Autowired
  private ApplicationContext context;

  @lombok.SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    Set<Field> fields = ReflectionUtils.getAllFields(bean.getClass(), field -> field.isAnnotationPresent(InjectList.class));

    for (Field field : fields) {
      InjectList annotation = field.getAnnotation(InjectList.class);
      List<Object> list = Arrays.stream(annotation.value())
          .map(aClass -> Introspector.decapitalize(aClass.getSimpleName()))
          .map(name -> context.getBean(name))
          .collect(Collectors.toList());

      field.setAccessible(true);
      field.set(bean, list);
    }
    return bean;
  }
}
```

Использование
```
@Component
public class Знахарь implements Целитель {
  @InjectList({Баня.class, Аспирин.class})
  private List<Лечение> лечениеs;

  @Override
  public void исцелять(Patient patient) {
    System.out.println("Определяю лечение...");
//    водка.применить(patient);
    лечениеs.forEach(лечение -> лечение.применить(patient));
  }
}
```

Таким образом мы можем определять какие именно классы будут инджектится в наш лист

## Заменить старые аннотации на новые

1. Импортируем пакет со старыми зависимостями (например https://github.com/Jeka1978/joker-corona-legacy) в котором используются устаревшие аннотации `Singleton`
2. В стартере создаем класс LegacyBeanDefinitionRegistrar, который будет анализировать пакет и заменять старые бины на новые</br>
Эта обработка происходит на этапе компиляции
```
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
```
*** В данном классе можно подменять бины например которые хранятся в базе данных, подменять бины

3. Регистрируем LegacyBeanDefinitionRegistrar
```
@Configuration
@Import(LegacyBeanDefinitionRegistrar.class)
public class InjectListConfiguration {
  @Bean
  public InjectListBPP injectListBPP () {
    return new InjectListBPP();
  }
}
```

4. Используем новую аннотацию
```
@Component
public class Священник implements Целитель {
  @Autowired
  @Legacy
  List<Лечение> устаревшиеМетоды;

  @Override
  public void исцелять(Patient patient) {
    устаревшиеМетоды.forEach(лечение -> лечение.применить(patient));
  }
}
```

## Стратегия и Комманда (Strategy & Command)

### Избавляемся от switch
Есть сервис, в котором определяется каким методом пациента будут лечить. Способ лечения зависит от пожеланий пациента
```
@Service
public class HospitalImpl implements Hospital {
  @Autowired
  private Cleric cleric;

  @Autowired
  private AlcoDoctor alcoDoctor;

  @Autowired
  private DefaultHealer defaultHealer;

  @Autowired
  private Physician physician;

  @Override
  public void processPatient(Patient patient) {
    switch (patient.getMethod()) {
      case Healer.TRADITIONAL -> {
        physician.treat(patient);
        break;
      }
      case Healer.FOLK -> {
        cleric.treat(patient);
        break;
      }
      case Healer.ALCOHOL -> {
        alcoDoctor.treat(patient);
        break;
      }
      default -> defaultHealer.treat(patient);
    }
  }
}
```

1. Заинджектить мапу, где ключем будет `id` бина, а значением `Healer`
```
@Service
public class NewHospitalImpl implements Hospital {
  @Autowired
  private Map<String, Healer> map;

  @Override
  public void processPatient(Patient patient) {
      map.getOrDefault(patient.getMethod(), new DefaultHealer()).treat(patient);
  }
}
```
*Минус 
- Для данной реализации необходимо задать id бину `@Component(Healer.ALCOHOL)`
```
@Component(Healer.ALCOHOL)
public class AlcoDoctor implements Healer {
  @TreatmentType(type = Treatment.ALCOHOL)
  private Treatment cognac;

  @InjectList({Sauna.class, Aspirin.class})
  private List<Treatment> treatments;

  @Override
  public void treat(Patient patient) {
    System.out.println("Определяю лечение...");
//    водка.применить(patient);
    treatments.forEach(treatment -> treatment.use(patient));
  }

  @Override
  public String myType() {
    return ALCOHOL;
  }
}
```

2. Заинджектить мапу, где ключем будет тип лечения, а значением `Healer`
   - Добавить метод `myType` для всех целителей
```
  @Override
  public String myType() {
    return ALCOHOL;
  }
```
  - Получить лист с Healer в конструкторе и замапить список в мапу
```
  private Map<String, Healer> map;

  public NewHospitalImpl (List<Healer> healerList) {
    map = healerList.stream().collect(toMap(Healer::myType, Function.identity()));
  }
```
*Минус
- Госпиталь не должен сам составлять себе мапу лекарей. Это должно быть в конфигурации

3. Создать бин в конфигурации, который будет создавать мапу целителей
  - В файле с аннотацией @Configuration объявить бин
```
@Bean
public Map<String, Healer> hospitalMap (List<Healer> healersList) {
  map = healerList.stream().collect(toMap(Healer::myType, Function.identity()));
}
```

4. Созданные целители сами регистрируются в больнице
- Во время работы системы создается целитель и добавляется в больницу


***
Как в runtime регистрировать бины
1. Сервис от `ClassLoader`
- Создать сервис, который будет наследоваться от `ClassLoader`
- Данный класс будет принимать некий className
- Находить в какой-то папке класс
- Создавать класс из байткода
```
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
```
2. Создать класс для хранения данных о бине
```
@Data
public class BeanMD {
  // id
  private String beanName;
  // полное название класса
  private String beanClassName;
}
```
3. Создать контроллер для обработка запроса и создании и регистрации бина по полученным параметрам
```
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
```


