import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2026, Ariel University,
 *  * Ex1: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex1-
 * It contains few testing functions for the polynomial functions as define in Ex1.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex1Test {
    static final double[] P1 = {2, 0, 3, -1, 0}, P2 = {0.1, 0, 1, 0.1, 3};
    static double[] po1 = {2, 2}, po2 = {-3, 0.61, 0.2};
    ;
    static double[] po3 = {2, 1, -0.7, -0.02, 0.02};
    static double[] po4 = {-3, 0.61, 0.2};

    @Test
    /**
     * Tests that f(x) == poly(x).
     */
    void testF() {
        double fx0 = Ex1.f(po1, 0);
        double fx1 = Ex1.f(po1, 1);
        double fx2 = Ex1.f(po1, 2);
        assertEquals(fx0, 2, Ex1.EPS);
        assertEquals(fx1, 4, Ex1.EPS);
        assertEquals(fx2, 6, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1(x) + p2(x) == (p1+p2)(x)
     */
    void testF2() {
        double x = Math.PI;
        double[] po12 = Ex1.add(po1, po2);
        double f1x = Ex1.f(po1, x);
        double f2x = Ex1.f(po2, x);
        double f12x = Ex1.f(po12, x);
        assertEquals(f1x + f2x, f12x, Ex1.EPS);
    }

    @Test
    /**
     * Tests that p1+p2+ (-1*p2) == p1
     */
    void testAdd() {
        double[] p12 = Ex1.add(po1, po2);
        double[] minus1 = {-1};
        double[] pp2 = Ex1.mul(po2, minus1);
        double[] p1 = Ex1.add(p12, pp2);
        assertTrue(Ex1.equals(p1, po1));
    }

    @Test
    /**
     * Tests that p1+p2 == p2+p1
     */
    void testAdd2() {
        double[] p12 = Ex1.add(po1, po2);
        double[] p21 = Ex1.add(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }

    @Test
    /**
     * Tests that p1+0 == p1
     */
    void testAdd3() {
        double[] p1 = Ex1.add(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, po1));
    }

    @Test
    /**
     * Tests that p1*0 == 0
     */
    void testMul1() {
        double[] p1 = Ex1.mul(po1, Ex1.ZERO);
        assertTrue(Ex1.equals(p1, Ex1.ZERO));
    }

    @Test
    /**
     * Tests that p1*p2 == p2*p1
     */
    void testMul2() {
        double[] p12 = Ex1.mul(po1, po2);
        double[] p21 = Ex1.mul(po2, po1);
        assertTrue(Ex1.equals(p12, p21));
    }

    @Test
    /**
     * Tests that p1(x) * p2(x) = (p1*p2)(x),
     */
    void testMulDoubleArrayDoubleArray() {
        double[] xx = {0, 1, 2, 3, 4.1, -15.2222};
        double[] p12 = Ex1.mul(po1, po2);
        for (int i = 0; i < xx.length; i = i + 1) {
            double x = xx[i];
            double f1x = Ex1.f(po1, x);
            double f2x = Ex1.f(po2, x);
            double f12x = Ex1.f(p12, x);
            assertEquals(f12x, f1x * f2x, Ex1.EPS);
        }
    }

    @Test
    /**
     * Tests a simple derivative examples - till ZERO.
     */
    void testDerivativeArrayDoubleArray() {
        double[] p = {1, 2, 3}; // 3X^2+2x+1
        double[] pt = {2, 6}; // 6x+2
        double[] dp1 = Ex1.derivative(p); // 2x + 6
        double[] dp2 = Ex1.derivative(dp1); // 2
        double[] dp3 = Ex1.derivative(dp2); // 0
        double[] dp4 = Ex1.derivative(dp3); // 0
        assertTrue(Ex1.equals(dp1, pt));
        assertTrue(Ex1.equals(Ex1.ZERO, dp3));
        assertTrue(Ex1.equals(dp4, dp3));
    }

    @Test
    /**
     * Tests the parsing of a polynom in a String like form.
     */
    public void testFromString() {
        double[] p = {-1.1, 2.3, 3.1}; // 3.1X^2+ 2.3x -1.1
        String sp2 = "3.1x^2 +2.3x -1.1";
        String sp = Ex1.poly(p);
        double[] p1 = Ex1.getPolynomFromString(sp);
        double[] p2 = Ex1.getPolynomFromString(sp2);
        boolean isSame1 = Ex1.equals(p1, p);
        boolean isSame2 = Ex1.equals(p2, p);
        if (!isSame1) {
            fail();
        }
        if (!isSame2) {
            fail();
        }
        assertEquals(sp, Ex1.poly(p1));
    }

    @Test
    /**
     * Tests the equality of pairs of arrays.
     */
    public void testEquals() {
        double[][] d1 = {{0}, {1}, {1, 2, 0, 0}};
        double[][] d2 = {Ex1.ZERO, {1 + Ex1.EPS / 2}, {1, 2}};
        double[][] xx = {{-2 * Ex1.EPS}, {1 + Ex1.EPS * 1.2}, {1, 2, Ex1.EPS / 2}};
        for (int i = 0; i < d1.length; i = i + 1) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for (int i = 0; i < d1.length; i = i + 1) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Tests is the sameValue function is symmetric.
     */
    public void testSameValue2() {
        double x1 = -4, x2 = 0;
        double rs1 = Ex1.sameValue(po1, po2, x1, x2, Ex1.EPS);
        double rs2 = Ex1.sameValue(po2, po1, x1, x2, Ex1.EPS);
        assertEquals(rs1, rs2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function - it should be symmetric.
     */
    public void testArea() {
        double x1 = -4, x2 = 0;
        double a1 = Ex1.area(po1, po2, x1, x2, 100);
        double a2 = Ex1.area(po2, po1, x1, x2, 100);
        assertEquals(a1, a2, Ex1.EPS);
    }

    @Test
    /**
     * Test the area f1(x)=0, f2(x)=x;
     */
    public void testArea2() {
        double[] po_a = Ex1.ZERO;
        double[] po_b = {0, 1};
        double x1 = -1;
        double x2 = 2;
        double a1 = Ex1.area(po_a, po_b, x1, x2, 1);
        double a2 = Ex1.area(po_a, po_b, x1, x2, 2);
        double a3 = Ex1.area(po_a, po_b, x1, x2, 3);
        double a100 = Ex1.area(po_a, po_b, x1, x2, 100);
        double area = 2.5;
        assertEquals(a1, area, Ex1.EPS);
        assertEquals(a2, area, Ex1.EPS);
        assertEquals(a3, area, Ex1.EPS);
        assertEquals(a100, area, Ex1.EPS);
    }

    @Test
    /**
     * Test the area function.
     */
    public void testArea3() {
        double[] po_a = {2, 1, -0.7, -0.02, 0.02};
        double[] po_b = {6, 0.1, -0.2};
        double x1 = Ex1.sameValue(po_a, po_b, -10, -5, Ex1.EPS);
        double a1 = Ex1.area(po_a, po_b, x1, 6, 8);
        double area = 58.5658;
        assertEquals(a1, area, Ex1.EPS);
    }

    @Test
/**
 * Tests the equality of pairs of arrays.
 */
    public void testEquals2() { /** Added by me*/
        double[][] d1 = {
                {0},
                {3, 8.2},
                {5.3, -4.8, 0, 0}
        };
        double[][] d2 = {
                Ex1.ZERO,
                {3 - Ex1.EPS/2, 8.2 + Ex1.EPS/2},
                {5.3 + Ex1.EPS/2, -4.8 - Ex1.EPS/2, 0 * Ex1.EPS ,0},
        };
        double[][] xx = {{-2* Ex1.EPS}, {1+ Ex1.EPS*1.2}, {1,2, Ex1.EPS/2,0}};
        for(int i = 0; i < d1.length; i++) {
            assertTrue(Ex1.equals(d1[i], d2[i]));
        }
        for(int i = 0; i < d1.length; i++) {
            assertFalse(Ex1.equals(d1[i], xx[i]));
        }
    }

    @Test
    /**
     * Test linear polynomials
     */
    public void testPolyFromPoints1() { /** Added by me*/
        double[] xx = {1, 2};
        double[] yy = {3, 5}; // Line through (1,3) and (2,5)
        double[] coeffs = Ex1.PolynomFromPoints(xx, yy);

        assertNotNull(coeffs);
        assertEquals(0.0, coeffs[0], 1e-9); // A should be 0 for linear
        assertEquals(2.0, coeffs[1], 1e-9); // B = slope = (5-3)/(2-1) = 2
        assertEquals(1.0, coeffs[2], 1e-9); // C = y1 - B*x1 = 3 - 2*1 = 1
    }

    @Test
    /**
     * Points close to y
     */
    public void testPolyFromPoints2() { /** Added by me*/
        double[] xx = {1, 2, 3};
        double[] yy = {2, 3, 5}; // Points roughly on y = 0.5x^2 - 0.5x + 2
        double[] coeffs = Ex1.PolynomFromPoints(xx, yy);
    }

    @Test
    /**
     * Test when not enough points
     */
    public void testPolyFromPoints3() { /** Added by me*/
        double[] xx = {1};
        double[] yy = {2};
        assertNull(Ex1.PolynomFromPoints(xx, yy));
    }

    @Test
    /**
     * Test Linear Poly length
     */
    public void testLength1() { /** Added by me*/
        double[] p = {0, 1, 0};
        double len = Ex1.length(p, 0, 1, 100);
        assertEquals(Math.sqrt(2), len, 0.01);
    }

    @Test
    /**
     * Test Quadratic Poly length
     */
    public void testLength2() { /** Added by me*/
        double[] p = {0, 0, 1}; // y = x^2
        double len = Ex1.length(p, 0, 1, 100);
        // Exact length of y=x^2 from 0 to 1 is ∫ sqrt(1+(2x)^2) dx ≈ 1.47894
        assertEquals(1.47894, len, 0.01);
    }


    @Test
    /**
     * Test Quadratic poly from string
     */
    public void testFromString1() { /** Added by me*/
        String poly = "-1.0x^2 +3.0x +2.0";
        double[] coeffs = Ex1.getPolynomFromString(poly);
        assertArrayEquals(new double[]{2.0, 3.0, -1.0}, coeffs, 1e-9);
    }

    @Test
    /**
     * Test Linear poly from string
     */
    public void testFromString2() { /** Added by me*/
        String poly = "4.0x +5.0";
        double[] coeffs = Ex1.getPolynomFromString(poly);
        assertArrayEquals(new double[]{5.0, 4.0}, coeffs, 1e-9);
    }

    @Test
    /**
     * Test Negative coeffs
     */
    public void testFromString3() { /** Added by me*/
        String poly = "-2.0x^3 -4.0x^2 -6.0x -8.0";
        double[] coeffs = Ex1.getPolynomFromString(poly);
        assertArrayEquals(new double[]{-8.0, -6.0, -4.0, -2.0}, coeffs, 1e-9);
    }


    @Test
    /**
     * Test Quadratic Poly derivatives
     */
    public void testDerivative1() { /** Added by me */
        double[] po = {1.0, -4.0, 3.0}; // f(x) = 1 - 4x + 3x^2
        double[] result = Ex1.derivative(po);
        assertArrayEquals(new double[]{-4.0, 6.0}, result, 1e-9);
    }

    @Test
    /**
     * Test 0 in coeffs
     */
    public void testDerivative2() { /** Added by me */
        double[] po = {0.0, 0.0, 0.0}; // f(x) = 0
        double[] result = Ex1.derivative(po);
        assertArrayEquals(new double[]{0.0, 0.0}, result, 1e-9); // derivative = 0
    }


}

