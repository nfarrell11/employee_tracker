<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action="" method="post">
    <input type="hidden" name="action" value="add">
    
    <div class="form-row">
	    <label for="id">ID:</label>
	    <input type="text" name="id" id="id" value="${ params['id'] }">
    </div>
    <div class="error">${validationErrors['id']}</div>
    <div class="form-row">
	    <label for="firstName">First Name:</label>
	    <input type="text" name="firstName" id="firstName" value="${ params['firstName'] }">   
    </div>
	<div class="error">${validationErrors['firstName']}</div>
	<div class="form-row">
	    <label for="lastName">Last Name:</label>
	    <input type="text" name="lastName" id="lastName" value="${ params['lastName'] }">  
    </div>
    <div class="error">${validationErrors['lastName']}</div>
	<div class="form-row">
	    <label for="dob">Date of Birth:</label>
	    <input type="text" name="dob" id="dob" value="${ params['dob'] }">
    </div>
    <div class="error">${validationErrors['dob']}</div>

    <button type="submit">Add Employee</button>
</form>
<c:if test="${not empty addMsg}">
	<div>
	  <strong>${addMsg}</strong>
	</div>
</c:if>