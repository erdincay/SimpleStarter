<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions" %>

<!DOCTYPE html>
<html lang="en">
<c:set value="Login" var="title"/>
<%@ include file="Head.jsp" %>
<body>
<form action="Login" method="post">
    <table class="hiddenTable">
        <tbody>
        <tr>
            <th>Username:</th>
            <td><input type="text" name="${self:getUsrAttrName()}"
                       required/></td>
        </tr>
        <tr>
            <th>Password:</th>
            <td><input type="password" name="${self:getPwdAttrName()}"
                       required/></td>
        </tr>
        <tr>
            <td><input type="submit" name="${self:getSubmitAttrName()}"
                       value="${self:getConfirmAttrName()}"/></td>
            <td><input type="submit" name="${self:getSubmitAttrName()}"
                       value="${self:getCancelAttrName()}" formnovalidate/></td>
        </tr>
        </tbody>
    </table>
</form>
<%@ include file="Scripts.jsp" %>
</body>
</html>