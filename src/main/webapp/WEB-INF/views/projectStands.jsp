<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="project" type="ru.bsc.test.at.executor.model.Project"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
        <li class="breadcrumb-item active">Project stands</li>
    </ol>

    <h4>Project ${project.id}. ${project.name}</h4>

    <hr />
    <h4>Project stands</h4>
    <form id="save-scenario-stands-form" onsubmit="return false;">
        <table class="table table-condensed">
            <c:forEach items="${project.standList}" var="stand" varStatus="status">
                <tr>
                    <td>
                        <input type="hidden" name="stand[${status.index}][id]" value="${stand.id}"/>
                        <input type="hidden" name="stand[${status.index}][project][id]" value="${stand.project.id}"/>
                        ${stand.id}
                    </td>
                    <td style="width: 99%;">
                        <label>
                            Service URL
                            <input class="form-control" type="text" name="stand[${status.index}][serviceUrl]" value="${stand.serviceUrl}"/>
                        </label>
                        <label>
                            WireMock URL
                            <input class="form-control" type="text" name="stand[${status.index}][wireMockUrl]" value="${stand.wireMockUrl}"/>
                        </label>
                        <label>
                            Database URL
                            <input class="form-control" type="text" name="stand[${status.index}][dbUrl]" value="${stand.dbUrl}"/>
                        </label>
                        <label>
                            Database user
                            <input class="form-control" type="text" name="stand[${status.index}][dbUser]" value="${stand.dbUser}"/>
                        </label>
                        <label>
                            Database password
                            <input class="form-control" type="password" name="stand[${status.index}][dbPassword]" value="${stand.dbPassword}"/>
                        </label>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <button class="btn btn-default" id="save-scenario-stands">Save</button> <span id="save-scenario-stands-state"></span>
    </form>

    <h4>Add stand</h4>
    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/add-stand">
        <table>
            <tr><td>Service URL:</td><td><input class="form-control" name="serviceUrl"></td></tr>
            <tr><td></td><td><input class="btn btn-default" type="submit" value="Add"></td></tr>
        </table>
    </form>

</t:wrapper>
