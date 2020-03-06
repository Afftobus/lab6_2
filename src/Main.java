import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner in = new Scanner(System.in);

        // бесконечный цикл, выход из которого производится только по условию
        // т.к. мы будем прерывать его изнутри структуры switch/case, нужно его отметить меткой
        loop:
        while (true) {
            // выведем меню
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Режим работы");
            System.out.println("1) ввод массива данных");
            System.out.println("2) ввод по одному числу с выводом ответа после каждого");
            System.out.println("3) прогон случайными числами, вводимыми пачкой");
            System.out.println("4) прогон случайными числами, вводимыми по одному");
            System.out.println("5) прогон данных, соответствующих \"лучшему случаю\"");
            System.out.println("0) выход");
            System.out.println("выберите пункт меню: ");

            // прочитаем то, что ввёл пользователь и попытаемся привести это к числу
            String input = in.nextLine();
            int num = Integer.parseInt(input);

            // в зависимости от выбора включаем нужную функцию
            switch (num) {
                case 1:
                    manualEnterArray(in);
                    break;

                case 2:
                    manualEnterOneByOne(in);
                    break;

                case 3:
                    randomlEnterArray(in);
                    break;

                case 4:
                    randomEnterOneByOne(in);
                    break;

                case 5:
                    bestCase(in);
                    break;

                default:
                    System.out.println("Нет такого варианта");
                    break;

                // выход
                case 0:
                    break loop;
            }
        }

        // после завершения работы закрываем инпут и выводим сообщение
        in.close();
        System.out.println("Goodbye, cruel world...");
    }

    // здесь мы даём человеку ввести сразу всю кучу данных и выведем результат расчетов
    private static void manualEnterArray(Scanner in) {
        // приглашение
        System.out.println("------- ВВОД ДАННЫХ КУЧЕЙ РУКАМИ ----------------------------------");
        System.out.print("Введите массив чисел, разделённых запятой: ");
        // ожидание ввода
        String input = in.nextLine();

        // приведение строки с запятыми к массиву чисел
        String[] elements = input.split(",");
        List<Integer> data = new ArrayList<>();

        for (String el : elements) {
            data.add(Integer.parseInt(el.trim()));
        }

        // создание экземпляра класса-вычислителя
        LabVariant6 counter = new LabVariant6(data);
        // расчет, преобразование и вывод
        System.out.println(toText(counter.count()));
    }

    // пользователь добавляет по одному числу и после добавления каждого производится перерасчет
    private static void manualEnterOneByOne(Scanner in) {
        // приглашение
        System.out.println("------- ВВОД ДАННЫХ ПО ОДНОМУ РУКАМИ ------------------------------");
        System.out.println("Вводите числа, нажимайте Enter, для окончания введите пустую строку: ");

        // создание экземпляра класса-вычислителя с пустыми данными
        LabVariant6 counter = new LabVariant6();

        // ожидание ввода в бесконечном цикле
        while (true) {
            String input = in.nextLine();

            // если введена пустая строка, то выходим из функции вообще
            if (input.equals("")) {
                return;
            }

            // добавляем элемент в класс-вычислитель
            counter.put(Integer.parseInt(input));

            // расчет, преобразование и вывод
            System.out.println(toText(counter.count()));
        }
    }

    // сгенерим много данных, запихнём кучей в обработчик и повторим много раз
    private static void randomlEnterArray(Scanner in) {
        System.out.println("------- РАНДОМНАЯ ГЕНЕРАЦИЯ КУЧИ ДАННЫХ И ОБРАБОТКА ПАЧКОЙ --------");

        System.out.println("Количество рандомных чисел в вычисляемом массиве: ");
        String input = in.nextLine();
        int count = Integer.parseInt(input);

        System.out.println("Максимальное число: ");
        input = in.nextLine();
        int max = Integer.parseInt(input);

        System.out.println("Количество прогонов: ");
        input = in.nextLine();
        int iterationsCount = Integer.parseInt(input);

        // определим все переменные здесь
        List<Integer> data;
        LabVariant6 counter;
        int i;
        long start, end;

        // iterationsCount раз повторим вычисление
        while (iterationsCount-- > 0) {
            data = new ArrayList<>();
            i = count;

            // count раз добавим рандомное число в массив данных
            while (i-- > 0) {
                data.add((int) (Math.random() * max));
            }

            // создание экземпляра класса-вычислителя
            counter = new LabVariant6(data);

            // засечем время начала обработки
            start = System.nanoTime();

            // посчитаем
            ResultType result = counter.count();

            // засечем время окончания обработки
            end = System.nanoTime();

            // выведем инфу о том, что у нас получилось
            System.out.println(toText(result));
            System.out.println("Расчитано за " + (end - start) / 1000 + "мкс");
        }
    }

    // суём рандомные числа по одному и каждый рас пересчитываем
    private static void randomEnterOneByOne(Scanner in) {
        System.out.println("------- РАНДОМНАЯ ГЕНЕРАЦИЯ ДАННЫХ И ОБРАБОТКА ПО ОДНОМУ ---");

        System.out.println("Количество рандомных чисел в вычисляемом массиве: ");
        String input = in.nextLine();
        int count = Integer.parseInt(input);

        System.out.println("Максимальное число: ");
        input = in.nextLine();
        int max = Integer.parseInt(input);

        System.out.println("Количество прогонов: ");
        input = in.nextLine();
        int iterationsCount = Integer.parseInt(input);


        // определим переменную для класса-вычислителя
        LabVariant6 counter;

        // определим переменные
        long totalCountTime = 0;
        int i;

        // iterationsCount раз повторим вычисление
        while (iterationsCount-- > 0) {
            i = count;

            // создание экземпляра класса-вычислителя
            counter = new LabVariant6();

            // в цикле суём данные по одному и пересчитываем, засекая время расчета
            while (i-- > 0) {
                counter.put((int) (Math.random() * max));
                long start = System.nanoTime();
                counter.count();
                long end = System.nanoTime();
                totalCountTime += (end - start);
            }

            // здесь непонятно какой результат показывать, поэтому просто выдаю время, за которое всё обрабботалось
            System.out.println("Расчитано за " + (totalCountTime) / 1000 + "мкс");
        }

    }

    private static void bestCase(Scanner in) {
        System.out.println("------- ПРОГОН НА ДАННЫХ СООТВЕТСТВУЮЩИХ ЛУЧШЕМУ СЛУЧАЮ -----------");

        System.out.println("Количество чисел в вычисляемом массиве: ");
        String input = in.nextLine();
        int count = Integer.parseInt(input);

        System.out.println("Количество прогонов: ");
        input = in.nextLine();
        int iterationsCount = Integer.parseInt(input);

        // определим все переменные здесь
        List<Integer> data;
        LabVariant6 counter;
        int i;
        long start, end;

        // iterationsCount раз повторим вычисление
        while (iterationsCount-- > 0) {
            data = new ArrayList<>();
            i = count;

            // count-1 раз добавим  единицу в массив данных
            while (i-- > 1) {
                data.add(1);
            }

            // добавим в конец максимальное значение инта
            data.add(Integer.MAX_VALUE);

            // создание экземпляра класса-вычислителя
            counter = new LabVariant6(data);

            // засечем время начала обработки
            start = System.nanoTime();

            // посчитаем
            ResultType result = counter.count();

            // засечем время окончания обработки
            end = System.nanoTime();

            // выведем инфу о том, что у нас получилось
            System.out.println(toText(result));
            System.out.println("Расчитано за " + (end - start) / 1000 + "мкс");
        }
    }


    // метод для формирования человеческой строки в зависимости от значения результата
    private static String toText(ResultType resultType) {
        switch (resultType) {
            case EQUAL:
                return "Монохуйственно";
            case NO_DATA_TO_COUNT_RESULT:
                return "Хуйзнает вообще";
            case GREATER_IS_MORE:
                return "Тех что больше среднего - больше, тех, что меньше - меньше";
            case SMALLER_IS_MORE:
                return "Тех что меньше среднего - больше, тех, что больше - меньше";

            default:
                return "";
        }
    }
}
