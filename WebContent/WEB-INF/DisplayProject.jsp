<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions"%>

<!DOCTYPE html>
<html lang="en">
<c:set value="${ prj.title }" var="title" />
<%@ include file="Head.jsp"%>
<body>
	<%@ include file="Login_out.jsp"%>

	<div class="bubble">
		<p>Amount Pledged: <c:out value="${prj.pledged}" /></p>
		<p>Percentage Funded:
			${self:transPercent(prj.pledged,prj.fudTarget,3)}</p>
		<c:choose>
			<c:when test="${ nosponsored }">
				<strong><a
					href="Sponsor?${self:getIdAttrName()}=<c:out value='${prj.getIdentity()}'/>"
					title="thanks for sponsoring">Sponsor This Project.</a></strong>
			</c:when>
			<c:otherwise>
				<strong>you have sponsored.</strong>
			</c:otherwise>
		</c:choose>
	</div>

	<table class="hiddenTable">
		<tbody>
			<tr class="center">
				<td><h2><c:out value="${ prj.title }" /></h2></td>
			</tr>
			<tr class="center" id="author">
				<td><c:out value="${ prj.authorName }" /></td>
			</tr>
			<tr>
				<td><c:out value="${ prj.description}" /></td>
			</tr>
			<tr>
				<td><table class="left">
						<tbody>
							<tr>
								<th>Funding Target:</th>
								<td>$<c:out value="${ prj.fudTarget }" /></td>
							</tr>
							<tr>
								<th>Start Date:</th>
								<td><fmt:formatDate value="${prj.fudStartDate}"
										pattern="EEE, MMM dd, yyyy" /></td>
							</tr>
							<tr>
								<th>Funding Duration:</th>
								<td><c:out value="${prj.fudDuration}" /></td>
							</tr>
							<tr>
								<th>Days to Go:</th>
								<td><c:out value="${prj.fudLeftCals}" /></td>
							</tr>
						</tbody>
					</table></td>
			</tr>
			<c:if test="${ fn:length(prj.rewards) gt 0}">
				<tr>
					<td><table>
							<tbody>
								<c:forEach items="${prj.rewards}" var="rd">
									<tr>
										<th>Pledge $<c:out value="${ rd.pAmount }" />:</th>
										<td><c:out value="${ rd.rDescription }" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table></td>
				</tr>
			</c:if>
			<tr>
				<td><a href="DisplayAllProjects" title="show all the projects">All
						Projects</a></td>
			</tr>
		</tbody>
	</table>
	<%@ include file="Scripts.jsp"%>
</body>
</html>