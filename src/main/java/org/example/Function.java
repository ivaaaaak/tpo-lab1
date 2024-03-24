package org.example;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Function {

    public double arctg(double x, double eps) {

        if (abs(x) > 1) {
            throw new IllegalArgumentException("|x| must be <= 1");
        }

        double cur = eps;
        double result = 0;

        for (int n = 0; abs(cur) >= eps; n++) {
            cur = (pow(-1, n) * pow(x, 2 * n + 1)) / (2 * n + 1);
            result += cur;
        }

        return result;
    }
}
