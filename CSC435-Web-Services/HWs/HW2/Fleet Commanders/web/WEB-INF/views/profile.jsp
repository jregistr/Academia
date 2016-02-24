<%@ page import="Controllers.Controller" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.google.gson.JsonParser" %>
<%@ page import="com.google.gson.JsonArray" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fleet Heroes:Profile</title>
    <meta name="description" content="Interact, socialize, compete and do battle with other players online">
    <meta name="keyword" content="battleship,cruiser,submarine,destroyer,carrier,log,social,battle,fight,sink,sink you,compete">
    <meta name="robots" content="index,follow">

    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">


</head>
<%int id = (Integer)request.getAttribute(Controller.USER_ID_PARAMETER);%>

<%
    String custom = (String)request.getAttribute(Controller.getCustomizationsTableName());
    String infos = (String) request.getAttribute("info");
    String fullHist = (String) request.getAttribute("fullHistory");
    String battles = (String) request.getAttribute(Controller.getBattlesTableName());
    String commentsAtr = (String)request.getAttribute(Controller.getCommentsTableName());
    String friendsAtr = (String)request.getAttribute(Controller.getFriendsTableName());
%>

<%--<%String myBack = Customization.retrieveBackgroundPic(id);%>
<%String myProf = Customization.retrieveProfilePic(id);%>--%>
<%
    JsonParser parser = new JsonParser();
    JsonObject custOB = parser.parse(custom).getAsJsonObject();
    String myBack = custOB.get(Controller.getBackgroundImgColumn()).getAsString();
    String myProf = custOB.get(Controller.getProfileImgColumn()).getAsString();

/********************************************************************************/
    JsonObject infObj = parser.parse(infos).getAsJsonObject();
    String displayName = infObj.get(Controller.getDisplayNameColumn()).getAsString();
    String uName = infObj.get(Controller.getUserNameColumn()).getAsString();
%>
<body class="container-fluid" id="profileBody" style="min-width: 1000px; background-image:url(<%=myBack%>)">

<%--url(${pageContext.request.contextPath}/images/backs/<%=myBack%>)--%>
<%--    <div id="custModal" class="modal fade">
        <div class="modal-dialog" role="dialog" aria-hidden="true" data-backdrop="static" style="width: 1000px;padding: 5px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" aria-label="Close" onclick="closeCustomModel()"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title text-primary">Pick your Profile amd Background image</h4>
                </div>
                <div class="modal-body" style="height: 600px;">
                    <div class="row" style="height: 100%">
                        <div class="col-xs-3">
                            <label for="custSearchText" class="invisible"></label>
                            <input type="text" id="custSearchText" style="width: 100%" placeholder="search input">
                            <button class="btn btn-info" style="width: 100%; margin-top: 10px;" onclick="switchCustomState()">Background IMG <span class="glyphicon glyphicon-refresh"></span></button>
                            <button class="btn btn-info" style="width: 100%; margin-top: 10px;" onclick="switchCustomState()">Profile IMG <span class="glyphicon glyphicon-refresh"></span></button>
                        </div>
                        <div class="col-xs-9 imageSelectorNotReady" id="imageSelector" style="height:100%;">
                           &lt;%&ndash; <div style="border: 2px inset #000000; width: 150px; height: 250px; margin-right: 5px; margin-top: 5px;">

                            </div>&ndash;%&gt;
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    &lt;%&ndash;<button class="btn btn-primary" onclick="switchPage(1)">[1]</button> <button class="btn btn-primary" onclick="switchPage(2)">[2]</button> <button class="btn btn-primary" onclick="switchPage(3)">[3]</button> <button class="btn btn-primary" onclick="switchPage(4)">[4]</button>&ndash;%&gt;
                    <button type="button" class="btn btn-default" onclick="closeCustomModel()">Close</button>
                    <button type="button" class="btn btn-primary" onclick="saveCustom(<%=id%>)">Save changes</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->--%>

<header>
    <ul class="nav-left">
        <li style="font-family: Algerian">
            <a href="Profile" style="text-decoration: none">Commander Log</a>
        </li>
    </ul>
    <span class="logo"><button id="battleButton" class="btn btn-lg btn-danger" style="height: 100%; width: 100px; text-align: center;">Battle</button></span>
    <ul class="nav-right">
        <li>
            Logged in as:
        </li>
        <li>
            <a href="Profile"><%=uName%></a>
        </li>
        <li>
            <div style="width: 50px; height: 95%; margin: auto">
                <img class="imgFit" src="<%=myProf%>">
                <%--<img class="imgFit" src="${pageContext.request.contextPath}/images/pics/<%=myProf%>">--%>
            </div>
        </li>
        <li>
            <form class="form-group-sm" method="get" action="Logout">
                <Button type="submit" class="btn btn-danger">Logout</Button>
            </form>
        </li>
    </ul>
</header>

<div class="row" id="underNav">

</div>

<div id="mainDisplay">

    <div class="row" style="width: 100%; height: 80px; margin: auto; padding: 5px; background-color: #f5f5f5">
        <div style="width: 80px; height: 90%;float: left; margin: auto">
            <img class="imgFit" src="<%=myProf%>">
        </div>
        <h2 class="text-capitalize"><b><%=uName%></b> <button class="btn btn-primary" onclick="openCustomModal()"><span class="glyphicon glyphicon-pencil"></span></button> </h2>
        <%--<button class="btn btn-primary" id="showCustButton" >Click</button>--%>
    </div>
    <div class="row mainDisplayRow" role="tabpanel">
        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs" role="tablist">
            <li role="presentation" class="active"><a href="#Overview" role="tab" data-toggle="tab">Overview<span class="glyphicon glyphicon-eye-open"></span></a></li>
            <li role="presentation"> <a href="#Battles" role="tab" data-toggle="tab">Battles<span class="glyphicon glyphicon-tower"></span></a> </li>
            <li role="presentation"> <a href="#Chat" role="tab" data-toggle="tab">My Bullhorn<span class="glyphicon glyphicon-user"></span></a> </li>
            <li role="presentation"> <a href="#Friends" role="tab" data-toggle="tab">Friends<span class="glyphicon glyphicon-user"></span></a> </li>
            <li role="presentation"> <a href="#GameInfo" role="tab" data-toggle="tab">Game Info<span class="glyphicon glyphicon-book"></span></a> </li>
        </ul>
        <div id="my-tab-content" class="tab-content">
            <%--<%Battle[] battles = (Battle[]) request.getAttribute(Controller.BATTLES_PARAMETER);%>--%>
            <%--<%Battle[] battles = Battle.retrieve(id);%>--%>
            <%--<%int id = (Integer)request.getAttribute(Controller.USER_ID_PARAMETER);%>--%>
            <div role="tabpanel" class="tab-pane row myTabBody active" id="Overview">
                <div class="col-xs-4 myTabBodyCol">
                    <h2 style="margin:auto;text-align: center;"><%=displayName%></h2>
                    <div style="width: 95%;height: 600px; margin: auto;">
                        <img class="imgFitScale" style="margin: auto;" src="<%=myProf%>">
                    </div>
                </div>
                <div class="col-xs-4 myTabBodyCol">

                    <%if(fullHist != null && !fullHist.isEmpty()){%>
                        <%JsonObject histObj = parser.parse(fullHist).getAsJsonObject();%>
                        <h3>Battles Fought:<%=histObj.get(Controller.getFoughtProperty()).getAsInt()%></h3>
                        <h3>Battles Won:<%=histObj.get(Controller.getWinsProperty()).getAsInt()%></h3>
                        <h3>Ships Knocked:<%=histObj.get(Controller.getShipsKnockedProperty()).getAsInt()%></h3>
                        <h3>Ships Lost:<%=histObj.get(Controller.getShipsLostProperty()).getAsInt()%></h3>
                        <h3>Shots Fired:<%=histObj.get(Controller.getFiredProperty()).getAsInt()%></h3>
                        <%--<h3>Total Turns Taken:<%=histObj.get("date_started").getAsString()%></h3>--%>
                    <%}%>
                    <%if(fullHist == null || fullHist.isEmpty()){%>
                       <h3>Battles Fought:0</h3>
                       <h3>Battles Won:0</h3>
                       <h3>Ships Knocked:0</h3>
                       <h3>Ships Lost:0</h3>
                       <h3>Shots Fired:0</h3>
                    <%}%>
                </div>
                <div class="col-xs-4 myTabBodyCol">
                    <%if(fullHist != null && !fullHist.isEmpty()){%>
                         <%JsonObject histObj = parser.parse(fullHist).getAsJsonObject();%>
                         <%float rating = histObj.get(Controller.getLadderPointsProperty()).getAsFloat();
                             float rateNext = histObj.get(Controller.getLadderNextProperty()).getAsFloat();%>
                         <h2>Rank:<%=histObj.get(Controller.getRankProperty()).getAsString()%></h2>
                         <h2>Ladder Points:</h2><h3><%=rating%> / <%=rateNext%>=<small><%=String.format("%.2f", (rating / rateNext) * 100)%></small></h3>
                         <h2>Win Ratio:<%=String.format("%.2f",histObj.get(Controller.getWinRatioProperty()).getAsFloat())%></h2>
                         <h2>Average Turns Per Battle:<%=histObj.get(Controller.getAverageTurnsPerProperty()).getAsFloat()%></h2>
                         <h2>Accuracy:<%=String.format("%.2f",histObj.get(Controller.getAccuracyProperty()).getAsFloat())%></h2>
                         <h2>Ships Knocked Per Lost:<%=histObj.get(Controller.getKnockedPerLostProperty()).getAsFloat()%></h2>
                    <%}%>
                </div>

            </div>
            <div role="tabpanel" class="tab-pane myTabBody" id="Battles">
                <%if(battles != null && !battles.isEmpty()){%>
                    <%JsonObject object = parser.parse(battles).getAsJsonObject();
                        JsonArray bArray = object.getAsJsonArray("battles");
                    %>
                    <%for (int i = 0; i < bArray.size(); i++) {%>
                        <%JsonObject battle = bArray.get(i).getAsJsonObject();%>
                        <div class="row" style="height: 425px; padding: 5px;margin: 25px;">
                            <h4>Started:<%=battle.get(Controller.getDateStartedColumn()).getAsString()%>, Ended: <%=battle.get(Controller.getDateEndedColumn()).getAsString()%></h4>
                            <%String blue = battle.get(Controller.getBlueUserColumn()).getAsString();
                                String red = battle.get(Controller.getRedUserColumn()).getAsString();
                            %>
                            <div class="col-xs-6" style="height: 100%;background-color: rgba(135,206,250,0.2);">
                                <%int fBB = battle.get(Controller.getFiredByBlueColumn()).getAsInt();
                                    int hBB = battle.get(Controller.getHitsByBlueColumn()).getAsInt();
                                %>

                                <h1><%=(blue.equals(uName)) ? "Player:" + blue : "Challenger:" + blue%></h1>
                                <h2>Ships Knocked Out: <%=battle.get(Controller.getKnockedByBlueColumn()).getAsInt()%></h2>
                                <h2>Ships Lost: <%=battle.get(Controller.getLostByBlueColumn()).getAsInt()%></h2>
                                <h2>Shots Fired: <%=fBB%></h2>
                                <h2>Shots Hit: <%=hBB%></h2>
                                <h2>Accuracy:<%=String.format("%.2f",(float)fBB/(float)hBB)%></h2>
                                <%if((battle.get(Controller.getWinnerColumn()).getAsString()).equals(Controller.getWinnerBlue())){%>
                                    <h1 style="font-size: 2.5em; color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                <%}%>
                            </div>

                            <div class="col-xs-6" style="height: 100%;background-color: rgba(255,153,153,0.5);">
                                <%int fBR = battle.get(Controller.getFiredByRedColumn()).getAsInt();
                                    int hBR = battle.get(Controller.getHitsByRedColumn()).getAsInt();
                                %>

                                <h1><%=(red.equals(uName)) ? "Player:" + red : "Challenger:" + red%></h1>
                                <h2>Ships Knocked Out: <%=battle.get(Controller.getKnockedByRedColumn()).getAsInt()%></h2>
                                <h2>Ships Lost: <%=battle.get(Controller.getLostByRedColumn()).getAsInt()%></h2>
                                <h2>Shots Fired: <%=fBR%></h2>
                                <h2>Shots Hit: <%=hBR%></h2>
                                <h2>Accuracy:<%=String.format("%.2f",(float)hBR/(float)fBR)%></h2>
                                <%if((battle.get(Controller.getWinnerColumn()).getAsString()).equals(Controller.getWinngerRed())){%>
                                    <h1 style="font-size: 2.5em; color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                <%}%>
                            </div>

                        </div>
                    <%}%>
                <%}%>

            </div>
            <div role="tabpanel" class="tab-pane myTabBody" id="Chat">
                <div class="col-xs-4">
                    <div id="messages">
                        <%if(commentsAtr != null && !commentsAtr.isEmpty()){%>
                            <%JsonObject commentsObj = parser.parse(commentsAtr).getAsJsonObject();
                                JsonArray comments = commentsObj.getAsJsonArray(Controller.getCommentsTableName());
                            %>

                            <%for(JsonElement element : comments){%>
                                <%JsonObject comment = element.getAsJsonObject();%>
                                <%if(comment != null){%>
                                    <div class="comment">
                                        <h5><b><%=comment.get(Controller.getPosterUserColumn()).getAsString()%></b> <i><%=comment.get(Controller.getTimePostedColumn()).getAsString()%></i></h5>
                                        <p class="msg">
                                            <%=comment.get(Controller.getMsgColumn()).getAsString()%>
                                        </p>
                                    </div>
                                <%}%>
                            <%}%>
                        <%}%>
                    </div>
                    <div>
                        <form class="form-group-lg">
                            <input id="userComment" class="form-control" style="height: 80px" type="text" placeholder="enter a message" name="comment">
                            <button value="<%=id%>,<%=id%>" id="postComment" class="form-control btn btn-primary" type="button">Post Comment</button>
                        </form>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane myTabBody" id="Friends">
                <div class="col-xs-5">
                    <%if(friendsAtr != null && !friendsAtr.isEmpty()){%>
                        <%JsonObject friendsObj = parser.parse(friendsAtr).getAsJsonObject();
                        JsonArray friendsArray = friendsObj.getAsJsonArray(Controller.getFriendsTableName());
                        %>
                        <%for(JsonElement element : friendsArray){%>
                            <%if(element != null){%>
                                <%JsonObject friend = element.getAsJsonObject();%>
                                <%if(friend != null){%>
                                    <div class="comment" style="margin-top: 15px">
                                        <form method="get" action="Profile">
                                            <h2>Name: <%=friend.get(Controller.getUserNameColumn())%></h2>
                                            <button name="<%=Controller.VIEWING_ID_PARAMETER%>" value="<%=friend.get(Controller.getUserIdColumn()).getAsInt()%>" type="submit" class="form-control btn btn-primary" style="display: table-cell">Go to <%=friend.get(Controller.getUserNameColumn())%>'s Profile</button>
                                        </form>
                                    </div>
                                <%}%>
                            <%}%>
                        <%}%>
                    <%}%>
                </div>
                <div class="col-xs-3">

                </div>
                <div class="col-xs-4">
                    <form class="form-group-lg">
                        <label for="findFriendInput">Search for another user</label>
                        <input id="findFriendInput" class="input-lg" type="text" placeholder="search for a user">
                        <button id="findFriend" type="button" class="btn btn-primary">Search</button>
                    </form>
                    <div id="usersResult">

                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane myTabBody" id="GameInfo">
                <div class="col-lg-12">
                    <%if(Controller.SHIPS_INFO_ARRAY.length == Controller.SHIPS_INFO_IMAGE_ARRAY.length){%>
                    <%for(int i = 0; i < Controller.SHIPS_INFO_ARRAY.length; i ++){%>
                    <%String info = Controller.SHIPS_INFO_ARRAY[i];%>
                    <%String infoIMG = Controller.SHIPS_INFO_IMAGE_ARRAY[i];%>
                    <div class="comment" style="font-family: Tahoma; font-size: 1.4em">
                        <div style="width: 100px; height: 100px;">
                            <img class="imgFit" src="${pageContext.request.contextPath}/images/<%=infoIMG%>">
                        </div>
                        <h4>
                            <%=info%>
                        </h4>
                    </div>
                    <%}%>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/bootstrap.js"></script>

<script>
    function addButton(name){
        $.ajax({
            url : "Services/Addfriend",
            data : {
                user:"<%=uName%>",
                target:name
            },
            type:"POST",
            success:function(response){
                location.reload();
            }
        });
    }
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/myJs.js"></script>

</body>
</html>
