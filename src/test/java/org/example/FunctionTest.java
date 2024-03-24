package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.Math.atan;
import static org.junit.jupiter.api.Assertions.*;


public class FunctionTest {

    Function fun = new Function();
    double eps = 0.0001;

    @ParameterizedTest
    @ValueSource(doubles = {-1.01, -1.1, -4, -35})
    public void checkNegativeDivergenceInterval(double x) {
        assertThrows(IllegalArgumentException.class, () -> fun.arctg(x, eps));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.01, 1.1, 7, 24})
    public void checkPositiveDivergenceInterval(double x) {
        assertThrows(IllegalArgumentException.class, () -> fun.arctg(x, eps));
    }

    @Test
    public void checkBoundaries() {
        double x = 1;
        assertEquals(atan(x), fun.arctg(x, eps), eps);
        assertEquals(atan(-x), fun.arctg(-x, eps), eps);
    }

    @Test
    public void checkZero() {
        double x = 0;
        assertEquals(atan(x), fun.arctg(x, eps), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.25, 0.4, 0.73, 0.912, 1})
    public void checkPositiveConvergenceInterval(double x) {
        assertEquals(atan(x), fun.arctg(x, eps), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -0.25, -0.4, -0.73, -0.912, -1})
    public void checkNegativeConvergenceInterval(double x) {
        assertEquals(atan(x), fun.arctg(x, eps), eps);
    }
}
