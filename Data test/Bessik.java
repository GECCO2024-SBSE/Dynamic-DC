
/**
 * @author tridv on 11/2/2023
 * @project evosuite
 */
public class Bessik {
    private static final double EPS = 1.0e-10;
    private static final double FPMIN = 1.0e-30;
    private static final int MAXIT = 10000;
    private static final double XMIN = 2.0;
    private static final double PI = 3.141592653589793;
    private static final int NUSE1 = 5;
    private static final int NUSE2 = 5;

    public static void bessik(double x, double xnu, double[] ri, double[] rk, double[] rip, double[] rkp) {
        int i, l, nl;
        double a,a1, b, c, d, del, del1, delh, dels, e, f, fact, fact2, ff, gam1=0.0, gam2=0.0,
                gammi=0.0, gampl=0.0, h, p, pimu, q, q1, q2, qnew, ril, ril1, rimu, rip1, ripl,
                ritemp, rk1, rkmu, rkmup, rktemp, s, sum, sum1, x2, xi, xi2, xmu, xmu2;
        if (x <= 0.0 || xnu < 0.0)
            System.err.print("bad arguments in bessik");

        nl = (int) (xnu + 0.5);
        xmu = xnu - nl;
        xmu2 = xmu * xmu;
        xi = 1.0 / x;
        xi2 = 2.0 * xi;
        h = xnu * xi;
        if (h < FPMIN)
            h = FPMIN;

        b = xi2 * xnu;
        d = 0.0;
        c = h;
        for (i = 1; i <= MAXIT; i++) {
            b += xi2;
            d = 1.0 / (b + d);
            c = b + 1.0 / c;
            del = c * d;
            h = del * h;
            if (Math.abs(del - 1.0) < EPS)
                break;
        }
        ril = FPMIN;
        ripl = h * ril;
        ril1 = ril;
        rip1 = ripl;
        fact = xnu * xi;
        for (l = nl; l >= 1; l--) {
            ritemp = fact * ril + ripl;
            fact -= xi;
            ripl = fact * ritemp + ril;
            ril = ritemp;
        }
        f = ripl / ril;

        if (x < XMIN) {
            x2 = 0.5 * x;
            pimu = PI * xmu;
            fact = (Math.abs(pimu) < EPS ? 1.0 : pimu / Math.sin(pimu));
            d = -Math.log(x2);
            e = xmu * d;
            fact2 = (Math.abs(e) < EPS ? 1.0 : Math.sinh(e) / e);
            ff = fact * (gam1 * Math.cosh(e) + gam2 * fact2 * d);
            sum = ff;
            e = Math.exp(e);
            p = 0.5 * e / gampl;
            q = 0.5 / (e * gammi);
            c = 1.0;
            d = x2 * x2;
            sum1 = p;
            for (i = 1; i <= MAXIT; i++) {
                ff = (i * ff + p + q) / (i * i - xmu2);
                c *= (d / i);
                p /= (i - xmu);
                q /= (i + xmu);
                del = c * ff;
                sum += del;
                del1 = c * (p - i * ff);
                sum1 += del1;
                if (Math.abs(del) < Math.abs(sum) * EPS)
                    break;
            }
            if (i > MAXIT){}
            rkmu = sum;
            rk1 = sum1 * xi2;
        } else {
            b = 2.0 * (1.0 + x);
            d = 1.0 / b;
            h = delh = d;
            q1 = 0.0;
            q2 = 1.0;
            a1 = 0.25 - xmu2;
            q = c = a1;
            a = -a1;
            s = 1.0 + q * delh;
            for (i = 2; i <= MAXIT; i++) {
                a -= 2 * (i - 1);
                c = -a * c / i;
                qnew = (q1 - b * q2) / a;
                q1 = q2;
                q2 = qnew;
                q += c * qnew;
                b += 2.0;
                d = 1.0 / (b + a * d);
                delh = (b * d - 1.0) * delh;
                h += delh;
                dels = q * delh;
                s += dels;
                if (Math.abs(dels / s) < EPS)
                    break;
            }
            h = a1 * h;
            rkmu = Math.sqrt(PI / (2.0 * x)) * Math.exp(-x) / s;
            rk1 = rkmu * (xmu + x + 0.5 - h) * xi;
        }
        rkmup = xmu * xi * rkmu - rk1;
        rimu = xi / (f * rkmu - rkmup);
        ri[0] = (rimu * ril1) / ril;
        rip[0] = (rimu * rip1) / ril;
        for (i = 1; i <= nl; i++) {
            rktemp = (xmu + i) * xi2 * rk1 + rkmu;
            rkmu = rk1;
            rk1 = rktemp;
        }
        rk[0] = rkmu;
        rkp[0] = xnu * xi * rkmu - rk1;
    }}
