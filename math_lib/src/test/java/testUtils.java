import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import ru.esstu.maven.MathUtils;

import static org.junit.jupiter.api.Assertions.*;
class testUtils {

    @Test
    @DisplayName("Тест факториала:")
    void testFactorial() {
        assertEquals(1, MathUtils.factorial(0), "0! = 1");
        assertEquals(1, MathUtils.factorial(1), "1! = 1");
        assertEquals(2, MathUtils.factorial(2), "2! = 2");
        assertEquals(6, MathUtils.factorial(3), "3! = 6");
        assertEquals(24, MathUtils.factorial(4), "4! = 24");
        assertEquals(120, MathUtils.factorial(5), "5! = 120");
        assertEquals(720, MathUtils.factorial(6), "6! = 720");
        assertEquals(5040, MathUtils.factorial(7), "7! = 5040");
    }

    @Test
    @DisplayName("Тест факториала: отрицательное число -> исключение")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> MathUtils.factorial(-1),
                "Отрицательное число должно вызывать исключение");
    }

    //тесты нод
    @ParameterizedTest
    @CsvSource({
            "12, 8, 4",
            "17, 19, 1",
            "100, 10, 10",
            "0, 5, 5",
            "5, 0, 5",
            "0, 0, 0",
            "-12, 8, 4",
            "12, -8, 4",
            "-12, -8, 4",
            "54, 24, 6"
    })
    @DisplayName("Тест НОД с разными значениями")
    void testGcd(int a, int b, int expected) {
        assertEquals(expected, MathUtils.gcd(a, b),
                String.format("НОД(%d, %d) должно быть %d", a, b, expected));
    }

    //тесты нок

    @ParameterizedTest
    @CsvSource({
            "12, 8, 24",
            "17, 19, 323",
            "4, 6, 12",
            "5, 7, 35",
            "-12, 8, 24",
            "12, -8, 24",
            "6, 8, 24"
    })
    @DisplayName("Тест НОК с разными значениями")
    void testLcm(int a, int b, int expected) {
        assertEquals(expected, MathUtils.lcm(a, b),
                String.format("НОК(%d, %d) должно быть %d", a, b, expected));
    }

    @Test
    @DisplayName("Тест НОК: ноль -> исключение")
    void testLcmZero() {
        assertThrows(ArithmeticException.class,
                () -> MathUtils.lcm(0, 5),
                "НОК с нулем должно вызывать исключение");

        assertThrows(ArithmeticException.class,
                () -> MathUtils.lcm(5, 0),
                "НОК с нулем должно вызывать исключение");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29})
    @DisplayName("Тест простых чисел")
    void testPrimeNumbers(int n) {
        assertTrue(MathUtils.isPrime(n), n + " должно быть простым");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25})
    @DisplayName("Тест составных чисел")
    void testCompositeNumbers(int n) {
        assertFalse(MathUtils.isPrime(n), n + " не должно быть простым");
    }

    @Test
    @DisplayName("Тест отрицательных чисел")
    void testNegativeNumbers() {
        assertFalse(MathUtils.isPrime(-2), "Отрицательные числа не простые");
        assertFalse(MathUtils.isPrime(-5), "Отрицательные числа не простые");
    }

    @Test
    @DisplayName("Тест генерации случайных чисел")
    void testRandomInRange() {
        for (int i = 0; i < 100; i++) {
            int result = MathUtils.randomInRange(1, 10);
            assertTrue(result >= 1 && result <= 10,
                    "Число должно быть в диапазоне [1, 10]");
        }
    }

    @Test
    @DisplayName("Тест с одинаковыми границами")
    void testRandomSameBounds() {
        assertEquals(5, MathUtils.randomInRange(5, 5),
                "При min = max должно возвращаться это число");
    }

    @Test
    @DisplayName("Тест с некорректным диапазоном")
    void testRandomInvalidRange() {
        assertThrows(IllegalArgumentException.class,
                () -> MathUtils.randomInRange(10, 5),
                "Должно быть исключение при min > max");
    }
}