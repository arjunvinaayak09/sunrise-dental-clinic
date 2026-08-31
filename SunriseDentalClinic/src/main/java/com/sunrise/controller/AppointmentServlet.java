package com.sunrise.controller;

import com.sunrise.model.Appointment;
import com.sunrise.service.AppointmentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    private final AppointmentService service = new AppointmentService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!loggedIn(req)) { resp.sendRedirect("index.jsp"); return; }
        try {
        	String action = req.getParameter("action");
        	if ("view".equals(action)) {
        	    Appointment a = service.find(req.getParameter("appointmentNo"));
        	    req.setAttribute("appointment", a);
        	    req.getRequestDispatcher("/WEB-INF/views/appointment-details.jsp").forward(req, resp);
        	} else if ("edit".equals(action)) {
        	    // Load the appointment into the register/edit form so the
        	    // receptionist can actually change patient/appointment details.
        	    Appointment a = service.find(req.getParameter("appointmentNo"));
        	    if (a == null) {
        	        req.setAttribute("error", "Appointment not found. Please check the Appointment ID and try again.");
        	        req.getRequestDispatcher("/WEB-INF/views/register-appointment.jsp").forward(req, resp);
        	        return;
        	    }
        	    req.setAttribute("appointment", a);
        	    req.getRequestDispatcher("/WEB-INF/views/register-appointment.jsp").forward(req, resp);
        	} else if ("all".equals(action)) {
        	    req.setAttribute("appointments", service.all());
        	    req.getRequestDispatcher("/WEB-INF/views/reports.jsp").forward(req, resp);
        	} else {
        	    req.getRequestDispatcher("/WEB-INF/views/register-appointment.jsp").forward(req, resp);
        	}
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!loggedIn(req)) { resp.sendRedirect("index.jsp"); return; }
        String action = req.getParameter("action");
        try {
            if ("save".equals(action)) {
                Appointment a = fromRequest(req);
                if ("update".equals(req.getParameter("mode"))) {
                    service.update(a);
                    resp.sendRedirect("appointments?action=view&appointmentNo=" + a.getAppointmentNo() + "&msg=updated");
                } else {
                    service.create(a);
                    resp.sendRedirect("appointments?action=view&appointmentNo=" + a.getAppointmentNo() + "&msg=registered");
                }
            } else if ("delete".equals(action)) {
                service.delete(req.getParameter("appointmentNo"));
                resp.sendRedirect("dashboard?msg=deleted");
            }
        } catch (IllegalArgumentException ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register-appointment.jsp").forward(req, resp);
        } catch (Exception ex) {
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/register-appointment.jsp").forward(req, resp);
        }
    }

    private Appointment fromRequest(HttpServletRequest req) {
        Appointment a = new Appointment();
        a.setAppointmentNo(req.getParameter("appointmentNo"));
        a.setPatientName(req.getParameter("patientName"));
        a.setAddress(req.getParameter("address"));
        a.setContactNumber(req.getParameter("contactNumber"));
        a.setDentistName(req.getParameter("dentistName"));
        a.setTreatmentType(req.getParameter("treatmentType"));
        a.setAppointmentDate(Date.valueOf(req.getParameter("appointmentDate")));
        a.setAppointmentTime(Time.valueOf(req.getParameter("appointmentTime") + ":00"));
        a.setConsultationFee(new BigDecimal(req.getParameter("consultationFee")));
        a.setTreatmentCost(new BigDecimal(req.getParameter("treatmentCost")));
        return a;
    }

    private boolean loggedIn(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null && s.getAttribute("user") != null;
    }
}
