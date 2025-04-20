<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="ca.bcit.comp3601.a01325163_assign2.employee.domain.Employee"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<p><strong>${listMsg}</strong><p>
<table border=1>
    <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Date of Birth</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <td>${employee.id}</td>
                <td>${fn:substring(employee.firstName, 0, 20)}</td>
				<td>${fn:substring(employee.lastName, 0, 20)}</td>
                <td>${employee.dob}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
