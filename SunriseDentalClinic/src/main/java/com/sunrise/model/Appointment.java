package com.sunrise.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Appointment {

    private int appointmentId;

    private String appointmentNo;
    private String patientName;
    private String address;
    private String contactNumber;
    private String dentistName;
    private String treatmentType;
    private Date appointmentDate;
    private Time appointmentTime;
    private BigDecimal consultationFee;
    private BigDecimal treatmentCost;


    // =========================================================
    // APPOINTMENT ID
    // =========================================================

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    // =========================================================
    // APPOINTMENT NUMBER
    // =========================================================

    public String getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(String appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    // =========================================================
    // PATIENT NAME
    // =========================================================

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    // =========================================================
    // ADDRESS
    // =========================================================

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    // =========================================================
    // CONTACT NUMBER
    // =========================================================

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    // =========================================================
    // DENTIST NAME
    // =========================================================

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }


    // =========================================================
    // TREATMENT TYPE
    // =========================================================

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }


    // =========================================================
    // APPOINTMENT DATE
    // =========================================================

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    // =========================================================
    // APPOINTMENT TIME
    // =========================================================

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    // =========================================================
    // CONSULTATION FEE
    // =========================================================

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(BigDecimal consultationFee) {
        this.consultationFee = consultationFee;
    }


    // =========================================================
    // TREATMENT COST
    // =========================================================

    public BigDecimal getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(BigDecimal treatmentCost) {
        this.treatmentCost = treatmentCost;
    }


    // =========================================================
    // TOTAL BILL
    // =========================================================

    public BigDecimal getTotalBill() {

        BigDecimal consultation =
                consultationFee == null
                        ? BigDecimal.ZERO
                        : consultationFee;

        BigDecimal treatment =
                treatmentCost == null
                        ? BigDecimal.ZERO
                        : treatmentCost;

        return consultation.add(treatment);
    }
}