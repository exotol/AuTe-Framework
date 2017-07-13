<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:wrapper>
    <%--@elvariable id="scenario" type="ru.bsc.test.at.executor.model.Scenario"--%>
    <%--@elvariable id="project" type="ru.bsc.test.at.executor.model.Project"--%>
    <%--@elvariable id="stepDetail" type="ru.bsc.test.at.executor.model.Step"--%>
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/project/${project.id}">${project.name}</a></li>
        <c:if test="${empty stepDetail}">
            <li class="breadcrumb-item active">${scenario.name}</li>
        </c:if>
        <c:if test="${not empty stepDetail}">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/scenario/${scenario.id}">${scenario.name}</a></li>
            <li class="breadcrumb-item active">Step ${stepDetail.id}. ${stepDetail.relativeUrl}</li>
        </c:if>

    </ol>

    <c:if test="${empty stepDetail}">
        <div style="clear: both;"></div>
        <form method="post" style="float: left;" action="${pageContext.request.contextPath}/project/${project.id}/execute-scenarios" onsubmit="return confirm('Execute scenario?')">
            <input type="hidden" name="scenarios[]" value="${scenario.id}"/>
            <input class="btn btn-default" type="submit" value="Execute this scenario">
        </form>

        <form method="post" style="float: left;" action="${pageContext.request.contextPath}/step/add-step" onsubmit="return confirm('Add step?')">
            <input type="hidden" name="scenarioId" value="${scenario.id}"/>
            <input class="btn btn-default" type="submit" value="Add step">
        </form>

        <form method="post" class="delete-form" style="float: right;" action="${pageContext.request.contextPath}/scenario/${scenario.id}/clone" onsubmit="return confirm('Copy scenario?')">
            <input class="btn btn-default" type="submit" value="Copy scenario">
        </form>
        <div style="clear: both;"></div>
    </c:if>

    <%--@elvariable id="steps" type="java.util.List<ru.bsc.test.at.executor.model.Step>"--%>
    <c:if test="${empty stepDetail}">
        <h4>Scenario ${scenario.id}. ${scenario.name}</h4>
        <a href="${pageContext.request.contextPath}/scenario/${scenario.id}/settings">Scenario settings</a><br/><br/>
    </c:if>
    <c:if test="${not empty stepDetail}">
        <h4>Step ${stepDetail.id}. <span style="font-size: smaller;">${project.serviceUrl}</span>${stepDetail.relativeUrl}</h4>
    </c:if>
    <form method="post" id="steps-form" action="${pageContext.request.contextPath}/scenario/${scenario.id}/save" onsubmit="return false;">
        <table class="table table-condensed steps-table">
            <tr>
                <th style="width: 1%;">Sort</th>
                <th>
                    URL<br/>Request body<br/>
                    <small style="font-weight: initial; font-size: smaller; color: gray;">${project.serviceUrl}</small>
                </th>
                <th>Request headers</th>
                <th>Expected response</th>
                <th>
                    Expected status code<br/>
                    Json XPath<br/>
                    Saving values<br/>
                    Responses
                </th>
            </tr>
            <c:forEach items="${steps}" var="step" varStatus="status">
                <tr>
                    <td>
                        <input type="text" style="width: inherit;" class="form-control" name="step[${status.index}][sort]" size="3" value="${step.sort}">
                        <c:if test="${empty stepDetail}">
                            <span><a href="${pageContext.request.contextPath}/step/${step.id}" target="_blank">Edit</a></span>
                        </c:if>
                        <input type="hidden" name="step[${status.index}][id]" value="${step.id}"/>

                        <input type="hidden" name="step[${status.index}][scenario][id]" value="${step.scenario.id}"/>
                        <input type="hidden" name="step[${status.index}][dbParams]" value="${step.dbParams}"/>
                        <input type="hidden" name="step[${status.index}][tmpServiceRequestsDirectory]" value="${step.tmpServiceRequestsDirectory}"/>
                    </td>
                    <td width="30%">
                        <select class="form-control" style="width: 15%; display: inline; float: left; padding-right: 0; padding-left: 0;" name="step[${status.index}][requestMethod]">
                            <option value=""></option>
                            <option value="GET"    ${step.requestMethod eq 'GET' ? 'selected' : ''}   >GET</option>
                            <option value="POST"   ${step.requestMethod eq 'POST' ? 'selected' : ''}  >POST</option>
                            <option value="PUT"    ${step.requestMethod eq 'PUT' ? 'selected' : ''}   >PUT</option>
                            <option value="DELETE" ${step.requestMethod eq 'DELETE' ? 'selected' : ''}>DELETE</option>
                            <option value="PATCH"  ${step.requestMethod eq 'PATCH' ? 'selected' : ''} >PATCH</option>
                        </select>
                        <input type="text" class="form-control" style="float: right; width: 85%; display: inline;" name="step[${status.index}][relativeUrl]" value="${step.relativeUrl}" aria-describedby="basic-addon3">

                        <!-- ${step.requestBodyType} -->
                        <select class="form-control" name="step[${status.index}][requestBodyType]" style="width: initial; display: inline; font-size: small; height: inherit;">
                            <option value="0" ${step.requestBodyType eq 'FORM' ? '':'selected'}>JSON request body</option>
                            <option value="1" ${step.requestBodyType eq 'FORM' ? 'selected':''}>FORM-data request body</option>
                        </select>
                        <a href="#" onclick="alert('JSON request body example:\n{id: 1, attributeA: \'valueA\', attributeB: \'valueB\'}' + '\n\nFORM-data request body example:\n id=1\n attributeA=valueA\n attributeB=valueB'); return false;">(?)</a>

                        <textarea style="height: 143px;" class="form-control" rows="9" name="step[${status.index}][request]">${step.request}</textarea>
                    </td>
                    <td width="10%"><textarea class="form-control" rows="12" name="step[${status.index}][requestHeaders]">${step.requestHeaders}</textarea></td>
                    <td width="30%">
                        <!-- ${step.requestBodyType} -->
                        <select class="form-control" name="step[${status.index}][expectedResponseIgnore]" style="width: initial; display: inline; font-size: small; height: inherit;">
                            <option value="false" ${step.expectedResponseIgnore ? '':'selected'}>-</option>
                            <option value="true" ${step.expectedResponseIgnore ? 'selected':''}>IGNORE response</option>
                        </select>
                        <textarea class="form-control" rows="10" style="height: 177px;" name="step[${status.index}][expectedResponse]"><c:out value="${step.expectedResponse}"/></textarea>
                    </td>
                    <td>
                        <label for="expectedStatusCode${status.index}">Expected status code <a href="#" onclick="alert('Example:\n404\n200\n500\nempty value - ignore status code'); return false;">(?)</a></label>
                        <input type="text" class="form-control" id="expectedStatusCode${status.index}" name="step[${status.index}][expectedStatusCode]" size="3" value="${step.expectedStatusCode}">

                        <label for="jsonXPath${status.index}">Saving values (Json XPath) <a href="#" onclick="alert('Example:\nparameterNameA = $.accountPortfolio[0].accountInfo.accountNumber\nparameterNameB = $.account.accountInfo[2].accountId\n\n $ - all response content'); return false;">(?)</a></label>
                        <textarea class="form-control" id="jsonXPath${status.index}" rows="2" name="step[${status.index}][jsonXPath]">${step.jsonXPath}</textarea>

                        <label for="savingValues${status.index}">Saving values <a href="#" onclick="alert('Example:\nparameterNameA, parameterNameB'); return false;">(?)</a></label>
                        <textarea class="form-control" id="savingValues${status.index}" rows="2" name="step[${status.index}][savingValues]">${step.savingValues}</textarea>

                        <label for="responses${status.index}">Responses (SoapUI mock) <a href="#" onclick="alert('Example:\nServiceName: ResponseNameA\nServiceName: ResponseNameB\n\nServiceName - service name from SoapUI'); return false;">(?)</a></label>
                        <textarea class="form-control" id="responses${status.index}" rows="2" name="step[${status.index}][responses]">${step.responses}</textarea>
                    </td>
                </tr>
                <tr>
                    <td style="border-top: 0;"></td>
                    <td style="border-top: 0;" colspan="5">
                        <div class="form-group" style="width: 20%; float: left;">
                            <label for="exampleInputName2">Variables <a href="#" onclick="alert('Example\nVariables: varA,varB,varC\nSql: select fieldA, fieldB, fieldC from table where id = :savedValueId or name = :parameterName '); return false;">(?)</a></label>
                            <input name="step[${status.index}][sqlSavedParameter]" type="text" class="form-control" id="exampleInputName2" placeholder="Variable name (saving values)" value="${step.sqlSavedParameter}">
                        </div>
                        <div class="form-group" style="width: 80%; float: right;">
                            <label for="exampleInputEmail2">Sql</label>
                            <input name="step[${status.index}][sql]" type="text" class="form-control" id="exampleInputEmail2" placeholder="Sql query" value="${step.sql}">
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <button class="btn btn-default" id="save-steps">Save</button> <span id="saving-state"></span>
    </form>

    <c:if test="${empty stepDetail}">
        <form method="post" class="delete-form" action="${pageContext.request.contextPath}/scenario/${scenario.id}/delete-scenario" onsubmit="return confirm('Delete scenario?')">
            <input class="btn btn-default" type="submit" value="Delete scenario" ${empty steps ? '' : 'disabled'}>
            <c:if test="${not empty steps}">
                <span style="font-size: smaller;">Allowed to delete only empty scenarios</span>
            </c:if>
        </form>
    </c:if>

    <c:if test="${not empty stepDetail}">
        <form method="post" class="delete-form" action="${pageContext.request.contextPath}/step/${stepDetail.id}/delete-step" onsubmit="return confirm('Delete step?')">
            <input class="btn btn-default" type="submit" value="Delete step">
        </form>

        <div style="clear: both;"></div>
        <h4>Expected service requests</h4>

        <form method="post" id="expected-service-requests-form" action="${pageContext.request.contextPath}/step/${stepDetail.id}/save-expected-service-requests" onsubmit="return false;">
            <table class="table table-condensed">
                <tr>
                    <th style="width: 1%;">Sort</th>
                    <th>Service name</th>
                    <th>Expected request</th>
                    <th>Ignored tags</th>
                    <th style="width: 1%;"></th>
                </tr>
                <%--@elvariable id="expectedRequestsList" type="java.util.List<ru.bsc.test.at.executor.model.ExpectedServiceRequest>"--%>
                <c:forEach items="${expectedRequestsList}" var="expectedRequest" varStatus="status">
                    <tr>
                        <td>
                            <input type="hidden" name="expectedRequest[${status.index}][id]" value="${expectedRequest.id}"/>
                            <input type="hidden" name="expectedRequest[${status.index}][step][id]" value="${expectedRequest.step.id}"/>
                            <input class="form-control" style="width: inherit;" size="3" name="expectedRequest[${status.index}][sort]" value="${expectedRequest.sort}"/>
                        </td>
                        <td><input class="form-control" name="expectedRequest[${status.index}][serviceName]" value="${expectedRequest.serviceName}"/></td>
                        <td>
                            <textarea rows="6" class="form-control" name="expectedRequest[${status.index}][expectedServiceRequest]">${expectedRequest.expectedServiceRequest}</textarea>
                        </td>
                        <td>
                            <input class="form-control" name="expectedRequest[${status.index}][ignoredTags]" value="${expectedRequest.ignoredTags}"/>
                        </td>
                        <td>
                            <a class="btn btn-default" role="button" data-delete-expected-request="${expectedRequest.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button class="btn btn-default" id="save-expected-service-requests">Save</button> <span id="save-expected-service-requests-state"></span>
        </form>

        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/step/add-expected-request" onsubmit="return confirm('Add expected request?')">
            <input type="hidden" name="stepId" value="${stepDetail.id}"/>
            <input class="form-control" type="text" name="serviceName" placeholder="New service name"/>
            <input class="btn btn-default" type="submit" value="Add expected request">
        </form>
        <!-- TODO вывод списка ожидаемых вызовов сервисов + редактирование -->
    </c:if>
</t:wrapper>
