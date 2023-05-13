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
