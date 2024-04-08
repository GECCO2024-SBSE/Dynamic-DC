public class Ran2 {
    private static final int IM1 = 2147483563;
    private static final int IM2 = 2147483399;
    private static final double AM = 1.0 / IM1;
    private static final int IMM1 = IM1 - 1;
    private static final int IA1 = 40014;
    private static final int IA2 = 40692;
    private static final int IQ1 = 53668;
    private static final int IQ2 = 52774;
    private static final int IR1 = 12211;
    private static final int IR2 = 3791;
    private static final int NTAB = 32;
    private static final double EPS = 1.2e-7;
    private static final double RNMX = 1.0 - EPS;
    private static final long NDIV = (1 + IMM1 / NTAB);

    private long idum2 = 123456789;
    private long iy = 0;
    private long[] iv = new long[NTAB];

    public double ran2(long idum) {
        int j;
        long k;
        float temp;

        if (idum <= 0) {
            // Initialize.
            if (-idum < 1) idum = 1; // Be sure to prevent idum = 0.
            else idum = -idum;
            idum2 = idum;
            for (j = NTAB + 7; j >= 0; j--) {
                // Load the shuffle table (after 8 warm-ups).
                k = idum / IQ1;
                idum = IA1 * (idum - k * IQ1) - k * IR1;
                if (idum < 0) idum += IM1;
                if (j < NTAB) iv[j] = idum;
            }
            iy = iv[0];
        }

        k = idum / IQ1; // Start here when not initializing.
        idum = IA1 * (idum - k * IQ1) - k * IR1; // Compute idum=(IA1*idum) % IM1 without overflows by Schrage’s method.
        if (idum < 0) idum += IM1;

        k = idum2 / IQ2;
        idum2 = IA2 * (idum2 - k * IQ2) - k * IR2; // Compute idum2=(IA2*idum) % IM2 likewise.
        if (idum2 < 0) idum2 += IM2;

        j = (int) (iy / NDIV); // Will be in the range 0..NTAB-1.
        iy = iv[j] - idum2; // Here idum is shuffled, idum and idum2 are combined to generate output.
        iv[j] = idum;

        if (iy < 1) iy += IMM1;
        temp = (float) (AM * iy);

        return (temp > RNMX) ? RNMX : temp; // Because users don’t expect endpoint values.
    }
}

