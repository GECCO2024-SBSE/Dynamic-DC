package com.examples.with.different.packagename.GECCO;

import org.mockito.internal.stubbing.answers.Returns;

/**
 * @author tridv on 11/2/2023
 * @project evosuite
 */
public class Bessj {
    private static final double ACC = 1.0e10;
    private static final double BIGNO = 1.0e10;
    private static final double BIGNI = 1.0e-10;

    public double bessj(int n, double x)
//    Returns the Bessel function Jn(x) for any real x and n ≥ 2.
    {
        int j, jsum, m;
        double ax, bj, bjm, bjp, sum, tox, ans;
        if (n < 2) {
            System.out.println("Index n less than 2 in bessj");
        }
        ax = Math.abs(x);
        if (ax == 0.0) {
            return 0.0;
        } else if (ax > (float) n) {
            tox = 2.0 / ax;
            bjm = bessj0(ax);
            bj = bessj1(ax);
            for (j = 1; j < n; j++) {
                bjp = j * tox * bj - bjm;
                bjm = bj;
                bj = bjp;
            }
            ans = bj;
        } else {
            tox = 2.0 / ax;
            m = 2 * ((n + (int) Math.sqrt(ACC * n)) / 2);
            jsum = 0;
            bjp = ans = sum = 0.0;
            bj = 1.0;
            for (j = m; j > 0; j--) {
                bjm = j * tox * bj - bjp;
                bjp = bj;
                bj = bjm;
                if (Math.abs(bj) > BIGNO) {
                    bj *= BIGNI;
                    bjp *= BIGNI;
                    ans *= BIGNI;
                    sum *= BIGNI;
                }
                if (jsum == 1) sum += bj;
                jsum = 1 - jsum;
                if (j == n) ans = bjp;
            }
            sum = 2.0 * sum - bj;
            ans /= sum;
        }
        return x < 0.0 && (n == 1) ? -ans : ans;
    }

    private double bessj0(double x) {
        double ax, z;
        double xx, y, ans, ans1, ans2;
        if ((ax = Math.abs(x)) < 8.0) {
            y = x * x;
            ans1 = 57568490574.0 + y * (-13362590354.0 + y * (651619640.7
                    + y * (-11214424.18 + y * (77392.33017 + y * (-184.9052456)))));
            ans2 = 57568490411.0 + y * (1029532985.0 + y * (9494680.718
                    + y * (59272.64853 + y * (267.8532712 + y * 1.0))));
            ans = ans1 / ans2;
        } else {
            z = 8.0 / ax;
            y = z * z;
            xx = ax - 0.785398164;
            ans1 = 1.0 + y * (-0.1098628627e-2 + y * (0.2734510407e-4
                    + y * (-0.2073370639e-5 + y * 0.2093887211e-6)));
            ans2 = -0.1562499995e-1 + y * (0.1430488765e-3
                    + y * (-0.6911147651e-5 + y * (0.7621095161e-6
                    - y * 0.934945152e-7)));
            ans = Math.sqrt(0.636619772 / ax) * (Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
        }
        return ans;
    }

    private double bessj1(double x) {
        double ax, z;
        double xx, y, ans, ans1, ans2;
        if ((ax = Math.abs(x)) < 8.0) {
            y = x * x;
            ans1 = x * (72362614232.0 + y * (-7895059235.0 + y * (242396853.1
                    + y * (-2972611.439 + y * (15704.48260 + y * (-30.16036606))))));
            ans2 = 144725228442.0 + y * (2300535178.0 + y * (18583304.74
                    + y * (99447.43394 + y * (376.9991397 + y * 1.0))));
            ans = ans1 / ans2;
        } else {
            z = 8.0 / ax;
            y = z * z;
            xx = ax - 2.356194491;
            ans1 = 1.0 + y * (0.183105e-2 + y * (-0.3516396496e-4
                    + y * (0.2457520174e-5 + y * (-0.240337019e-6))));
            ans2 = 0.04687499995 + y * (-0.2002690873e-3
                    + y * (0.8449199096e-5 + y * (-0.88228987e-6
                    + y * 0.105787412e-6)));
            ans = Math.sqrt(0.636619772 / ax) * (Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
            if (x < 0.0) ans = -ans;
        }
        return ans;
    }
}
