package com.sunrise.service;

import com.sunrise.dao.AppointmentDAO;
import com.sunrise.model.Appointment;

import java.math.BigDecimal;
import java.util.List;

public class AppointmentService {

    private final AppointmentDAO dao = new AppointmentDAO();

    public void validate(Appointment a) {

        if (a.getAppointmentNo() == null ||
            a.getAppointmentNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment ID is required.");
        }

        if (a.getPatientName() == null ||
            a.getPatientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name is required.");
        }

        if (a.getAddress() == null ||
            a.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required.");
        }

        if (a.getContactNumber() == null ||
            a.getContactNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Contact number is required.");
        }

        if (a.getDentistName() == null ||
            a.getDentistName().trim().isEmpty()) {
            throw new IllegalArgumentException("Dentist name is required.");
        }

        if (a.getTreatmentType() == null ||
            a.getTreatmentType().trim().isEmpty()) {
            throw new IllegalArgumentException("Treatment type is required.");
        }

        if (a.getAppointmentDate() == null ||
            a.getAppointmentTime() == null) {
            throw new IllegalArgumentException(
                "Appointment date and time are required."
            );
        }

        if (a.getConsultationFee() == null) {
            a.setConsultationFee(BigDecimal.ZERO);
        }

        if (a.getTreatmentCost() == null) {
            a.setTreatmentCost(BigDecimal.ZERO);
        }

        if (a.getConsultationFee().signum() < 0 ||
            a.getTreatmentCost().signum() < 0) {
            throw new IllegalArgumentException(
                "Costs cannot be negative."
            );
        }
    }

    public void create(Appointment a) throws Exception {
        validate(a);

        if (!dao.create(a)) {
            throw new Exception("Registration failed.");
        }
    }

    public Appointment find(String no) throws Exception {
        return dao.findByAppointmentNo(no);
    }

    // EDIT / UPDATE
    public void update(Appointment a) throws Exception {
        validate(a);

        if (!dao.update(a)) {
            throw new Exception("Appointment not found or update failed.");
        }
    }

    public void delete(String no) throws Exception {
        if (!dao.delete(no)) {
            throw new Exception("Appointment not found.");
        }
    }

    public List<Appointment> all() throws Exception {
        return dao.findAll();
    }

    public int count() throws Exception {
        return dao.count();
    }
}