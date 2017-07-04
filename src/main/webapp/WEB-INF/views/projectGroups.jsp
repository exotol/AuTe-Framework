<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="project" type="ru.bsc.test.autotester.model.Project"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
        <li class="breadcrumb-item active">Project groups</li>
    </ol>

    <h4>Project ${project.id}. ${project.name}</h4>

    <hr />
    <h4>Project groups</h4>
    <form id="save-scenario-groups-form" onsubmit="return false;">
        <table class="table table-condensed">
            <c:forEach items="${project.scenarioGroups}" var="group" varStatus="status">
                <tr>
                    <td>
                        <input type="hidden" name="scenarioGroup[${status.index}][id]" value="${group.id}"/>
                        <input type="hidden" name="scenarioGroup[${status.index}][project][id]" value="${group.project.id}"/>
                        ${group.id}
                    </td>
                    <td style="width: 99%;">
                        <input class="form-control" type="text" name="scenarioGroup[${status.index}][name]" value="${group.name}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <button class="btn btn-default" id="save-scenario-groups">Save</button> <span id="save-scenario-groups-state"></span>
    </form>

    <h4>Add group</h4>
    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/add-group">
        <table>
            <tr><td>Name:</td><td><input class="form-control" name="name"></td></tr>
            <tr><td></td><td><input class="btn btn-default" type="submit" value="Add"></td></tr>
        </table>
    </form>

</t:wrapper>
