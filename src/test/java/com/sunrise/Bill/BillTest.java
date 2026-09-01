package com.sunrise.Bill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BillTest {

    @Test
    public void testCalculateBill() {

        double consultationFee = 500.00;
        double treatmentCost = 1000.00;

        double total =
                consultationFee + treatmentCost;

        assertEquals(
                1500.00,
                total,
                0.001
        );
    }

    @Test
    public void testZeroBill() {

        double consultationFee = 0.00;
        double treatmentCost = 0.00;

        double total =
                consultationFee + treatmentCost;

        assertEquals(
                0.00,
                total,
                0.001
        );
    }

    @Test
    public void testOnlyConsultationFee() {

        double consultationFee = 500.00;
        double treatmentCost = 0.00;

        double total =
                consultationFee + treatmentCost;

        assertEquals(
                500.00,
                total,
                0.001
        );
    }

    @Test
    public void testOnlyTreatmentCost() {

        double consultationFee = 0.00;
        double treatmentCost = 1000.00;

        double total =
                consultationFee + treatmentCost;

        assertEquals(
                1000.00,
                total,
                0.001
        );
    }
}