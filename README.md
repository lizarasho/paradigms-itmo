# Paradigms-ITMO

## 1. Queue
#### Очередь на массиве
 * Реализовать методы
    * `push` – добавить элемент в начало очереди
    * `peek` – вернуть последний элемент в очереди
    * `remove` – вернуть и удалить последний элемент из очереди
#### Очередь на связном списке
 * Добавить в интерфейс очереди и реализовать методы
     * `filter(predicate)` – создать очередь, содержащую элементы, удовлетворяющие
            [предикату](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)
    * `map(function)` – создать очередь, содержащую результаты применения
            [функции](https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)
 * Исходная очередь должна остаться неизменной
 * Тип возвращаемой очереди должен соответствовать типу исходной очереди
 * Взаимный порядок элементов должен сохраняться
 * Дублирования кода быть не должно
* * *
* * * 

## 2. Expression Parser
#### Вычисление выражений
 * Реализовать интерфейсы `Expression`, `DoubleExpression`, `TripleExpression`
#### Разбор выражений
 * Класс `ExpressionParser` должен реализовывать интерфейс `Parser`
#### Обработка ошибок
 * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс
        `TripleExpression`
 * Нельзя использовать типы `long` и `double`
 * Нельзя использовать методы классов `Math` и `StrictMath`
#### Вычисление в различных типах
Модификации
 * *Базовая*
    * Класс `GenericTabulator` должен реализовывать интерфейс
      `Tabulator` и
      сроить трехмерную таблицу значений заданного выражения.
        * `mode` — режим вычислений:
           * `i` — вычисления в `int` с проверкой на переполнение;
           * `d` — вычисления в `double` без проверки на переполнение;
           * `bi` — вычисления в `BigInteger`.
        * `expression` — выражение, для которого надо построить таблицу;
        * `x1`, `x2` — минимальное и максимальное значения переменной `x` (включительно)
        * `y1`, `y2`, `z1`, `z2` — аналогично для `y` и `z`.
        * Результат: элемент `result[i][j][k]` должен содержать
          значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`.
          Если значение не определено (например, по причине переполнения),
          то соответствующий элемент должен быть равен `null`.
 * *AsmUfb*
    * Дополнительно реализовать унарные операции:
        * `abs` — модуль числа, `abs -5` равно 5;
        * `square` — возведение в квадрат, `square 5` равно 25.
    * Дополнительно реализовать бинарную операцию (максимальный приоритет):
        * `mod` — взятие по модулю, приоритет как у умножения (`1 + 5 mod 3` равно `1 + (5 mod 3)` равно `3`).
    * Дополнительно реализовать поддержку режимов:
        * `u` — вычисления в `int` без проверки на переполнение;
        * `f` — вычисления в `float` без проверки на переполнение;
        * `b` — вычисления в `byte` без проверки на переполнение.
 * *Ls*
    * Дополнительно реализовать поддержку режимов:
        * `l` — вычисления в `long` без проверки на переполнение;
        * `s` — вычисления в `short` без проверки на переполнение.
* * *
* * * 

## 3. Parser to DNF
#### Зачет за первый семестр
 * Реализовать перевод из произвольного бинарного математического выражения, содержащего операторы `or` (|), `and` (&) и `not` (~), в  *дизъюнктивную нормальную форму*.
* * *
* * * 

## 4. Markdown to HTML
1. Разработайте конвертер из Markdown-разметки в HTML.
2. Конвертер должен поддерживать следующие возможности:
    * Абзацы текста разделяются пустыми строками.
    * Элементы строчной разметки: выделение (* или _), сильное выделение (** или __ ), зачеркивание (--), код ( \`)
    * Заголовки (# * уровень заголовка)
3. Конвертер должен называться Md2Html и принимать два аргумента: название входного файла с Markdown-разметкой и название выходного файла c HTML-разметкой. Оба файла должны иметь кодировку UTF-8.

Модификации
 * *Базовая*
 * *Link*
    * Добавьте поддержку ```[ссылок с _выделением_](https://kgeorgiy.info)```:
        ```&lt;a href='https://kgeorgiy.info'>ссылок с &lt;em>выделением&lt;/em>&lt;/a>```
 * *Underline*
    * Добавьте поддержку `++подчеркивания++`: `<u>подчеркивания</u>`
 * *Image*
    * Добавьте поддержку ```![картинок](http://www.ifmo.ru/images/menu/small/p10.jpg)```:
        ```&lt;img alt='картинок' src='http://www.ifmo.ru/images/menu/small/p10.jpg'&gt;```
 * *Mark*
    * Добавьте поддержку `~выделения цветом~`: `<mark>выделения цветом</mark>`
 * *All*
    * Добавьте поддержку всех вышеперечисленных модификаций
* * *
* * * 

## 5. Functional Javascript
#### Функциональные выражения на JavaScript
1. Разработайте функции `cnst`, `variable`, `add`, `subtract`, `multiply`, `divide`, negate для вычисления выражений с одной переменной.
2. *Усложненный вариант.* Требуется написать функцию `parse`, осуществляющую разбор выражений, записанных в обратной польской записи.
3. При выполнение задания следует обратить внимание на:
   * Применение функций высшего порядка.
   * Выделение общего кода для бинарных операций.
   
Модификации
 * *Базовая*
    * Код должен находиться в файле `functionalExpression.js`.
 * *OneIffAbs*. Дополнительно реализовать поддержку:
    * переменных: `y`, `z`;
    * констант:
        * `one` — 1;
        * `two` — 2;
    * операций:
        * `abs` — абсолютное значение, `-2 abs` равно 2;
        * `iff` — условный выбор:
            если первый аргумент неотрицательный,
            вернуть второй аргумент,
            иначе вернуть первый третий аргумент.
            * `iff one two 3` равно 2
            * `iff -1 -2 -3` равно -3
            * `iff 0 one two` равно 1; 
* * *
* * * 

## 6. Object Javascript
#### Объектные выражения на JavaScript
1. Разработайте классы `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` для представления выражений с одной переменной.
    * Пример описания выражения 2x-3:
                        `let expr = new Subtract(new Multiply(new Const(2), new Variable("x")), new Const(3));`
    * Метод `evaluate(x)` должен производить вычисления вида: При вычислении такого выражения вместо каждой переменной подставляется значение x, переданное в качестве параметра функции evaluate (на данном этапе имена переменных игнорируются).
    * Метод `toString()` должен выдавать запись выражения в обратной польской записи. Например, `expr.toString()` должен выдавать `2 x * 3 -`.
2. *Усложненный вариант.* Метод `diff("x")` должен возвращать выражение, представляющее производную исходного выражения по переменной x. Например, `expr.diff("x")` должен возвращать выражение, эквивалентное `new Const(2)`.
3. *Бонусный вариант.* Требуется написать метод `simplify()`, производящий вычисления константных выражений. Например,
`parse("x x 2 - * 1 *").diff("x").simplify().toString()` должно возвращать `x x 2 - +`.
4. При выполнение задания следует обратить внимание на:
    * Применение инкапсуляции.
    * Выделение общего кода для операций.
    
 Модификации
 * *Базовая*
    * Код должен находиться в файле `objectExpression.js`.
 * *ArcTan*. Дополнительно реализовать поддержку:
    * функций:
        * `ArcTan` (`atan`) — арктангенс, `1256 atan` примерно равно 1.57;
        * `ArcTan2` (`atan2`) — арктангенс, `841 540 atan2` примерно равно 1;
#### Обработка ошибок на JavaScript
1. Добавьте в предыдущее домашнее задание функцию `parsePrefix(string)`, разбирающую выражения, задаваемые записью вида `(- (* 2 x) 3)`. Если разбираемое выражение некорректно, метод `parsePrefix` должен бросать человеко-читаемое сообщение об ошибке.
2. Добавьте в предыдущее домашнее задание метод `prefix()`, выдающий выражение в формате, ожидаемом функцией `parsePrefix`.
3. При выполнение задания следует обратить внимание на:
    * Применение инкапсуляции.
    * Выделение общего кода для бинарных операций.
    * Обработку ошибок.
    * Минимизацию необходимой памяти.
    
Модификации
 * *Базовая*
    * Код должен находиться в файле `objectExpression.js`.
 * *PrefixSumexpSoftmax*, *PostfixSumexpSoftmax*. Дополнительно реализовать поддержку:
    * выражений в постфиксной записи: `(2 3 +)` равно 5
    * операций произвольного числа аргументов:
        * `Sumexp` (`sumexp`) — сумма экспонент, `(8 8 9)` примерно равно 14065;
        * `Softmax` (`softmax`) — softmax первого аргумента, `(softmax 1 2 3)` примерно равно 9;
* * *
* * * 

## 7. Linear algebra Clojure
#### Линейная алгебра на Clojure
1. Разработайте функции для работы с объектами линейной алгебры, которые представляются следующим образом:
    * скаляры – числа;
    * векторы – векторы чисел;
    * матрицы – векторы векторов чисел.
2. Функции над векторами:
    * v+/v-/v* – покоординатное сложение/вычитание/умножение;
    * scalar/vect – скалярное/векторное произведение;
    * v*s – умножение на скаляр.
3. Функции над матрицами:
    * m+/m-/m* – поэлементное сложение/вычитание/умножение;
    * m*s – умножение на скаляр;
    * m*v – умножение на вектор;
    * m*m – матричное умножение;
    * transpose – траспонирование;
4. *Усложненный вариант.*
    * Ко всем функциям должны быть указаны контракты. Например, нельзя складывать вектора разной длины.
    * Все функции должны поддерживать произвольное число аргументов. Например (`v+ [1 2] [3 4] [5 6]`) должно быть равно `[9 12]`.
5. При выполнение задания следует обратить внимание на:
    * Применение функций высшего порядка.
    * Выделение общего кода для операций.   
6. Код должен находиться в файле `linear.clj`. 
    
Модификации
 * *Tensor*
    * Назовем _тензором_ многомерную прямоугольную таблицу чисел.
    * Добавьте операции поэлементного сложения (`t+`),
        вычитания (`t-`) и умножения (`t*`) тензоров.
        Например, `(s+ [[1 2] [3 4]] [[5 6] [7 8]])` должно быть равно `[[6 8] [10 12]]`.
* * *
* * * 

## 8. Functional Clojure
#### Функциональные выражения на Clojure
1. Разработайте функции `constant`, `variable`, `add`, `subtract`, `multiply` и `divide` для представления арифметических выражений.
    * Пример описания выражения 2x-3:
`(def expr
  (subtract
    (multiply
      (constant 2)
      (variable "x"))
    (constant 3)))`
    * Выражение должно быть функцией, возвращающей значение выражение при подстановке элементов, заданных отображением. Например, `(expr {"x" 2})` должно быть равно 1.
2. Разработайте разборщик выражений, читающий выражения в стандартной для Clojure форме. Например,
`(parseFunction "(- (* 2 x) 3)")`
должно быть эквивалентно `expr`.
3. *Усложненный вариант.* Функции `add`, `subtract`, `multiply` и `divide` должны принимать произвольное число аргументов. Разборщик так же должен допускать произвольное число аргументов для `+`, `-`, `*`.
4. При выполнение задания следует обратить внимание на:
    * Выделение общего кода для операций.
5. Код должен находиться в файле `expression.clj`.    
    
Модификации
 * *SquareSqrt*. Дополнительно реализовать поддержку:
    * унарных операций:
        * `square` (`square`) – возведение в квадрат, `(square 3)` равно 9;
        * `sqrt` (`sqrt`) – извлечение квадратного корня из модуля аргумента, `(sqrt -9)` равно 3.
 * *MedAvg*. Дополнительно реализовать поддержку:
    * операций:
        * `med` (`med`) – медиана, `(med 1 2 6)` равно 2;
        * `avg` (`max`) – среднее, `(min 1 2 6)` равно 3;
* * *
* * * 
