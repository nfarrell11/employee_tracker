<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="" method="post">
    <input type="hidden" name="action" value="delete">
    <div class="form-row">
    	<label for="id">Employee ID:</label>
    	<input type="text" name="id"><br>
    </div>
    <button type="submit">Delete Employee</button>
</form>
<c:if test="${not empty deleteMsg}">
	<div><strong>${deleteMsg}</strong></div>
  	<div class="error"><strong>${errorMsg}</strong></div>
</c:if>