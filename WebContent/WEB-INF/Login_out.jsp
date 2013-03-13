<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions"%>

<c:choose>
	<c:when test="${empty sessionScope[self:getUsrAttrName()]}">
		<div id='log'>
			<a href='Login' title='To Log In'>Login</a>
		</div>
	</c:when>
	<c:otherwise>
		<div id='log'>
			<a href='Logout' title='To Log Out'>Logout</a>
		</div>
	</c:otherwise>
</c:choose>