package com.examples.with.different.packagename.untitled;

import java.util.function.Function;
public class Dbrent {
    private static final int ITMAX = 100;
    private static final double ZEPS = 1.0e-10;

    public static double dbrent(double ax, double bx, double cx, Function<Double, Double> f, Function<Double, Double> df,
                                double tol, double[] xmin) {
        int iter;
        boolean ok1;
        boolean ok2;
        double a, b, d = 0, d1, d2, du, dv, dw, dx, e = 0.0;
        double fu, fv, fw, fx, olde, tol1, tol2, u, u1, u2, v, w, x, xm;

        a = (ax < cx ? ax : cx);
        b = (ax > cx ? ax : cx);
        x = w = v = bx;
        fw = fv = fx = f.apply(x);
        dw = dv = dx = df.apply(x);

        for (iter = 1; iter <= ITMAX; iter++) {
            xm = 0.5 * (a + b);
            tol1 = tol * Math.abs(x) + ZEPS;
            tol2 = 2.0 * tol1;
            if (Math.abs(x - xm) <= (tol2 - 0.5 * (b - a))) {
                xmin[0] = x;
                return fx;
            }
            if (Math.abs(e) > tol1) {
                d1 = 2.0 * (b - a);
                d2 = d1;
                if (dw != dx) d1 = (w - x) * dx / (dx - dw);
                if (dv != dx) d2 = (v - x) * dx / (dx - dv);

                u1 = x + d1;
                u2 = x + d2;
                ok1 = (a - u1) * (u1 - b) > 0.0 && dx * d1 <= 0.0;
                ok2 = (a - u2) * (u2 - b) > 0.0 && dx * d2 <= 0.0;
                olde = e;
                e = d;
                if (ok1 || ok2) {
                    if (ok1 && ok2)
                        d = (Math.abs(d1) < Math.abs(d2) ? d1 : d2);
                    else if (ok1)
                        d = d1;
                    else
                        d = d2;
                    if (Math.abs(d) <= Math.abs(0.5 * olde)) {
                        u = x + d;
                        if (u - a < tol2 || b - u < tol2)
                            d = Math.signum(tol1) * (xm - x);
                    } else {
                        d = 0.5 * (e = (dx >= 0.0 ? a - x : b - x));
                    }
                } else {
                    d = 0.5 * (e = (dx >= 0.0 ? a - x : b - x));
                }
            } else {
                d = 0.5 * (e = (dx >= 0.0 ? a - x : b - x));
            }
            if (Math.abs(d) >= tol1) {
                u = x + d;
                fu = f.apply(u);
            } else {
                u = x + Math.signum(tol1) * tol1;
                fu = f.apply(u);
                if (fu > fx) {
                    xmin[0] = x;
                    return fx;
                }
            }
            du = df.apply(u);

            if (fu <= fx) {
                if (u >= x) a = x;
                else b = x;
                housekeeping(v, fv, dv, w, fw, dw, x, fx, dx, u, fu, du);
            } else {
                if (u < x) a = u;
                else b = u;
                if (fu <= fw || w == x) {
                    housekeeping(v, fv, dv, w, fw, dw, x, fx, dx, u, fu, du);
                } else if (fu < fv || v == x || v == w) {
                    housekeeping(v, fv, dv, w, fw, dw, x, fx, dx, u, fu, du);
                }
            }
        }
        throw new RuntimeException("Too many iterations in routine dbrent");
    }

    private static void housekeeping(double a, double fa, double da, double b, double fb, double db,
                                     double c, double fc, double dc, double x, double fx, double dx) {
        a = b;
        fa = fb;
        da = db;
        b = c;
        fb = fc;
        db = dc;
        c = x;
        fc = fx;
        dc = dx;
    }
}
