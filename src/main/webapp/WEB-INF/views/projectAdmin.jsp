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
    <h4>Admin</h4>
    <form method="post" action="${pageContext.request.contextPath}/project/${project.id}/admin/parse-all-scenarios-expected-service-requests-jmba">
        <div class="form-group">
            <label for="expectedRequestsBaseDir">Name:</label>
            <input class="form-control" id="expectedRequestsBaseDir" name="expectedRequestsBaseDir" value="c:\projects\jmba-at\src\test\resources\expectedJmbaToWsRequests\">
        </div>
        <input class="btn btn-default" type="submit" value="Parse all scenarios: expected service requests (JMBA)">
    </form>

</t:wrapper>
