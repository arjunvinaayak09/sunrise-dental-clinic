package com.sunrise.dao;

import com.sunrise.config.DBConnection;
import com.sunrise.model.Appointment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================

    public boolean create(Appointment a) throws Exception {

        String sql =
                "INSERT INTO appointments " +
                "(appointment_no, patient_name, address, contact_number, " +
                "dentist_name, treatment_type, appointment_date, appointment_time, " +
                "consultation_fee, treatment_cost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection
                        .getInstance()
                        .getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    a.getAppointmentNo()
            );

            ps.setString(
                    2,
                    a.getPatientName()
            );

            ps.setString(
                    3,
                    a.getAddress()
            );

            ps.setString(
                    4,
                    a.getContactNumber()
            );

            ps.setString(
                    5,
                    a.getDentistName()
            );

            ps.setString(
                    6,
                    a.getTreatmentType()
            );

            ps.setDate(
                    7,
                    a.getAppointmentDate()
            );

            ps.setTime(
                    8,
                    a.getAppointmentTime()
            );

            ps.setBigDecimal(
                    9,
                    getSafeAmount(a.getConsultationFee())
            );

            ps.setBigDecimal(
                    10,
                    getSafeAmount(a.getTreatmentCost())
            );

            return ps.executeUpdate() > 0;
        }
    }


    // =========================================================
    // FIND BY APPOINTMENT NUMBER
    // =========================================================

    public Appointment findByAppointmentNo(
            String no) throws Exception {

        String sql =
                "SELECT " +
                "id, " +
                "appointment_no, " +
                "patient_name, " +
                "address, " +
                "contact_number, " +
                "dentist_name, " +
                "treatment_type, " +
                "appointment_date, " +
                "appointment_time, " +
                "consultation_fee, " +
                "treatment_cost " +
                "FROM appointments " +
                "WHERE appointment_no = ?";

        try (
                Connection con = DBConnection
                        .getInstance()
                        .getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    no
            );

            try (
                    ResultSet rs = ps.executeQuery()
            ) {

                if (rs.next()) {

                    return mapAppointment(rs);
                }
            }
        }

        return null;
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    public boolean update(
            Appointment a) throws Exception {

        String sql =
                "UPDATE appointments SET " +
                "patient_name = ?, " +
                "address = ?, " +
                "contact_number = ?, " +
                "dentist_name = ?, " +
                "treatment_type = ?, " +
                "appointment_date = ?, " +
                "appointment_time = ?, " +
                "consultation_fee = ?, " +
                "treatment_cost = ? " +
                "WHERE appointment_no = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    a.getPatientName()
            );

            ps.setString(
                    2,
                    a.getAddress()
            );

            ps.setString(
                    3,
                    a.getContactNumber()
            );

            ps.setString(
                    4,
                    a.getDentistName()
            );

            ps.setString(
                    5,
                    a.getTreatmentType()
            );

            ps.setDate(
                    6,
                    a.getAppointmentDate()
            );

            ps.setTime(
                    7,
                    a.getAppointmentTime()
            );

            ps.setBigDecimal(
                    8,
                    getSafeAmount(a.getConsultationFee())
            );

            ps.setBigDecimal(
                    9,
                    getSafeAmount(a.getTreatmentCost())
            );

            ps.setString(
                    10,
                    a.getAppointmentNo()
            );

            return ps.executeUpdate() > 0;
        }
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    public boolean delete(
            String no) throws Exception {

        String sql =
                "DELETE FROM appointments " +
                "WHERE appointment_no = ?";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    no
            );

            return ps.executeUpdate() > 0;
        }
    }


    // =========================================================
    // FIND ALL APPOINTMENTS
    // =========================================================

    public List<Appointment> findAll()
            throws Exception {

        List<Appointment> list =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "id, " +
                "appointment_no, " +
                "patient_name, " +
                "address, " +
                "contact_number, " +
                "dentist_name, " +
                "treatment_type, " +
                "appointment_date, " +
                "appointment_time, " +
                "consultation_fee, " +
                "treatment_cost " +
                "FROM appointments " +
                "ORDER BY appointment_date, appointment_time";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                list.add(
                        mapAppointment(rs)
                );
            }
        }

        return list;
    }


    // =========================================================
    // COUNT APPOINTMENTS
    // =========================================================

    public int count()
            throws Exception {

        String sql =
                "SELECT COUNT(*) FROM appointments";

        try (
        		Connection con = DBConnection
                .getInstance()
                .getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }
        }

        return 0;
    }


    // =========================================================
    // MAP DATABASE ROW TO APPOINTMENT OBJECT
    // =========================================================

    private Appointment mapAppointment(
            ResultSet rs) throws Exception {

        Appointment a = new Appointment();

        a.setAppointmentId(
                rs.getInt("id")
        );
        a.setAppointmentNo(
                rs.getString("appointment_no")
        );

        // =====================================================
        // PATIENT DETAILS
        // =====================================================

        a.setPatientName(
                rs.getString("patient_name")
        );

        a.setAddress(
                rs.getString("address")
        );

        a.setContactNumber(
                rs.getString("contact_number")
        );


        // =====================================================
        // DENTIST
        // =====================================================

        a.setDentistName(
                rs.getString("dentist_name")
        );


        // =====================================================
        // TREATMENT
        // =====================================================

        a.setTreatmentType(
                rs.getString("treatment_type")
        );


        // =====================================================
        // DATE AND TIME
        // =====================================================

        a.setAppointmentDate(
                rs.getDate("appointment_date")
        );

        a.setAppointmentTime(
                rs.getTime("appointment_time")
        );


        // =====================================================
        // CONSULTATION FEE
        // =====================================================

        BigDecimal consultationFee =
                rs.getBigDecimal(
                        "consultation_fee"
                );

        if (consultationFee == null) {

            consultationFee =
                    BigDecimal.ZERO;
        }

        a.setConsultationFee(
                consultationFee
        );


        // =====================================================
        // TREATMENT COST
        // =====================================================

        BigDecimal treatmentCost =
                rs.getBigDecimal(
                        "treatment_cost"
                );

        if (treatmentCost == null) {

            treatmentCost =
                    BigDecimal.ZERO;
        }

        a.setTreatmentCost(
                treatmentCost
        );


        return a;
    }


    // =========================================================
    // SAFE AMOUNT
    // =========================================================

    private BigDecimal getSafeAmount(
            BigDecimal amount) {

        if (amount == null) {

            return BigDecimal.ZERO;
        }

        return amount;
    }
}