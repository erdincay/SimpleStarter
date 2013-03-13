<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions" %>

<!DOCTYPE html>
<html lang="en">
<c:set value="Add Rewards" var="title"/>
<%@ include file="Head.jsp"%>
<body>
	<%@ include file="Login_out.jsp"%>
	<form action="AddRewards" method="post">
		<table>
			<caption>Create Project - Add Rewards</caption>
			<tbody>
				<tr>
					<td>Pledge Amount:</td>
					<td><input type="number" step="any" min="0" name="amount" required placeholder="number:1+" /></td>
				</tr>
				<tr>
					<td class="top">Reward Description:</td>
					<td><textarea name="description" rows="5" cols="40">${self:getTestStr()}</textarea></td>
				</tr>
			</tbody>
		</table>
		<p>
			<input type="hidden" name="${self:getIdAttrName()}" value=<c:out value="${prj.identity}"/> />
			<input class="alignToTable" type="submit" name="${self:getSubmitAttrName()}" value="Add"/> 
			<input class="alignToTable" type="submit" name="${self:getSubmitAttrName()}" value="Finish" formnovalidate />
		</p>
	</form>
	<%@ include file="Scripts.jsp"%>
</body>
</html>