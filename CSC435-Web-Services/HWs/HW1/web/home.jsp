<%--
  Created by IntelliJ IDEA.
  User: Jeff
  Date: 2/14/2015
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Home</title>

        <meta name="description" content="Interact, socialize, compete and do battle with other players online">
        <meta name="keyword" content="battleship,cruiser,submarine,destroyer,carrier,log,social,battle,fight,sink,sink you,compete">
        <meta name="robots" content="index,follow">

        <link rel="shortcut icon" type="imgage/png" href="${pageContext.request.contextPath}/images/favicon.png">
        <!--bootstrap css-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <!--custom css--->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    </head>
    <body>
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="navbar-brand" style="font-family:Algerian; font-size: 1.2em;float: left;display: table"> <div class="navbar-brand" style="display: table-cell; vertical-align: middle">Commander Log</div></div>
            <div style="float: right;font-size: 25px; margin-right: 20px"><%=session.getAttribute("userName") %><span class="glyphicon glyphicon-user"></span></div>
        </div>
        <div class="container" style="width: 95%;margin: 120px auto;">
            <div class="row" style="width: 100%; height: 800px;border: 3px solid green">
                <div class="col-lg-4" style="height: 100%;border: 2px solid red">
                    <img src="${pageContext.request.contextPath}/images/user.jpg" style="width: 100%;height: 412px"><br>
                    <h1><%=session.getAttribute("fullName") %></h1>
                    <h4>Battles:<%=request.getAttribute("battles") %></h4>
                    <h4>Ships Knocked Out:<%=request.getAttribute("knockOuts") %></h4>
                    <h4>Ships Lost:<%=request.getAttribute("shipsLost") %></h4>
                    <h4>Won:<%=request.getAttribute("won") %></h4>
                    <h4>Rating:<%=request.getAttribute("rating") %></h4>
                    <h2>Rank:<%=request.getAttribute("rank") %></h2>
                    <form method="get" action="History">
                        <div class="form-group hidden">
                            <label for="use">send user</label>
                            <input id="use" name="user" value=<%=session.getAttribute("userName") %>>
                        </div>
                        <button type="submit" class="btn btn-success">Get History</button>
                    </form>

                    <%--<c:if test="${battlesWon}">
                        Hello there +battlesWon
                    </c:if>--%>
                </div>
                <div class="col-lg-8">
                    <form method="post" action="Home">
                        <div class="form-group hidden">
                            <label for="bat"></label>
                            <input id="bat" type="text" name="battle" value="battle">
                        </div>
                        <button  type="submit" class="btn btn-danger" style="width: 70%; height: 70%; margin: auto;">Do Battle</button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
