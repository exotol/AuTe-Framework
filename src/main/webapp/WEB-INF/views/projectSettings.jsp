<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="project" type="ru.bsc.test.autotester.model.Project"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
        <li class="breadcrumb-item active">Project settings</li>
    </ol>

    <h4>Project ${project.id}. ${project.name}</h4>

    <hr />
    <h4>Settings</h4>
    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/settings">
        <table>
            <tr><td>Name:</td><td><input class="form-control" type="text" name="name" value="<c:out value="${project.name}"/>"/></td></tr>
            <tr><td>Base URL:</td><td><input class="form-control" type="text" name="serviceUrl" value="<c:out value="${project.serviceUrl}"/>"/></td></tr>
            <tr>
                <td>Before scenario</td>
                <td>
                    <select class="form-control" name="beforeScenarioId">
                        <option value="">-</option>
                        <%--@elvariable id="projectScenarios" type="java.util.List<ru.bsc.test.autotester.model.Scenario>"--%>
                        <c:forEach items="${projectScenarios}" var="scenario">
                            <option value="${scenario.id}" ${project.beforeScenarioId eq scenario.id ? 'selected' : ''}>${scenario.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>After scenario</td>
                <td>
                    <select class="form-control" name="afterScenarioId">
                        <option value="">-</option>
                        <%--@elvariable id="projectScenarios" type="java.util.List<ru.bsc.test.autotester.model.Scenario>"--%>
                        <c:forEach items="${projectScenarios}" var="scenario">
                            <option value="${scenario.id}" ${project.afterScenarioId eq scenario.id ? 'selected' : ''}>${scenario.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <input class="btn" type="submit" value="Save">
    </form>

    <hr />
    <h4>Import from excel</h4>
    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/import-from-excel" enctype="multipart/form-data">
        <input type="file" name="excelFile"/><br/>
        <select class="form-control" name="scenarioGroup">
            <option value="">-</option>
            <%--@elvariable id="scenarioGroups" type="java.util.List<ru.bsc.test.autotester.model.ScenarioGroup>"--%>
            <c:forEach items="${scenarioGroups}" var="group">
                <option value="${group.id}">${group.name}</option>
            </c:forEach>
        </select>
        <input class="btn" type="submit" value="Import">
    </form>


</t:wrapper>
