from</br>
https://www.youtube.com/watch?v=hTC12FfCqT4 </br>
https://www.youtube.com/watch?v=yWEy7j5lvaE </br></br>

# Spark builder

Задача:
1. Создать свой аналог Spark JPA

Как должно работать:
1. Должен уметь анализировать пакет на предмет моделей
2. Уметь превращать интерфейс с дефолтными методами в класс (аналог JPARepository - findByName etc)
3. Уметь читать файлы разных форматов (cvs, json)

## Подготовка к реализации
1. Создаем класс `Entity`. В нашей реализации отметку `Entity` будет выплнять кастомная аннотация `Source`
```
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/speakers.json")
public class Speaker {
  private String name;
  private int age;
}
```
2. Создаем аннотацию `Source`
```
@Retention(RUNTIME)
public @interface Source {
  String value();
}
```

3. Создаем интерфейс `SparkRepository` - аналог JPARepository
```
public interface SparkRepository<M> {
}
```

4. Так как мы все будем через прокси нам понадобится обработчик InvocationHandler
```
public class SparkInvocationHandlerFactory implements InvocationHandler {
  // Класс модели (1)
  private Class<?> modelClass;

  // Ссылка на данные для данной модели(1)
  private String pathToData;

  // Класс для извлечения данных(1)
  private DataExtractor dataExtractor;

  // Трансформации (у каждого метода свой список)
  private Map<Method, List<SparkTransformation>> transformationChain;

  // Терминальная операция (у каждого метода свой список)
  private Map<Method, Finalizer> finalizerMap;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return null;
  }
}
```

5. `SparkTransformation` будет интерфейсом, такак реализаций может быть много
```
public interface SparkTransformation {
}
```

6. `DataExtractor` так же будет интерфейсом, так как данные могут извлекаться разными способами
* Коллекции spark
  - RDD - коллекции, похожая на stream api
  - Dataframe - похоже на работу с sql</br></br>
  
  - DataSet - смесь `RDD` и `Dataframe`
```
public interface DataExtractor {
  Dataset<Row> load (String pathToData);
}
```

7. `Finalizer` тоже будет интерфейсом, так как терминальных методом может быть много
```
public interface Finalizer {
  Object doAction (Dataset<Row> rowDataset);
}
```

## Как будет работать SparkInvocationHandlerFactory
1. Когда `SparkInvocationHandlerFactory` обращается он определяет
- Получить данные с помощью `DataExtractor`
- Трансвормируем полученные данные
- Вызываем finalizer - тем самым возвращаем данные
```
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
  Dataset<Row> dataset = dataExtractor.load(pathToData, context);
  List<SparkTransformation> transformations = transformationChain.get(method);

  for (SparkTransformation transformation : transformations) {
    dataset = transformation.transform(dataset);
  }

  Finalizer finalizer = finalizerMap.get(method);

  Object retVal = finalizer.doAction(dataset);
  return retVal;
}
```

## Создаем класс для сканирования имплементаций `SparkRepository` и регистрации бинов
- Регистрируем бины sparkSession & sparkContext
  - Вытаскиваем из `application.properties` название приложения
  - Создаем бины
  - Регистрируем бины
- Сканируем все имплементации `SparkRepository` и создаем для них прокси и регистрируем бины
  - Получаем значение из файла окружения
  - Создаем класс для сканирования пакетов
  - Получаем все классы, которые реализуют интерфейс `SparkRepository`
  - Итерируемся по всем имплементациям
  - Создаем прокси для каждой имплементации
  - регистрируем бин

```
public class SparkApplicationContextInitializer  implements ApplicationContextInitializer {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    registerSparkBean(applicationContext);

    // Получаем значение из файла окружения
    String packagesToScan = applicationContext.getEnvironment().getProperty("spark.packages-to-scan");
    // Создаем класс для сканирования пакетов
    Reflections scanner = new Reflections(packagesToScan);
    // Получаем все классы, которые реализуют интерфейс `SparkRepository`
    Set<Class<? extends SparkRepository>> sparkRepoInterfaces = scanner.getSubTypesOf(SparkRepository.class);
    // Итерируемся по всем имплементациям
    sparkRepoInterfaces.forEach(sparkRepoInterface -> {
      // Создаем прокси для каждой имплементации
      Object golem = Proxy.newProxyInstance(
          sparkRepoInterface.getClassLoader(),
          new Class[]{sparkRepoInterface},
          invocationHandler
      );

      // регистрируем бин
      applicationContext
          .getBeanFactory()
          .registerSingleton(
              Introspector.decapitalize(sparkRepoInterface.getSimpleName()), golem
          );
    });
  }

  private void registerSparkBean(ConfigurableApplicationContext applicationContext) {
    // Вытаскиваем из `application.properties` название приложения
    String appName = applicationContext.getEnvironment().getProperty("spark.app-name");
    // Создаем бины
    SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate();
    JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
    // Регистрируем бины
    applicationContext.getBeanFactory().registerSingleton("sparkContext", sparkContext);
    applicationContext.getBeanFactory().registerSingleton("sparkSession", sparkSession);
  }
}
```

## Создаем `SparkInvocationHandlerFactory`
1. Нам понадобятся вспомогательные методы для получения модели и для получения наименования полей
```
  private Class<?> getModelClass(Class<? extends SparkRepository> repoInterface) {
    // Берем первый параметр интерфейса и кастим его в `ParameterizedType`
    ParameterizedType genericInterface = (ParameterizedType) repoInterface.getGenericInterfaces()[0];
    // Получаем аргументы
    Class<?> modelClass = (Class<?>) genericInterface.getActualTypeArguments()[0];
    return modelClass;
  }

  private Set<String> getFieldNames(Class<?> modelClass) {
    // Получаем все свойства класса
    return Arrays.stream(modelClass.getDeclaredFields())
        // Отфильтровываем все, которые имеют аннотацию @Transient
        .filter(field -> !field.isAnnotationPresent(Transient.class))
        // Отфильтровываем все свойтсва, которые являются коллекциями
        .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
        // Получаем название филдов
        .map(Field::getName)
        // Собираем все в set чтобы хранить только уникальные значения
        .collect(Collectors.toSet());

  }
```

2. Для работы `SparkInvocationHandlerFactory` нам понадобятся
- private DataExtractorResolver dataExtractorResolver; - для получения данных
- private Map<String, TransformationSpider> spiderMap; - для хранения всех возможных имплементаций для парсинга названия метода в команды преобразования данных
- private Map<String, Finalizer> finalizerMap; - для хранения "финальных" методов
- private ConfigurableApplicationContext context; - контекст

3. Метод `create` в `SparkInvocationHandlerFactory`
```
public SparkInvocationHandler create (Class<? extends SparkRepository> sparkRepoInterface) {
  // Получаем название класса
  Class<?> modelClass = getModelClass(sparkRepoInterface);
  // Получаем из аннотации Source значение value, которое будет являться путем до файла
  String pathToData = modelClass.getAnnotation(Source.class).value();
  // получаем все названия полей из модели
  Set<String> fieldNames = getFieldNames(modelClass);
  // Создаем DataExtractor
  DataExtractor dataExtractor = dataExtractorResolver.resolve(pathToData);

  // будет хранить в себе имплементации "финальных" методов
  Map<Method, Finalizer> method2Finalizer = new HashMap<>();
  // будет хранить в себе список методов трансформаций
  Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();

  Method[] methods = sparkRepoInterface.getMethods();
  /* method -> List<User> findByNameOfGrandmotherContainsAndAgeLessThanOrderByAgeAndNameSave
  * field names -> NameOfGrandmother, Age, Age
  * Strategy name -> findBy, OrderBy
  * FilterTransformation -> Contains, LessThan
  * Finalizer -> Save
  */
  for (Method method : methods) {
    TransformationSpider currentSpider = null;
    String name = method.getName();
    /* findByNameOfGrandmotherContainsAndAgeLessThanOrderByAgeAndNameSave превратится
    * {"find", "by", "of", "grandmother", "contains" ..etc}
    */
    List<String> methodWords = WordsMatcher.toWordsByJavaConvention(name);
    List<SparkTransformation> transformations = new ArrayList<>();

    while (methodWords.size() > 1) {
      // 1й список -> название стратегий таких как findBy, OrderBy
      // 2й список -> список слов, на который было распаршено имя метода
      String spiderName = WordsMatcher
          .findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
      if (!spiderName.isEmpty()) {
        currentSpider = spiderMap.get(spiderName);
      }

      transformations.add(currentSpider.getTransformation(methods));
    }

    transformationChain.put(method, transformations);
    String finalizerName = "collect";
    if (methodWords.size() == 1) {
      finalizerName = methodWords.get(0);
    }
    method2Finalizer.put(method, finalizerMap.get(finalizerName));
  }

  return SparkInvocationHandler.builder()
      .modelClass(modelClass)
      .pathToData(pathToData)
      .dataExtractor(dataExtractor)
      .transformationChain(transformationChain)
      .finalizerMap(method2Finalizer)
      .context(context)
      .build();
}
```

## Создадим имплментации Finalizer
```
public class CountFinalizer implements Finalizer {
  @Override
  public Object doAction(Dataset<Row> dataset, Class<?> model) {
    return dataset.count();
  }
}
```

```
public class CollectFinalizer implements Finalizer {
  @Override
  public Object doAction(Dataset<Row> dataset, Class<?> model) {
    Encoder<?> encoder = Encoders.bean(model);
    return dataset.as(encoder).collectAsList();
  }
}
```

## Как инджектить все зависимости
- Можно создать спринг контекст внутри спринга
- Данный контекст будет являться временным и удаляться после того как все необходимые действия будут произведены
- В связи с чем мы можем сделать из классов бины и пользоваться `@Autowired`

## Передача аргументов для фильтров

Для каждого фильтра нам необходимо передавать необходимые аргументы</br>
Для этого нам понадобится изменить структуру `transformationChain`</br>
Будем использовать `Tuple`</br>
`private Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain;`</br>
За каждым названием методом будет закреплен лист, в котором будет хранится Tuple2, состоящий из трансформации и листа с аргументами</br></br>

Трансформация теперь возвращает `Tuple2<SparkTransformation, List<String>>`
```
@Component("findBy")
@RequiredArgsConstructor
public class FindByTransformationSpider implements TransformationSpider {

  private final Map<String, FilterTransformation> filterTransformationMap;

  @Override
  public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
    List<String> columnNames = List.of(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords));
    String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
    return new Tuple2<>(filterTransformationMap.get(filterName), columnNames);
  }
}
```

SparkInvocationHandler теперь так же принимает Tuple, который был получен в ходе работы трансформаций
```
@Builder
public class SparkInvocationHandler implements InvocationHandler {
  // Класс модели (1)
  private Class<?> modelClass;

  // Ссылка на данные для данной модели(1)
  private String pathToData;

  // Класс для извлечения данных(1)
  private DataExtractor dataExtractor;

  // Трансформации (у каждого метода свой список)
  private Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain;

  // Терминальная операция (у каждого метода свой список)
  private Map<Method, Finalizer> finalizerMap;

  private ConfigurableApplicationContext context;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Dataset<Row> dataset = dataExtractor.load(pathToData, context);
    List<Tuple2<SparkTransformation, List<String>>> tuple2List = transformationChain.get(method);

    for (Tuple2<SparkTransformation, List<String>> tuple : tuple2List) {
      SparkTransformation sparkTransformation = tuple._1();
      List<String> columnNames = tuple._2();
      dataset = sparkTransformation.transform(dataset, columnNames, new OrderedBag<>(args));
    }

    Finalizer finalizer = finalizerMap.get(method);

    Object retVal = finalizer.doAction(dataset, modelClass);
    return retVal;
  }
}
```

## Сортировка

1. Реализовать Класс `SortTransformation`
```
public class SortTransformation implements SparkTransformation {
  @Override
  public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
    return dataset
        .orderBy(
            columnNames.get(0),
            columnNames.stream().skip(1).toArray(String[]::new)
        );
  }
}
```

2. Реализовать спайдера сортировки
```
@Component("orderBy")
public class OrderByTransformationSpider implements TransformationSpider {
  @Override
  public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNamed) {
    // Сюда придет как минимум одно слово для сортировки
    String sortColumn = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNamed, methodWords);

    List<String> additional = new ArrayList<>();
    while (!methodWords.isEmpty() && methodWords.get(0).equalsIgnoreCase("and")) {
      // Убираем `and`
      methodWords.remove(0);
      // кладем слово, следующее за `and`
      additional.add(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNamed, methodWords));
    }

    additional.add(0 , sortColumn);

    return new Tuple2<>(new SortTransformation(), additional);
  }
}
```

3. Создаем новый метод в интерфейсе с сортировкой
```
public interface CriminalRepo extends SparkRepository<Criminal> {
  List<Criminal> findByNumberGreaterThanOrderByNumber(int min);
}
```

## Метод Contains


