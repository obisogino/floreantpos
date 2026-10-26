package com.floreantpos.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Records existing US-locale display and rounding rules before migration. */
class NumberUtilBehaviorTest {

    @Test
    void defaultMoneyDisplayHidesNegativeAmounts() {
        assertEquals("0.00", NumberUtil.formatNumber(-12.5));
        assertEquals("0.00", NumberUtil.formatNumber(-12.5, false));
    }

    @Test
    void moneyDisplayCanKeepNegativeAmountsWhenRequested() {
        assertEquals("-12.50", NumberUtil.formatNumber(-12.5, true));
    }

    @Test
    void missingAmountsDisplayAsZeroWithTheRequestedPrecision() {
        assertEquals("0.00", NumberUtil.formatNumber((Double) null));
        assertEquals("0.000", NumberUtil.format3DigitNumber(null));
        assertEquals("0", NumberUtil.trimDecilamIfNotNeeded(null));
    }

    @Test
    void moneyDisplayKeepsTwoPlacesAndGrouping() {
        assertEquals("1,234.50", NumberUtil.formatNumber(1234.5));
    }

    @Test
    void threePlaceDisplayKeepsNegativeAmounts() {
        assertEquals("-12.500", NumberUtil.format3DigitNumber(-12.5));
    }

    @Test
    void decimalTrimmingRemovesWholeNumberFractionsAndHidesNegatives() {
        assertEquals("12", NumberUtil.trimDecilamIfNotNeeded(12.0));
        assertEquals("12.5", NumberUtil.trimDecilamIfNotNeeded(12.5));
        assertEquals("0", NumberUtil.trimDecilamIfNotNeeded(-12.5));
    }

    @Test
    void exactHalfValuesRoundAwayFromZero() {
        assertEquals(1.13, NumberUtil.roundToTwoDigit(1.125));
        assertEquals(-1.13, NumberUtil.roundToTwoDigit(-1.125));
        assertEquals(1.063, NumberUtil.roundToThreeDigit(1.0625));
    }

    @Test
    void existingDoubleBasedRoundingOf2675IsPreserved() {
        // Characterization, not a new rule: do not silently change money in a JDK move.
        assertEquals(2.67, NumberUtil.roundToTwoDigit(2.675));
    }

    @Test
    void variablePrecisionIsCappedAtFivePlaces() {
        assertEquals("12.50000", NumberUtil.formatNumber(12.5, 8));
        assertEquals("12.50", NumberUtil.formatNumber(12.5, 2));
    }

    @Test
    void missingInputParsesAsZero() throws Exception {
        assertEquals(0.0, NumberUtil.parse(null).doubleValue());
        assertEquals(0.0, NumberUtil.parse("").doubleValue());
    }

    @Test
    void groupedDecimalInputParsesToTheExistingAmount() throws Exception {
        assertEquals(1234.5, NumberUtil.parse("1,234.50").doubleValue());
    }
}
