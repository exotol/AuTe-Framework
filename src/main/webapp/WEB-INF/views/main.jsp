<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <ol class="breadcrumb">
        <li class="breadcrumb-item active">Home</li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/import-project-from-yaml">Import project form YAML</a></li>
    </ol>

    <%--@elvariable id="projects" type="java.util.List<ru.bsc.test.autotester.model.Project>"--%>
    <h4>Projects</h4>
    <table class="table table-condensed">
        <c:forEach items="${projects}" var="project">
            <tr>
                <td><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></td>
                <td>${project.serviceUrl}</td>
            </tr>
        </c:forEach>
    </table>
</t:wrapper>
