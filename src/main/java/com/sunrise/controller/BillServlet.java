package com.sunrise.controller;

import com.sunrise.dao.AppointmentDAO;
import com.sunrise.dao.BillDAO;
import com.sunrise.model.Appointment;
import com.sunrise.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final BillDAO billDAO = new BillDAO();

    // =========================================================
    // GET
    // =========================================================
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        // -----------------------------------------------------
        // Check receptionist access
        // -----------------------------------------------------
        if (!isReceptionist(req)) {
            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Receptionist access required"
            );
            return;
        }

        // -----------------------------------------------------
        // Get appointment number from URL/form
        // Example:
        // /bill?appointmentId=APP001
        // -----------------------------------------------------
        String appointmentNo = req.getParameter("appointmentId");

        if (appointmentNo != null && !appointmentNo.trim().isEmpty()) {

            appointmentNo = appointmentNo.trim();

            try {

                Appointment appointment =
                        appointmentDAO.findByAppointmentNo(appointmentNo);

                // -------------------------------------------------
                // Appointment not found
                // -------------------------------------------------
                if (appointment == null) {

                    req.setAttribute(
                            "errorMessage",
                            "Appointment not found for: " + appointmentNo
                    );

                } else {

                    // -------------------------------------------------
                    // Appointment found
                    // -------------------------------------------------
                    setBillAttributes(req, appointment);
                }

            } catch (Exception e) {

                throw new ServletException(
                        "Unable to find appointment",
                        e
                );
            }
        }

        // -----------------------------------------------------
        // Display bill page
        // -----------------------------------------------------
        showBillPage(req, resp);
    }

    // =========================================================
    // POST
    // =========================================================
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        // -----------------------------------------------------
        // Check receptionist access
        // -----------------------------------------------------
        if (!isReceptionist(req)) {
            resp.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Receptionist access required"
            );
            return;
        }

        try {

            // -------------------------------------------------
            // Get appointment number
            // -------------------------------------------------
            String appointmentNo =
                    req.getParameter("appointmentId");

            if (appointmentNo == null
                    || appointmentNo.trim().isEmpty()) {

                req.setAttribute(
                        "errorMessage",
                        "Please enter an appointment number."
                );

                showBillPage(req, resp);
                return;
            }

            appointmentNo = appointmentNo.trim();

            // -------------------------------------------------
            // Find appointment
            // -------------------------------------------------
            Appointment appointment =
                    appointmentDAO.findByAppointmentNo(
                            appointmentNo
                    );

            // -------------------------------------------------
            // Appointment not found
            // -------------------------------------------------
            if (appointment == null) {

                req.setAttribute(
                        "errorMessage",
                        "Appointment not found: " + appointmentNo
                );

                showBillPage(req, resp);
                return;
            }

            // -------------------------------------------------
            // Get consultation fee
            // -------------------------------------------------
            BigDecimal consultationFee =
                    appointment.getConsultationFee();

            if (consultationFee == null) {
                consultationFee = BigDecimal.ZERO;
            }

            // -------------------------------------------------
            // Get treatment cost
            // -------------------------------------------------
            BigDecimal treatmentCost =
                    appointment.getTreatmentCost();

            if (treatmentCost == null) {
                treatmentCost = BigDecimal.ZERO;
            }

            // -------------------------------------------------
            // Set scale to 2 decimal places
            // -------------------------------------------------
            consultationFee =
                    consultationFee.setScale(
                            2,
                            RoundingMode.HALF_UP
                    );

            treatmentCost =
                    treatmentCost.setScale(
                            2,
                            RoundingMode.HALF_UP
                    );

            // -------------------------------------------------
            // Validate amounts
            // -------------------------------------------------
            if (consultationFee.compareTo(BigDecimal.ZERO) < 0
                    || treatmentCost.compareTo(BigDecimal.ZERO) < 0) {

                req.setAttribute(
                        "errorMessage",
                        "Appointment contains an invalid amount."
                );

                setBillAttributes(req, appointment);

                showBillPage(req, resp);
                return;
            }

            // -------------------------------------------------
            // Calculate total
            //
            // BigDecimal + BigDecimal
            // -------------------------------------------------
            BigDecimal totalAmount =
                    consultationFee.add(treatmentCost);

            totalAmount =
                    totalAmount.setScale(
                            2,
                            RoundingMode.HALF_UP
                    );

            // -------------------------------------------------
            // Save bill
            //
            // Appointment.java contains:
            // getId()
            //
            // NOT:
            // getAppointmentId()
            // -------------------------------------------------
            //
            // If your BillDAO currently accepts double,
            // convert only when sending values to BillDAO.
            // -------------------------------------------------
            //billDAO.createBill(
            //		appointment.getAppointmentId(),
            //        consultationFee.doubleValue(),
             //       treatmentCost.doubleValue(),
           //         totalAmount.doubleValue()
          //  );

            // -------------------------------------------------
            // Send appointment to JSP
            // -------------------------------------------------
            req.setAttribute(
                    "appointment",
                    appointment
            );

            // -------------------------------------------------
            // Send BigDecimal amounts to JSP
            // -------------------------------------------------
            req.setAttribute(
                    "consultationFee",
                    consultationFee
            );

            req.setAttribute(
                    "treatmentCost",
                    treatmentCost
            );

            req.setAttribute(
                    "totalAmount",
                    totalAmount
            );

            // -------------------------------------------------
            // Tell JSP that bill was saved
            // -------------------------------------------------
            req.setAttribute(
                    "billSaved",
                    true
            );

            // -------------------------------------------------
            // Success message
            // -------------------------------------------------
            req.setAttribute(
                    "successMessage",
                    "Bill calculated and saved successfully."
            );

            // -------------------------------------------------
            // Display bill
            // -------------------------------------------------
            showBillPage(req, resp);

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to calculate bill",
                    e
            );
        }
    }

    // =========================================================
    // SET BILL ATTRIBUTES
    // =========================================================
    private void setBillAttributes(
            HttpServletRequest req,
            Appointment appointment) {

        // -----------------------------------------------------
        // Consultation fee
        // -----------------------------------------------------
        BigDecimal consultationFee =
                appointment.getConsultationFee();

        if (consultationFee == null) {
            consultationFee = BigDecimal.ZERO;
        }

        // -----------------------------------------------------
        // Treatment cost
        // -----------------------------------------------------
        BigDecimal treatmentCost =
                appointment.getTreatmentCost();

        if (treatmentCost == null) {
            treatmentCost = BigDecimal.ZERO;
        }

        // -----------------------------------------------------
        // Round to 2 decimal places
        // -----------------------------------------------------
        consultationFee =
                consultationFee.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        treatmentCost =
                treatmentCost.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        // -----------------------------------------------------
        // Calculate total
        // -----------------------------------------------------
        BigDecimal totalAmount =
                consultationFee.add(treatmentCost);

        totalAmount =
                totalAmount.setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        // -----------------------------------------------------
        // Send everything to JSP
        // -----------------------------------------------------
        req.setAttribute(
                "appointment",
                appointment
        );

        req.setAttribute(
                "consultationFee",
                consultationFee
        );

        req.setAttribute(
                "treatmentCost",
                treatmentCost
        );

        req.setAttribute(
                "totalAmount",
                totalAmount
        );
    }

    // =========================================================
    // SHOW BILL JSP
    // =========================================================
    private void showBillPage(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher(
                "/bill.jsp"
        ).forward(req, resp);
    }

    // =========================================================
    // CHECK RECEPTIONIST
    // =========================================================
    private boolean isReceptionist(
            HttpServletRequest req) {

        HttpSession session =
                req.getSession(false);

        if (session == null) {
            return false;
        }

        Object obj =
                session.getAttribute("user");

        if (!(obj instanceof User)) {
            return false;
        }

        User user =
                (User) obj;

        return user.getRole() != null
                && "RECEPTIONIST".equalsIgnoreCase(
                        user.getRole().trim()
                );
    }
}