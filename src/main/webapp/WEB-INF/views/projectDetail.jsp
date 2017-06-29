<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="scenarioGroupId" type="java.lang.Long"--%>
    <%--@elvariable id="scenarioGroups" type="java.util.List<ru.bsc.test.autotester.model.ScenarioGroup>"--%>
    <%--@elvariable id="project" type="ru.bsc.test.autotester.model.Project"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>

        <%--@elvariable id="executeResult" type="java.lang.Integer"--%>
        <c:if test="${empty executeResult}">
            <li class="breadcrumb-item active">${project.name}</li>
        </c:if>
        <c:if test="${not empty executeResult}">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
            <li class="breadcrumb-item active">Execute result</li>
        </c:if>
    </ol>

    <%--@elvariable id="scenarios" type="java.util.List<ru.bsc.test.autotester.model.Scenario>"--%>
    <h4>Project ${project.id}. ${project.name}</h4>
    <a href="${pageContext.request.contextPath}/project/${project.id}/settings">Project settings</a>

    <a class="btn btn-default" target="_blank" role="button" style="float: right;" href="${pageContext.request.contextPath}/project/${project.id}/get-yaml">Download project as YAML</a>

    <nav aria-label="Scenario groups">
        <ul class="pagination">
            <li class="${scenarioGroupId eq 0 ? 'active' : ''}"><a href="${pageContext.request.contextPath}/project/${project.id}">All</a></li>
            <c:forEach items="${scenarioGroups}" var="scenarioGroup">
                <li class="${scenarioGroupId eq scenarioGroup.id ? 'active' : ''}"><a href="${pageContext.request.contextPath}/project/${project.id}?scenarioGroupId=${scenarioGroup.id}">${scenarioGroup.name}</a></li>
            </c:forEach>
        </ul>
    </nav>

    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/execute-scenarios">
        <table class="table table-condensed">
            <tr>
                <th style="width: 35px;">
                    <input style="width: 24px; height: 24px; margin: 0;" type="checkbox" onclick="$('input[name=scenarios\\[\\]]').prop('checked', $(this).prop('checked'));" />
                </th>
                <th>Название сценария</th>
                <th>Ошибок при последнем запуске</th>
                <th>Время последнего запуска</th>
            </tr>
            <c:forEach items="${scenarios}" var="scenario">
                <tr>
                    <td>
                        <input ${scenario.lastRunFailures eq 0 ? '' : 'checked'} type="checkbox" style="width: 24px; height: 24px; margin: 0;" name="scenarios[]" value="${scenario.id}">
                    </td>
                    <td><a href="${pageContext.request.contextPath}/scenario/${scenario.id}">${scenario.name}</a></td>
                    <td style="color: ${scenario.lastRunFailures eq 0 ? 'gray' : 'red'}">${scenario.lastRunFailures}</td>
                    <td>${scenario.lastRunAt}</td>
                </tr>
                <c:if test="${not empty scenario.stepResults}">
                    <tr>
                        <td style="border-top: 0;"></td>
                        <td colspan="3">
                            <c:forEach items="${scenario.stepResults}" var="stepResult">
                                ${stepResult.step.requestMethod} <small style="color: gray;">${project.serviceUrl}</small>${stepResult.requestUrl}
                                <span style="color: ${stepResult.result eq 'OK' ? 'darkgreen' : 'red'};">${stepResult.result}</span>


                                <a href="#" onclick="$(this).next().toggle(); return false;">Details</a>
                                <div style="display: none;">
                                    <a href="${pageContext.request.contextPath}/step/${stepResult.step.id}" style="font-size: smaller;" target="_blank">Edit step</a>
                                    <h4 style="font-weight: bold;">Request body</h4>
                                    <textarea rows="5" readonly style="width: 97.5%;"><c:out value="${stepResult.requestBody}"/></textarea>
                                    <table style="width: 100%;">
                                        <tr>
                                            <th>Actual</th>
                                            <th>Expected</th>
                                        </tr>
                                        <tr>
                                            <td width="50%">
                                                <textarea rows="10" readonly style="width: 95%;"><c:out value="${stepResult.actual}"/></textarea>
                                            </td>
                                            <td>
                                                <textarea rows="10" readonly style="width: 95%;"><c:out value="${stepResult.expected}"/></textarea>
                                            </td>
                                        </tr>
                                    </table>
                                    <pre><c:out value="${stepResult.details}"/></pre>
                                </div>
                                <hr style="margin-top: 8px; margin-bottom: 4px;"/>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
        <input type="submit" class="btn btn-default" value="Execute selected scenarios"/>
        <input type="submit" style="float: right;" class="btn btn-default" value="Download selected scenarios as .xlsx" formaction="${pageContext.request.contextPath}/project/${project.id}/export-to-excel"/>
    </form>

    <c:if test="${empty executeResult}">
        <hr />
        <h4>Add new scenario</h4>
        <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/add-scenario">
            <table>
                <tr><td>Name:</td><td><input class="form-control" name="name"></td></tr>
                <tr>
                    <td>Scenario group:</td>
                    <td>
                        <select class="form-control" name="scenarioGroupId">
                            <option value="">-</option>
                            <%--@elvariable id="scenarioGroups" type="java.util.List<ru.bsc.test.autotester.model.ScenarioGroup>"--%>
                            <c:forEach items="${scenarioGroups}" var="group">
                                <option value="${group.id}">${group.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr><td></td><td><input class="btn btn-default" type="submit" value="Add"></td></tr>
            </table>
        </form>
    </c:if>
</t:wrapper>
