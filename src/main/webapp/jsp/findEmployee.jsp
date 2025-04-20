<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action="" method="post">
    <input type="hidden" name="action" value="find">
    <div class="form-row">
    <label for="id">Employee ID:</label>
    <input type="text" name="id"><br>
    </div>

    <button type="submit">Find Employee</button>
</form>
<c:if test="${not empty findMsg}">
    <div>
        <strong>${findMsg}</strong> 
    </div>
    <div class="error">
        <strong>${errorMsg}</strong> 
    </div>
</c:if>
<c:if test="${not empty foundEmployee}">
    <p>ID: ${foundEmployee.id}</p>
    <p>First Name: ${foundEmployee.firstName}</p>
    <p>Last Name: ${foundEmployee.lastName}</p>
    <p>Date of Birth: ${foundEmployee.dob}</p>
</c:if>