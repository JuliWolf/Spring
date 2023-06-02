# Junit
## Что Такое Junit тестирование

- Фреймворк для тестирования работы классов и его методов

## Как Junit работает

- раннер Junit запускает тесты и выводит их результат
- Junit ожидает конкретный результат выполнения и в случае несоотвествия выкидывается ошибка

## Пример теста
1. Создаем класс, который будем проверять
```
public class AddExample {
  public int addMethod (int a, int b, int c) {
    int sum = a + b + c;
    return sum;
  }
}
```

2. Создаеми класс для проверки
- Внутри метода создаем экземпляр класса, который необходимо протестировать
- assertEquals - означает, что мы ожидаем одиннаковый результат
```
public class AddExampleTest {
  @Test
  public void addMethodTest () {
    AddExample addExample = new AddExample();
    int actualResult = addExample.addMethod(10, 10, 10);
    int expectedResult = 20;
    Assertions.assertEquals(expectedResult, actualResult);
  }
}
```

## Тестирование метода с сервисом
1. В классе добавляем новый метод, данные для которого приходят из сервиса
```
public class AddExample {
  private CalculateService calculateService;

  public void setCalculateService(CalculateService calculateService) {
    this.calculateService = calculateService;
  }

...

  public int getSumService () {
    int sum = 0;
    int a[] = calculateService.retrieveCalculateSum();

    for (int i : a) {
      sum += i;
    }

    return sum;
  }
}
```

2. В папке test создаем папку stub и создаем там класс, который будет имплментить интерфейс CalculateService
```
public class CalculateServiceStub implements CalculateService {
  @Override
  public int[] retrieveCalculateSum() {
    return new int[] { 1, 2, 3 };
  }
}
```

3. Создаем тест
```
public class AddExampleStubTest {

  @Test
  public void getSumServiceTest () {
    AddExample addExample = new AddExample();
    addExample.setCalculateService(new CalculateServiceStub());
    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }
}
```

## Mocking services
1. Используем статический метод `mock` для создания класса реализующего интерфейс `CalculateService`
2. Используя статический метод `when` добавляем возвращаемые данные конкретному методу
```
public class AddExampleMockTest {

  @Test
  public void getSumServiceTest () {
    AddExample addExample = new AddExample();

    // Создаем мок для сервиса
    CalculateService calculateService = mock(CalculateService.class);
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1,2,3});

    addExample.setCalculateService(calculateService);

    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
  }
}
```

## Рефакторинг используя аннотации

1. Для использования аннотаций Mockito необходимо добавить аннотацию всему классу с тестами
```
// Junit 5
@ExtendWith(MockitoExtension.class)
public class AddExampleMockTest {...}

// Junit 4
@RunWith(MockitoJUnitRunner.class)
public class AddExampleMockTest {...}
```

2. InjectMocks
- Содаем инстанс класса и инджектит в него моги, созданные с помощью аннотации Mock
```
@InjectMocks
AddExample addExample;
```

3. Mock
- Создает класс, в который можно будет добавить моковые данные
```
@Mock
CalculateService calculateService;
```

4. Код, который выполняется перед методами
```
@Before
public void before () {
addExample.setCalculateService(calculateService);
}
```

5. Результат
```
@Test
public void getSumServiceTest () {
    // подкладываем данные в сервис
    when(calculateService.retrieveCalculateSum())
        .thenReturn(new int[] {1,2,3});
    
    int actualResult = addExample.getSumService();
    int expectedResult = 6;
    Assertions.assertEquals(expectedResult, actualResult);
}
```

## Проверка нескольких вариантов в тесте
- Используем 2 раза метод `thenReturn`
- Для первого раза вернет 10
- Для второго 20
```
public class SampleList {
  @Test
  public void listSizeTest () {
    List mock = mock(List.class);
    when(mock.size()).thenReturn(10).thenReturn(20);
    assertEquals(10, mock.size());
    assertEquals(20, mock.size());
  }
}
```

## Сводка аннотаций Junit
| Назначение                                           | Junit4       | Junit5       |
|------------------------------------------------------|--------------|--------------|
| Обозначение теста                                    | @Test        | @Test        |
| Выполнять до всех методов тестов в текущем классе    | @BeforeClass | @BeforeAll   |
| Выполнять после всех методов тестов в текущем классе | @AfterClass  | @AfterAll    |
| Выполнять перед запуском каждого метода              | @Before      | @BeforeEach  |
| Выполнять после выполнения каждого метода            | @After       | @AfterEach   |
| Задизейблить метод/класс                             | @Ignore      | @Disabled    |
| Тестировать фабрику для динамических тестов          | NA           | @TestFactory |
| Связанные тесты (последовательные)                   | NA           | @Nested      |
| Добавить тег и фильтры                               | NA           | @Tag         |
| Зарегистрировть кастомное расширение                 | NA           | @ExtendWith  |

