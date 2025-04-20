<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Management</title>
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>

<h1>Dunder-Mifflin Paper Company</h1>
<!-- Two Column Layout -->
<div class="container">

    <!-- Left Column: Employee Table -->
    <div class="column">
        <jsp:include page="/jsp/employees.jsp" />
    </div>

    <!-- Right Column: CRUD Forms -->
    <div class="column">
        <!--<h2>Manage Employees</h2>-->
        
        <h3>Add Employee</h3>
        <jsp:include page="/jsp/addEmployee.jsp" />

        <h3>Find Employee</h3>
        <jsp:include page="/jsp/findEmployee.jsp" />

        <h3>Delete Employee</h3>
        <jsp:include page="/jsp/deleteEmployee.jsp" />
    </div>
</div>

</body>
</html>