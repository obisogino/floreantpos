package com.floreantpos.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.floreantpos.model.Discount;
import com.floreantpos.model.TicketDiscount;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemDiscount;

/** Records current discount calculations; does not introduce new pricing rules. */
class DiscountUtilBehaviorTest {

    @Test
    void fixedTicketDiscountDoesNotScaleWithTicketPrice() {
        TicketDiscount discount = ticketDiscount(Discount.DISCOUNT_TYPE_AMOUNT, 15.0);
        assertEquals(15.0, DiscountUtil.calculateDiscountAmount(80.0, discount).doubleValue());
        assertEquals(15.0, DiscountUtil.calculateDiscountAmount(400.0, discount).doubleValue());
    }

    @Test
    void percentageTicketDiscountUsesTheGivenPrice() {
        TicketDiscount discount = ticketDiscount(Discount.DISCOUNT_TYPE_PERCENTAGE, 25.0);
        assertEquals(20.0, DiscountUtil.calculateDiscountAmount(80.0, discount).doubleValue());
    }

    @Test
    void itemPercentageWithoutMinimumAppliesToOneUnitPrice() {
        TicketItemDiscount discount = itemDiscount(3, 90.0,
                Discount.DISCOUNT_TYPE_PERCENTAGE, 20.0, 0);
        assertEquals(6.0, DiscountUtil.calculateDiscountAmount(discount).doubleValue());
    }

    @Test
    void minimumQuantityCountsOnlyCompleteGroupsForFixedDiscount() {
        TicketItemDiscount discount = itemDiscount(5, 150.0,
                Discount.DISCOUNT_TYPE_AMOUNT, 4.0, 2);
        assertEquals(8.0, DiscountUtil.calculateDiscountAmount(discount).doubleValue());
    }

    @Test
    void minimumQuantityPercentageUsesOneUnitPricePerCompleteGroup() {
        TicketItemDiscount discount = itemDiscount(5, 150.0,
                Discount.DISCOUNT_TYPE_PERCENTAGE, 20.0, 2);
        assertEquals(12.0, DiscountUtil.calculateDiscountAmount(discount).doubleValue());
    }

    @Test
    void quantityBelowMinimumGetsNoDiscount() {
        TicketItemDiscount discount = itemDiscount(1, 30.0,
                Discount.DISCOUNT_TYPE_AMOUNT, 4.0, 2);
        assertEquals(0.0, DiscountUtil.calculateDiscountAmount(discount).doubleValue());
    }

    private static TicketDiscount ticketDiscount(int type, double value) {
        TicketDiscount discount = new TicketDiscount();
        discount.setType(type);
        discount.setValue(value);
        return discount;
    }

    private static TicketItemDiscount itemDiscount(int count, double subtotal,
            int type, double value, int minimum) {
        TicketItem item = new TicketItem();
        item.setItemCount(count);
        item.setSubtotalAmount(subtotal);
        TicketItemDiscount discount = new TicketItemDiscount();
        discount.setTicketItem(item);
        discount.setType(type);
        discount.setValue(value);
        discount.setMinimumQuantity(minimum);
        return discount;
    }
}
