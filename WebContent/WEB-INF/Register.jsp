<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="registerClass" value="register"/>

<!DOCTYPE html>
<html lang='en'>
<%@ include file="Head.jsp" %>
<body>
<%@ include file="Login_out.jsp" %>

<c:if test="${not empty errorMessage && fn:length(errorMessage) gt 0}">
    <div id="errorMessage">
        <c:forEach items="${errorMessage}" var="em">
            <em>${ em }</em>
            <br/>
        </c:forEach>
    </div>
</c:if>

<form action="Register" method="post">
    <div class=${ registerClass }>
        <label>Username:</label> <input type="text"
                                        name="${self:getUsrAttrName()}" required/>
    </div>
    <div class=${ registerClass }>
        <label>Password:</label> <input type="password"
                                        name="${self:getPwdAttrName()}" required/>
    </div>
    <div class=${ registerClass }>
        <label>Retype Password:</label> <input type="password"
                                               name="retypePassword" required/>
    </div>

    <div class=${ registerClass }>
        <label>First Name:</label> <input type="text" name="firstName"
                                          required/>
    </div>
    <div class=${ registerClass }>
        <label>Last Name:</label> <input type="text" name="lastName" required/>
    </div>
    <div class=${ registerClass }>
        <input type="submit" name="${self:getSubmitAttrName()}"
               value="${self:getConfirmAttrName()}"/> <input type="submit"
                                                             name="${self:getSubmitAttrName()}"
                                                             value="${self:getCancelAttrName()}" formnovalidate/>
    </div>
</form>

<%@ include file="Scripts.jsp" %>
</body>
</html>