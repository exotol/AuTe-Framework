<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="scenario" type="ru.bsc.test.autotester.model.Scenario"--%>
    <%--@elvariable id="project" type="ru.bsc.test.autotester.model.Project"--%>
    <%--@elvariable id="projectScenarios" type="java.util.List<ru.bsc.test.autotester.model.Scenario>"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/scenario/${scenario.id}">${scenario.name}</a></li>
        <li class="breadcrumb-item active">Scenario settings</li>
    </ol>

    <h4>Scenario ${scenario.id}. ${scenario.name}</h4>

    <hr />
    <h4>Settings</h4>
    <form method="post" action="${pageContext.request.contextPath}/scenario/${scenario.id}/settings">
        <table>
            <tr><td>Name:</td><td><input class="form-control" type="text" name="name" value="<c:out value="${scenario.name}"/>"/></td></tr>
            <tr>
                <td>Before scenario</td>
                <td>
                    <select class="form-control" name="beforeScenarioId">
                        <option ${scenario.beforeScenario.id eq null ? 'selected' : ''} value="">From project settings (default)</option>
                        <option ${scenario.beforeScenario.id eq -1 ? 'selected' : ''} value="-1">Disabled</option>
                        <optgroup label="Scenarios">
                            <c:forEach items="${projectScenarios}" var="scenarioItem">
                                <option value="${scenarioItem.id}" ${scenario.beforeScenario.id eq scenarioItem.id ? 'selected' : ''}>${scenarioItem.name}</option>
                            </c:forEach>
                        </optgroup>
                    </select>
                </td>
            </tr>
            <tr>
                <td>After scenario</td>
                <td>
                    <select class="form-control" name="afterScenarioId">
                        <option ${scenario.afterScenario.id eq null ? 'selected' : ''} value="">From project settings (default)</option>
                        <option ${scenario.afterScenario.id eq -1 ? 'selected' : ''} value="-1">Disabled</option>
                        <optgroup label="Scenarios">
                            <c:forEach items="${projectScenarios}" var="scenarioItem">
                                <option value="${scenarioItem.id}" ${scenario.afterScenario.id eq scenarioItem.id ? 'selected' : ''}>${scenarioItem.name}</option>
                            </c:forEach>
                        </optgroup>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Group</td>
                <td>
                    <select class="form-control" name="scenarioGroupId">
                        <option value="">-</option>
                        <c:forEach items="${project.scenarioGroups}" var="scenarioGroup">
                            <option value="${scenarioGroup.id}" ${scenario.scenarioGroup.id eq scenarioGroup.id ? 'selected' : ''}>${scenarioGroup.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <input type="submit" class="btn btn-default" value="Save">
    </form>

    <hr/>
    <form style="display:none;" method="post" action="${pageContext.request.contextPath}/scenario/${scenario.id}/import-expected-service-requests" onsubmit="return confirm('Confirm operation');">
        <div class="form-group">
            <label for="expectedRequestsBaseDir">Name:</label>
            <input class="form-control" id="expectedRequestsBaseDir" name="expectedRequestsBaseDir" value="c:\projects\jmba-at\src\test\resources\expectedJmbaToWsRequests\">
        </div>
        <input type="submit" class="btn btn-default" value="Parse expected service requests from jmba">
    </form>
</t:wrapper>
