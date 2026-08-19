package com.sunrise.dao;

import com.sunrise.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillDAO {

    public void createBill(
            int appointmentId,
            double consultationFee,
            double treatmentCost,
            double totalAmount)
            throws Exception {

        String sql =
                "INSERT INTO bills " +
                "(appointment_id, consultation_fee, " +
                "treatment_cost, total_amount) " +
                "VALUES (?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, appointmentId);
            ps.setDouble(2, consultationFee);
            ps.setDouble(3, treatmentCost);
            ps.setDouble(4, totalAmount);

            ps.executeUpdate();
        }
    }
}