<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item active">Import project from YAML</li>
    </ol>

    <%--@elvariable id="projects" type="java.util.List<ru.bsc.test.autotester.model.Project>"--%>
    <h4>Import project from YAML</h4>
    <form method="post" action="${pageContext.request.contextPath}/import-project-from-yaml" enctype="multipart/form-data">
        <label>
            YAML file
            <input type="file" name="yamlFile" class="form-control-file"/>
        </label>
        <br/>
        <input type="submit" value="Import"/>
    </form>
</t:wrapper>
