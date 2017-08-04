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
        <a href="${pageContext.request.contextPath}/scenario/${scenario.id}/settings">Settings</a><br/><br/>
    </c:if>
    <c:if test="${not empty stepDetail}">
        <h4>Step ${stepDetail.id}. <span style="font-size: smaller;">${project.stand.serviceUrl}</span>${stepDetail.relativeUrl}</h4>
    </c:if>
    <form method="post" id="steps-form" action="${pageContext.request.contextPath}/scenario/${scenario.id}/save" onsubmit="return false;">
        <table class="table table-condensed steps-table">
            <tr>
                <th style="width: 1%;">Sort</th>
                <th>
                    URL<br/>Request body<br/>
                    <small style="font-weight: initial; font-size: smaller; color: gray;">${project.stand.serviceUrl}</small>
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
                    <td style="border-top: 0;" colspan="3">
                        <div class="form-group" style="width: 20%; float: left;">
                            <label for="exampleInputName2">Variables <a href="#" onclick="alert('Example\nVariables: varA,varB,varC\nSql: select fieldA, fieldB, fieldC from table where id = :savedValueId or name = :parameterName '); return false;">(?)</a></label>
                            <input name="step[${status.index}][sqlSavedParameter]" type="text" class="form-control" id="exampleInputName2" placeholder="Variable name (saving values)" value="${step.sqlSavedParameter}">
                        </div>
                        <div class="form-group" style="width: 80%; float: right;">
                            <label for="exampleInputEmail2">Sql</label>
                            <input name="step[${status.index}][sql]" type="text" class="form-control" id="exampleInputEmail2" placeholder="Sql query" value="${step.sql}">
                        </div>
                    </td>
                    <td style="border-top: 0;">
                        <div class="form-group" style="width: 10%; float: left;">
                            <label for="exampleInputName3">Polling</label>
                            <input style="margin: 5px;" name="step[${status.index}][usePolling]" type="checkbox" class="form-control" id="exampleInputName3" ${step.usePolling ? 'checked' : ''}>
                        </div>
                        <div class="form-group" style="width: 90%; float: left;">
                            <label for="exampleInputName4">Polling, checked element (JsonXPath) <a href="#" onclick="alert('Example:\n$.accountPortfolio[0].accountInfo.accountNumber\n\ncheck item not empty.'); return false;">(?)</a></label>
                            <input name="step[${status.index}][pollingJsonXPath]" type="text" class="form-control" id="exampleInputName4" placeholder="Example: $.items" value="${step.pollingJsonXPath}">
                        </div>
                    </td>
                </tr>

                <tr>
                    <td style="border-top: 0;"></td>
                    <td style="border-top: 0;" colspan="4">
                        <div class="form-horizontal">
                            <div class="col-sm-11">
                                <label>Comment:</label>
                                <input name="step[${status.index}][stepComment]" type="text" class="form-control" value="${step.stepComment}" placeholder="Comment" />
                                <label class="help-block">Check saved values:</label>
                                <div class="help-block">${step.savedValuesCheck}</div>
                            </div>
                            <div class="col-sm-1">
                                <label for="input_disabled">Disable step:</label>
                                <input style="margin: 5px;" name="step[${status.index}][disabled]" type="checkbox" class="form-control" id="input_disabled" ${step.disabled ? 'checked' : ''}>
                            </div>
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
        <h4>Check saved values</h4>
        <form method="post" id="check-saved-values-form" action="${pageContext.request.contextPath}/step/${stepDetail.id}/save-check-saved-values" onsubmit="return false;">
            <input type="hidden" name="savedValue[stepId]" value="${stepDetail.id}"/>
            <c:if test="${not empty stepDetail.savedValuesCheck}">
                <div class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-2"><label>Variable name</label></div>
                        <div class="col-sm-2"><label>Checked value</label></div>
                        <div class="col-sm-2"></div>
                    </div>

                    <c:forEach items="${stepDetail.savedValuesCheck}" var="savedValue" varStatus="status">
                        <div class="form-group">
                            <div class="col-sm-2">
                                <input class="form-control" name="savedValue[map][${status.index}][key]" value="${savedValue.key}"/>
                            </div>
                            <div class="col-sm-2">
                                <input class="form-control" name="savedValue[map][${status.index}][value]" value="${savedValue.value}" placeholder="Value"/>
                            </div>
                            <div class="col-sm-1">
                                <a class="btn btn-default" role="button" data-delete-check-saved-values="${savedValue.key}">Delete</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button class="btn btn-default" id="save-check-saved-values">Save</button> <span id="save-check-saved-values-state"></span>
            </c:if>
        </form>
        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/step/add-check-saved-value">
            <div class="form-group">
                <input type="hidden" name="stepId" value="${stepDetail.id}"/>
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="key" placeholder="Variable name"/>
                </div>
                <div class="col-sm-2">
                    <input class="form-control" type="text" name="value" placeholder="Value"/>
                </div>
                <div class="col-sm-2">
                    <input class="btn btn-default" type="submit" value="Add">
                </div>
            </div>
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


        <div style="clear: both;"></div>
        <h4>Mock service response</h4>
        <form method="post" id="mock-service-response-form" action="${pageContext.request.contextPath}/step/${stepDetail.id}/save-mock-service-responses" onsubmit="return false;">
            <table class="table table-condensed">
                <tr>
                    <th style="width: 1%;">Sort</th>
                    <th>Service url<br/>Response http-status<br/>Response body</th>
                    <th style="width: 1%;"></th>
                </tr>
                <%--@elvariable id="mockServiceResponseList" type="java.util.List<ru.bsc.test.at.executor.model.MockServiceResponse>"--%>
                <c:forEach items="${mockServiceResponseList}" var="mockServiceResponse" varStatus="status">
                    <tr>
                        <td style="width: 70px;">
                            <input type="hidden" name="mockServiceResponse[${status.index}][id]" value="${mockServiceResponse.id}"/>
                            <input type="hidden" name="mockServiceResponse[${status.index}][step][id]" value="${mockServiceResponse.step.id}"/>
                            <input class="form-control" style="width: inherit;" size="3" name="mockServiceResponse[${status.index}][sort]" value="${mockServiceResponse.sort}"/>
                        </td>
                        <td>
                            <input class="form-control" name="mockServiceResponse[${status.index}][serviceUrl]" value="${mockServiceResponse.serviceUrl}" placeholder="Service URL. Example: /mockDkboDebtServiceSoap11"/>
                            <input class="form-control" name="mockServiceResponse[${status.index}][httpStatus]" value="${mockServiceResponse.httpStatus}" placeholder="HTTP-status response. Example: 200"/>
                            <textarea rows="10" class="form-control" name="mockServiceResponse[${status.index}][responseBody]">${mockServiceResponse.responseBody}</textarea>
                        </td>
                        <td style="width: 1%;">
                            <a class="btn btn-default" role="button" data-delete-mock-service-response="${mockServiceResponse.id}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button class="btn btn-default" id="save-mock-service-response">Save</button> <span id="save-mock-service-response-state"></span>
        </form>

        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/step/add-mock-service" onsubmit="return confirm('Confirm this action: Add mock service')">
            <input type="hidden" name="stepId" value="${stepDetail.id}"/>
            <input class="form-control" type="text" name="serviceUrl" placeholder="New mock URL"/>
            <input class="btn btn-default" type="submit" value="Add mock service">
        </form>
    </c:if>
</t:wrapper>
