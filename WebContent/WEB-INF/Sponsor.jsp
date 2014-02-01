<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="self" uri="http://www.cs320.com/jsp/selfFunctions" %>

<!DOCTYPE html>
<html lang="en">
<c:set value="Sponsor" var="title"/>
<%@ include file="Head.jsp" %>
<body>
<%@ include file="Login_out.jsp" %>
<form action="Sponsor" method="post">
    <table class="hiddenTable">
        <tbody>
        <tr>
            <th>Enter your pledge amount:</th>
            <td></td>
        </tr>
        <tr>
            <td>$<input type="number" step="any" min="0" name="amount"
                        id="amount" required="required" placeholder="number:1+"/></td>
            <td><label for="amount">It's up to you.</label></td>
        </tr>
        <tr>
            <th>Select your reward:</th>
            <td></td>
        </tr>
        <tr>
            <td><input type="radio" name="reward" value="0" id="reward0"
                       checked="checked"/><label for="reward0">No reward:</label></td>
            <td><label for="reward0">No thanks, I just want to
                help the project.</label></td>
        </tr>
        <c:forEach items="${ prj.rewards }" var="rd" varStatus="status">
            <tr>
                <td><input type="radio" name="reward" value=
                    <c:out value='${rd.pAmount}'/>
                        id="reward${status.getIndex() + 1}"/><label
                        for="reward${status.getIndex() + 1}">Pledge $ <c:out value="${rd.pAmount}"/>:</label></td>
                <td><label for="reward${status.getIndex() + 1}"><c:out value='${
								rd.rDescription }'/></label></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>
        <input type="hidden" name="${self:getIdAttrName()}"
               value="${ prj.identity }"/> <input class="alignToTable"
                                                  type="submit" name="${ self:getSubmitAttrName()}"
                                                  value="${ self:getNextAttrName() }"/> <input class="alignToTable"
                                                                                               type="submit"
                                                                                               name="${ self:getSubmitAttrName()}"
                                                                                               value="${ self:getCancelAttrName() }"
                                                                                               formnovalidate/>
    </p>
</form>
<%@ include file="Scripts.jsp" %>
</body>
</html>