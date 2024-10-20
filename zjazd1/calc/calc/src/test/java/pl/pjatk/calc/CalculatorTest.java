package pl.pjatk.calc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(0, calculator.add(-5, 5));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        assertNotEquals(0, calculator.subtract(10, 5));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertTrue(calculator.multiply(5, 0) == 0);
    }

    @Test
    public void testMultiplyNegativeNumbers() {
        assertEquals(10, calculator.multiply(-2, -5));
    }

    @Test
    public void testDivide() {
        assertEquals(2, calculator.divide(6, 3));
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
    }

    @Test
    public void testDivideNegativeNumbers() {
        assertEquals(-2, calculator.divide(-6, 3));
        assertEquals(2, calculator.divide(-6, -3));
    }

}
