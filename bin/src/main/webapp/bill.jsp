<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Calculate Bill</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container form-card">
<h2>Calculate Bill</h2>
<form onsubmit="calculateBill(); return false;">
<label>Consultation Fee</label><input id="consultation" type="number" step="0.01" min="0" required>
<label>Treatment Cost</label><input id="treatment" type="number" step="0.01" min="0" required>
<button type="submit">Calculate Bill</button>
</form>
<h3 id="total"></h3>
<button onclick="window.print()">Print Bill</button>
</div>
<script>
function calculateBill(){
 const c=parseFloat(document.getElementById('consultation').value)||0;
 const t=parseFloat(document.getElementById('treatment').value)||0;
 document.getElementById('total').innerText='Total Bill: '+(c+t).toFixed(2);
}
</script>
</body>
</html>
