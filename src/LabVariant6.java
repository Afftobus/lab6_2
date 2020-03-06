import java.util.ArrayList;
import java.util.List;

public class LabVariant6 {
  // собственно, данные над которыми мы будем потом работать
  private List<Integer> data = new ArrayList<>();

  //держим всегда сумму. Легче хранить её отдельно, чем делать потом на вычислении 2 прохода
  private Integer summ = 0;

  public LabVariant6() {
    // пустой конструктор, просто создаёт новый объект, в который мы потом будем пихать данные по кускам
  }

  public LabVariant6(List<Integer> input) {
    // конструктор, в который мы сразу пихаем все данные и вычисляем одним куском
    // копируем данные в новый объект
    data = new ArrayList<>(input);

    // сразу считаем сумму. Просто так будет потом удобнее
    for (Integer x : data) {
      summ += x;
    }
  }

  public void put(Integer value) {
    // запихиваем в данные
    data.add(value);

    // тоже сразу считаем сумму.
    summ += value;
  }

  public ResultType count() {
    if (data.size() < 3) {
      // ваще нет смысла что-то делать, нам нечего считать
      return ResultType.NO_DATA_TO_COUNT_RESULT;
    }

    // логично, что если у нас количество чисел, входящих в одну из категорий,
    // уже больше половины  от общего числа чисел, то дальше считать уже не надо. У нас уже есть ответ.
    // поэтому сразу находим минимальное число, при котором ответ найден
    int half = data.size() / 2;

    int greaterThanAverage = 0;
    int smallerThanAverage = 0;

    // собственно, среднее значение
    float average = (float) summ / data.size();

    for (Integer x : data) {
      // обходим массив данных и инкрементим соответствующие счетчики
      // понятное дело, если число равно среднему, то никуда его не причисляем
      if (x > average) {
        greaterThanAverage++;
      }

      if (x < average) {
        smallerThanAverage++;
      }

      // проверяем, не превысил ли один из счетчиков середины + 1
      if (greaterThanAverage > half || smallerThanAverage > half) {
        // если такое есть, то прерываем цикл
        break;
      }
    }

    // теперь сформируем и вернём ответ
    if (greaterThanAverage > smallerThanAverage) {
      return ResultType.GREATER_IS_MORE;
    }

    if (smallerThanAverage > greaterThanAverage) {
      return ResultType.SMALLER_IS_MORE;
    }

    return ResultType.EQUAL;
  }


}
