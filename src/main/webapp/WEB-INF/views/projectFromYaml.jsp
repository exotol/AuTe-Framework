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
        <div class="form-group">
            <label>
                YAML file
                <input style="background-color: lightgray; height: 50px;" type="file" name="yamlFile" class="form-control-file"/>
            </label>
        </div>
        <input class="btn btn-default" type="submit" value="Import"/>
    </form>
</t:wrapper>
