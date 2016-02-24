
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fleet Heroes:Error</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="container-fluid">
    <div class="row">
        <div class="col-lg-12" style="overflow: scroll">
            <div style="width: 60%; height: 60%;">
                 <img class="imgFit" src="${pageContext.request.contextPath}/images/sadFace.png">
            </div>
            <h1 style="font-size: 2.5em">Sorry but the page you requested does not exist or an error occured.</h1>
            <form method="get" action="Profile">
                <button type="submit" class="btn btn-lg btn-primary">Click this button to go back to profile</button>
            </form>
        </div>
    </div>
</body>
</html>
