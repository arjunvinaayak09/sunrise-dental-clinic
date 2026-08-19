<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>

<html>

<head>

    <title>Calculate Bill - Sunrise Dental Clinic</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
        }

        .nav {
            background: white;
            padding: 18px 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .nav b {
            font-size: 20px;
        }

        .nav a {
            margin-left: 25px;
            text-decoration: none;
        }

        .logout {
            float: right;
        }

        .container {
            width: 90%;
            max-width: 900px;
            margin: 30px auto;
        }

        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.1);
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 11px;
            box-sizing: border-box;
            margin-top: 5px;
        }

        button {
            padding: 11px 20px;
            margin-top: 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .find {
            background: #0d6efd;
            color: white;
        }

        .print {
            background: #212529;
            color: white;
        }

        .details {
            margin-top: 25px;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 8px;
        }

        .details p {
            margin: 12px 0;
            font-size: 16px;
        }

        .details hr {
            margin: 20px 0;
            border: 0;
            border-top: 1px solid #ccc;
        }

        .amount {
            font-weight: bold;
            font-size: 17px !important;
        }

        .total-amount {
            font-size: 20px !important;
            font-weight: bold;
            padding: 12px;
            margin-top: 15px !important;
            border-radius: 5px;
            background: #e8f5e9;
        }

        .error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            margin-top: 20px;
            border-radius: 5px;
        }

        .error-title {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .success {
            background: #d4edda;
            color: #155724;
            padding: 15px;
            margin-top: 20px;
            border-radius: 5px;
        }

        .bill {
            border: 2px solid #333;
            padding: 30px;
            margin-top: 30px;
        }

        .clinic-name {
            text-align: center;
            font-size: 28px;
            font-weight: bold;
        }

        .bill-title {
            text-align: center;
            font-size: 20px;
            margin-bottom: 25px;
        }

        .bill-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .bill-table th,
        .bill-table td {
            border: 1px solid #ccc;
            padding: 12px;
        }

        .bill-table th {
            text-align: left;
        }

        .total {
            text-align: right;
            font-size: 22px;
            font-weight: bold;
            margin-top: 20px;
        }

        /* =====================================================
           PRINT STYLES
           ===================================================== */

        @media print {

            .nav,
            .search-card,
            .no-print {
                display: none !important;
            }

            body {
                background: white;
            }

            .container {
                width: 100%;
                margin: 0;
            }

            .card {
                box-shadow: none;
                padding: 0;
            }

            .bill {
                border: 1px solid #333;
            }

        }

    </style>

</head>


<body>


<!-- =========================================================
     NAVIGATION
     ========================================================= -->

<div class="nav">

    <b>Sunrise Dental Clinic</b>

    <a href="<%= request.getContextPath() %>/dashboard">
        Dashboard
    </a>

    <a class="logout"
       href="<%= request.getContextPath() %>/logout">
        Logout
    </a>

</div>



<div class="container">


<!-- =========================================================
     SEARCH APPOINTMENT
     ========================================================= -->

<div class="card search-card">

    <h2>Calculate Bill</h2>

    <form method="get"
          action="<%= request.getContextPath() %>/bill">

        <label>
            Appointment ID
        </label>

        <input
            type="number"
            name="appointmentId"
            min="1"
            required
            placeholder="Enter Appointment ID"
            value="<%= request.getParameter("appointmentId") != null
                    ? request.getParameter("appointmentId")
                    : "" %>"
        >

        <button
            class="find"
            type="submit">

            Find Appointment

        </button>

    </form>


    <!-- =====================================================
         ERROR MESSAGE
         ===================================================== -->

    <%

        String errorMessage =
                (String) request.getAttribute("errorMessage");

        if (errorMessage != null) {

    %>

        <div class="error">

            <div class="error-title">
                Error
            </div>

            <%= errorMessage %>

        </div>

    <%

        }

    %>

</div>



<!-- =========================================================
     GET APPOINTMENT
     ========================================================= -->

<%

    Object appointment =
            request.getAttribute("appointment");


    /*
     * These values must be loaded by your Servlet
     * from the database.
     */

     java.math.BigDecimal consultationFee =
     (java.math.BigDecimal) request.getAttribute("consultationFee");

java.math.BigDecimal treatmentCost =
     (java.math.BigDecimal) request.getAttribute("treatmentCost");

java.math.BigDecimal totalAmount =
     (java.math.BigDecimal) request.getAttribute("totalAmount");


    Boolean billSaved =
            (Boolean) request.getAttribute("billSaved");


    if (appointment != null) {

%>


<!-- =========================================================
     PATIENT DETAILS + DATABASE FEES
     ========================================================= -->

<div class="card">

    <h2>Patient Details</h2>


    <!-- =====================================================
         PRINTABLE DETAILS
         ===================================================== -->

    <div class="details" id="patientDetails">


        <p>
            <strong>Appointment ID:</strong>
            ${appointment.appointmentId}
        </p>


        <p>
            <strong>Patient Name:</strong>
            ${appointment.patientName}
        </p>


        <p>
            <strong>Patient Number:</strong>
            ${appointment.contactNumber}
        </p>


        <p>
            <strong>Address:</strong>
            ${appointment.address}
        </p>


        <p>
            <strong>Dentist Name:</strong>
            ${appointment.dentistName}
        </p>


        <p>
            <strong>Treatment Type:</strong>
            ${appointment.treatmentType}
        </p>


        <p>
            <strong>Appointment Date:</strong>
            ${appointment.appointmentDate}
        </p>


        <p>
            <strong>Appointment Time:</strong>
            ${appointment.appointmentTime}
        </p>


        <hr>


        <!-- =================================================
             CONSULTATION FEE FROM DATABASE
             ================================================= -->

        <p class="amount">

            <strong>Consultation Fee:</strong>

            <%
                if (consultationFee != null) {
            %>

                ₹ <%= String.format(
                        "%.2f",
                        consultationFee
                ) %>

            <%
                } else {
            %>

                Not Available

            <%
                }
            %>

        </p>


        <!-- =================================================
             TREATMENT COST FROM DATABASE
             ================================================= -->

        <p class="amount">

            <strong>Treatment Cost:</strong>

            <%
                if (treatmentCost != null) {
            %>

                ₹ <%= String.format(
                        "%.2f",
                        treatmentCost
                ) %>

            <%
                } else {
            %>

                Not Available

            <%
                }
            %>

        </p>


        <!-- =================================================
             TOTAL AMOUNT
             ================================================= -->

        <%
            if (totalAmount != null) {
        %>

        <p class="total-amount">

            <strong>Total Amount:</strong>

            ₹ <%= String.format(
                    "%.2f",
                    totalAmount
            ) %>

        </p>

        <%
            }
        %>


    </div>


    <!-- =====================================================
         PRINT DETAILS BUTTON
         ===================================================== -->


</div>



<!-- =========================================================
     PRINTABLE BILL
     ========================================================= -->

<%

    if (totalAmount != null) {

%>

<div class="card">

    <div class="bill">


        <!-- CLINIC NAME -->

        <div class="clinic-name">
            SUNRISE DENTAL CLINIC
        </div>


        <!-- BILL TITLE -->

        <div class="bill-title">
            PATIENT BILL
        </div>


        <hr>


        <!-- =================================================
             PATIENT INFORMATION
             ================================================= -->

        <p>
            <strong>Appointment ID:</strong>
            ${appointment.appointmentId}
        </p>


        <p>
            <strong>Patient Name:</strong>
            ${appointment.patientName}
        </p>


        <p>
            <strong>Patient Number:</strong>
            ${appointment.contactNumber}
        </p>


        <p>
            <strong>Address:</strong>
            ${appointment.address}
        </p>


        <p>
            <strong>Dentist Name:</strong>
            ${appointment.dentistName}
        </p>


        <p>
            <strong>Treatment Type:</strong>
            ${appointment.treatmentType}
        </p>


        <p>
            <strong>Appointment Date:</strong>
            ${appointment.appointmentDate}
        </p>


        <p>
            <strong>Appointment Time:</strong>
            ${appointment.appointmentTime}
        </p>


        <!-- =================================================
             BILL TABLE
             ================================================= -->

        <table class="bill-table">

            <tr>

                <th>
                    Description
                </th>

                <th>
                    Amount
                </th>

            </tr>


            <tr>

                <td>
                    Consultation Fee
                </td>

                <td>

                    ₹ <%= String.format(
                            "%.2f",
                            consultationFee
                    ) %>

                </td>

            </tr>


            <tr>

                <td>
                    Treatment Cost
                </td>

                <td>

                    ₹ <%= String.format(
                            "%.2f",
                            treatmentCost
                    ) %>

                </td>

            </tr>

        </table>


        <!-- =================================================
             TOTAL
             ================================================= -->

        <div class="total">

            Total Amount:

            ₹ <%= String.format(
                    "%.2f",
                    totalAmount
            ) %>

        </div>


        <hr>


        <p style="text-align:center;">

            Thank you for visiting

            <br>

            Sunrise Dental Clinic

        </p>


    </div>


    <!-- =====================================================
         SUCCESS MESSAGE
         ===================================================== -->

    <%

        if (Boolean.TRUE.equals(billSaved)) {

    %>

        <div class="success no-print">

            Bill calculated and saved successfully.

        </div>

    <%

        }

    %>


    <!-- =====================================================
         PRINT BILL BUTTON
         ===================================================== -->

    <div class="no-print">

        <button
            type="button"
            class="print"
            onclick="window.print()">

            Print Bill

        </button>

    </div>


</div>


<%

    }

%>


<%

    }

%>


</div>



<!-- =========================================================
     PRINT PATIENT DETAILS JAVASCRIPT
     ========================================================= -->

<script>

function printPatientDetails() {

    var printContent =
        document.getElementById("patientDetails").innerHTML;

    var printWindow =
        window.open(
            "",
            "",
            "width=800,height=600"
        );


    printWindow.document.write(`

        <!DOCTYPE html>

        <html>

        <head>

            <title>
                Patient Appointment Details
            </title>


            <style>

                body {
                    font-family: Arial, sans-serif;
                    padding: 40px;
                }


                .clinic-name {
                    text-align: center;
                    font-size: 28px;
                    font-weight: bold;
                    margin-bottom: 10px;
                }


                .title {
                    text-align: center;
                    font-size: 20px;
                    margin-bottom: 25px;
                }


                .details {
                    border: 1px solid #333;
                    padding: 25px;
                    margin-top: 20px;
                }


                .details p {
                    font-size: 16px;
                    margin: 12px 0;
                }


                .details hr {
                    margin: 20px 0;
                }


                .total-amount {
                    font-size: 20px;
                    font-weight: bold;
                    padding: 12px;
                    border: 1px solid #333;
                }


                hr {
                    margin: 20px 0;
                }

            </style>

        </head>


        <body>


            <div class="clinic-name">

                SUNRISE DENTAL CLINIC

            </div>


            <div class="title">

                PATIENT APPOINTMENT DETAILS

            </div>


            <hr>


            <div class="details">

                ${printContent}

            </div>


            <br>


            <p style="text-align:center;">

                Thank you for visiting
                Sunrise Dental Clinic

            </p>


        </body>

        </html>

    `);


    printWindow.document.close();

    printWindow.focus();

    printWindow.print();

    printWindow.close();

}

</script>



</body>

</html>