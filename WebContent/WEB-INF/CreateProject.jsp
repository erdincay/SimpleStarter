<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions" %>

<!DOCTYPE html>
<html lang="en">
<c:set value="Create A Project" var="title" />
<%@ include file="Head.jsp"%>
<body>
	<%@ include file="Login_out.jsp"%>
	<form action="CreateProject" method="post">
		<table>
			<caption>Create Project - Basic Information</caption>
			<tbody>
				<tr>
					<td>Project Title:</td>
					<td><input type="text" name="title" required
						placeholder="Your Project Name" /></td>
				</tr>
				<tr>
					<td class="top">Project Description:</td>
					<td><textarea name="description" rows="5" cols="40"> ${self:getTestStr()} </textarea></td>
				</tr>
				<tr>
					<td>Funding Target:</td>
					<td>$<input type="number" step="any" min="0" name="target"
						required placeholder="number:1+" /></td>
				</tr>
				<tr>
					<td>Start Date:</td>
					<td><input type="text" name="date" required
						placeholder="Month / Date / Year" />(MM/dd/yyyy)</td>
				</tr>
				<tr>
					<td>Funding Duration:</td>
					<td><input type="number" step="any" min="0"
						name="duration" required placeholder="number:1+" />Days</td>
				</tr>
			</tbody>
		</table>
		<p>
			<input class="alignToTable" type="submit"
				name="${self:getSubmitAttrName()}"
				value="${self:getNextAttrName()}" /> 
			<input class="alignToTable" type="submit"
				name="${self:getSubmitAttrName()}"
				value="${self:getCancelAttrName()}" formnovalidate />
		</p>
	</form>
	<%@ include file="Scripts.jsp"%>
</body>
</html>