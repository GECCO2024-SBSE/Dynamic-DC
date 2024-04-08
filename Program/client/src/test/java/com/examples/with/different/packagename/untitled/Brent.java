package com.examples.with.different.packagename.untitled;

import java.util.function.Function;

public class Brent{

    private static final int ITMAX = 100;
    private static final double CGOLD = 0.3819660;
    private static final double ZEPS = 1.0e-10;

    public static double brent(double ax, double bx, double cx, Function<Double, Double> f, double tol, double[] xmin) {
        int iter;
        double a, b, d = 0, etemp, fu, fv, fw, fx, p, q, r, tol1, tol2, u, v, w, x, xm;
        double e = 0.0; // Khoảng cách di chuyển ở bước trước đó.
        a = (ax < cx ? ax : cx);
        b = (ax > cx ? ax : cx);
        x = w = v = bx; // Khởi tạo...
        fw = fv = fx = f.apply(x);
        for (iter = 1; iter <= ITMAX; iter++) { // Vòng lặp chính.
            xm = 0.5 * (a + b);
            tol2 = 2.0 * (tol1 = tol * Math.abs(x) + ZEPS);
            if (Math.abs(x - xm) <= (tol2 - 0.5 * (b - a))) { // Kiểm tra kết thúc tại đây.
                xmin[0] = x;
                return fx;
            }
            if (Math.abs(e) > tol1) { // Xây dựng đường cong parabol thử nghiệm.
                r = (x - w) * (fx - fv);
                q = (x - v) * (fx - fw);
                p = (x - v) * q - (x - w) * r;
                q = 2.0 * (q - r);
                if (q > 0.0)
                    p = -p;
                q = Math.abs(q);
                etemp = e;
                e = d;
                if (Math.abs(p) >= Math.abs(0.5 * q * etemp) || p <= q * (a - x) || p >= q * (b - x))
                    d = CGOLD * (e = (x >= xm ? a - x : b - x)); // Các điều kiện trên quyết định tính chấp nhận của đường cong parabol. Ở đây, chúng ta thực hiện bước chia tỷ lệ và chọn đoạn lớn hơn trong hai đoạn.
                else
                    d = p / q; // Thực hiện bước parabol.
                u = x + d;
                if (u - a < tol2 || b - u < tol2)
                    d = Math.signum(tol1) * (xm - x);
            } else {
                d = CGOLD * (e = (x >= xm ? a - x : b - x));
            }
            u = (Math.abs(d) >= tol1 ? x + d : x + Math.signum(tol1) * tol1);
            fu = f.apply(u); // Một lượt đánh giá hàm duy nhất cho mỗi lần lặp.
            if (fu <= fx) { // Quyết định làm gì với việc đánh giá hàm của chúng ta.
                if (u >= x) a = x;
                else b = x;
                housekeeping(v, w, x, u); // Dọn dẹp sau mỗi lần lặp:
                housekeeping(fv, fw, fx, fu);
            } else {
                if (u < x) a = u;
                else b = u;
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            } // Hoàn tất công việc dọn dẹp. Quay lại một lần lặp khác.
        }
        throw new RuntimeException("Quá nhiều lần lặp trong brent");
    }

    private static void housekeeping(double a, double b, double c, double d) {
        // Chú ý rằng trong Java, giá trị của các biến được truyền bởi giá trị, do đó, không cần phải làm bất kỳ điều gì ở đây.
    }
}
