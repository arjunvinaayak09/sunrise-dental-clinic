package com.sunrise.controller;

import com.sunrise.model.Appointment;
import com.sunrise.service.AppointmentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/appointments")
public class AppointmentApiServlet extends HttpServlet {
    private final AppointmentService service = new AppointmentService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().print("{\"error\":\"Authentication required\"}");
            return;
        }
        try {
            Appointment a = service.find(req.getParameter("appointmentNo"));
            resp.setContentType("application/json");
            if (a == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().print("{\"error\":\"Appointment not found\"}");
                return;
            }
            String json = String.format(
                "{\"appointmentNo\":\"%s\",\"patientName\":\"%s\",\"address\":\"%s\",\"contactNumber\":\"%s\",\"dentistName\":\"%s\",\"treatmentType\":\"%s\",\"appointmentDate\":\"%s\",\"appointmentTime\":\"%s\",\"totalBill\":%s}",
                esc(a.getAppointmentNo()), esc(a.getPatientName()), esc(a.getAddress()),
                esc(a.getContactNumber()), esc(a.getDentistName()), esc(a.getTreatmentType()),
                a.getAppointmentDate(), a.getAppointmentTime(), a.getTotalBill());
            resp.getWriter().print(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().print("{\"error\":\"Server error\"}");
        }
    }

    private String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
