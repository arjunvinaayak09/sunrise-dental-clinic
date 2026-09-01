package com.sunrise.service;

import com.sunrise.model.Appointment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentServiceTest {
    @Test
    void totalBillShouldAddConsultationAndTreatment() {
        Appointment a = new Appointment();
        a.setConsultationFee(new BigDecimal("1000.00"));
        a.setTreatmentCost(new BigDecimal("2500.00"));
        assertEquals(new BigDecimal("3500.00"), a.getTotalBill());
    }
}
