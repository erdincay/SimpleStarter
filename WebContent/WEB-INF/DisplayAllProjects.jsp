<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions"%>

<!DOCTYPE html>
<html lang="en">
<c:set value="All Projects" var="title" />
<%@ include file="Head.jsp"%>
<body>
	<%@ include file="Login_out.jsp"%>
	<c:choose>
		<c:when test="${fn:length(prjs) le 0}">
			<h2>There is no project yet.</h2>
		</c:when>
		<c:otherwise>
			<table>
				<caption>Projects</caption>
				<tbody>
					<tr>
						<th>Project</th>
						<th>Posted By</th>
						<th>Start Date</th>
						<th>Days To Go</th>
						<th>Amount Pledged</th>
						<th>Percentage Funded</th>
					</tr>
					<c:forEach items="${requestScope.prjs}" var="prj"
						varStatus="status">
						<c:if test="${prj.fudLeftCals gt 0}">
							<c:choose>
								<c:when test="${status.getIndex() mod 2 eq 0}">
									<c:set value="" var="classVal" scope="page" />
								</c:when>
								<c:otherwise>
									<c:set value="class='cellcolor'" var="classVal" scope="page" />
								</c:otherwise>
							</c:choose>
							<tr ${classVal}>
								<td><a
									href="DisplayProject?id=<c:out value='${prj.identity}'/>"
									title="project detail"><c:out value="${prj.title}" /></a></td>
								<td><c:out value="${prj.authorName}" /></td>
								<td><fmt:formatDate value="${prj.fudStartDate}"
										pattern="EEE, MMM dd, yyyy" /></td>
								<td><c:out value="${prj.fudLeftCals}" /></td>
								<td><c:out value="${prj.pledged}" /></td>
								<td>${self:transPercent(prj.pledged,prj.fudTarget,3)}</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
	<p class="alignToTable">
		<a href="CreateProject" title="Creat a new project">Create a new
			project</a>
	</p>
	<%@ include file="Scripts.jsp"%>
</body>
</html>