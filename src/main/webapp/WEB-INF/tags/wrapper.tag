<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Auto Tester</title>
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css">
    <link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/css/styles.css">

    <script>
        window.contextPath = '${pageContext.request.contextPath}';
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.1.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/js/main.js" type="text/javascript"></script>

</head>
<body>
    <jsp:doBody/>
</body>
</html>