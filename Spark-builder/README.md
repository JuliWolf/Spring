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
