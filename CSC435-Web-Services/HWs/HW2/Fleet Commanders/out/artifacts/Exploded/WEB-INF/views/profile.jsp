<%@ page import="Controllers.Controller" %>
<%@ page import="Models.*" %>
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
  <%String myBack = Customization.retrieveBackgroundPic(id);%>
  <%String myProf = Customization.retrieveProfilePic(id);%>
  <body class="container-fluid" id="profileBody" style="min-width: 1000px; background-image: url(${pageContext.request.contextPath}/images/backs/<%=myBack%>)">
        <header>
            <ul class="nav-left">
                <li style="font-family: Algerian">
                    Commander Log
                </li>
            </ul>
            <span class="logo"><button class="btn btn-lg btn-danger" style="height: 100%; width: 100px; text-align: center;">Battle</button></span>
            <ul class="nav-right">
                <li>
                    Logged in as:
                </li>
                <li>
                    <%=request.getAttribute(Controller.USERNAME_PARAMETER)%>
                </li>
                <li>
                    <div style="width: 50px; height: 95%; margin: auto">
                        <img class="imgFit" src="${pageContext.request.contextPath}/images/pics/<%=myProf%>">
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
                    <img class="imgFit" src="${pageContext.request.contextPath}/images/pics/<%=myProf%>">
                </div>
                <h2 class="text-capitalize"><b><%=request.getAttribute(Controller.USERNAME_PARAMETER)%></b></h2>
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
                        <%Battle[] battles = Battle.retrieve(id);%>
                    <%--<%int id = (Integer)request.getAttribute(Controller.USER_ID_PARAMETER);%>--%>
                    <div role="tabpanel" class="tab-pane row myTabBody active" id="Overview">
                        <div class="col-xs-4 myTabBodyCol">
                            <h2 style="margin:auto;text-align: center;"><%=request.getAttribute(Controller.DISPLAYNAME_PARAMETER)%></h2>
                            <div style="width: 95%;height: 600px; margin: auto;">
                                <img class="imgFitScale" style="margin: auto;" src="${pageContext.request.contextPath}/images/pics/<%=myProf%>">
                            </div>
                        </div>
                        <div class="col-xs-4 myTabBodyCol">
                            <h3>Battles Fought: <%=Battle.totalBattles(battles)%></h3>
                            <h3>Battles Won: <%=Battle.totalWon(battles,id)%> </h3>
                            <h3>Ships Knocked Out: <%=Battle.totalKnockouts(battles,id)%> </h3>
                            <h3>Ships Lost: <%=Battle.totaShipslLost(battles,id)%> </h3>
                            <h3>Shots Fired: <%=Battle.totalShotsFired(battles,id)%> </h3>
                            <h3>Total Turns Taken: <%=Battle.totalTurns(battles)%> </h3>
                        </div>
                        <div class="col-xs-4 myTabBodyCol">
                            <h2>Win Ratio: <%=String.format("%.2f",Battle.winRatio(battles,id))%></h2>
                            <h2>Average Turns Per Battle: <%=Battle.averageTurnsPerBattle(battles,id)%></h2>
                            <h2>Accuracy: <%=String.format("%.3f",Battle.totalAccuracy(battles,id) * 100) + "%"%> </h2>
                            <h2>Ships Knocked Per Lost: <%=String.format("%.1f",Battle.shipsKnockedPerLost(battles,id))%></h2>
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane myTabBody" id="Battles">
                        <%if(battles != null && battles.length > 0){%>
                            <% for (Battle battle : battles) {%>
                                <% if(battle != null){%>
                                    <div class="row" style="height: 400px; padding: 5px; margin: 20px;">
                                        <h4>Started:<%=battle.getDateStarted().toString()%>, Ended:<%=battle.getDateEnded().toString()%> </h4>
                                        <div class="col-xs-6" style="height: 100%;background-color: rgba(135,206,250,0.2);"> <!--Blue side -->
                                            <h2>Ships Knocked out: <%=battle.getKnockedByBlue()%></h2>
                                            <h2>Ships Lost: <%=battle.getLostByBlue()%></h2>
                                            <h2>Shots Fired: <%=battle.getFiredByBlue()%></h2>
                                            <h2>Shots Hit: <%=battle.getHitsByBlue()%></h2>
                                            <h2>Accuracy: <%=String.format("%.3f",((double)battle.getHitsByBlue()/(double)battle.getFiredByBlue()) * 100) + "%"%></h2>
                                            <%if(battle.getWinner().equals(Battle.WINNER_BLUE)){%>
                                                <h1 style="font-size: 1.5em;color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                            <%}%>
                                        </div>
                                        <div class="col-xs-6" style="height: 100%;background-color: rgba(255,0,0,0.15);">
                                            <h2>Ships Knocked out: <%=battle.getKnockedByRed()%></h2>
                                            <h2>Ships Lost: <%=battle.getLostByRed()%></h2>
                                            <h2>Shots Fired: <%=battle.getFiredByRed()%></h2>
                                            <h2>Shots Hit: <%=battle.getHitsByRed()%></h2>
                                            <h2>Accuracy: <%=String.format("%.3f",((double)battle.getHitsByRed()/(double)battle.getFiredByRed()) * 100) + "%"%></h2>
                                            <%if(battle.getWinner().equals(Battle.WINNGER_RED)){%>
                                            <h1 style="font-size: 1.5em;color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                            <%}%>
                                        </div>
                                    </div>
                                <% }%>
                            <%}%>
                        <%}%>
                    </div>
                    <div role="tabpanel" class="tab-pane myTabBody" id="Chat">
                       <div class="col-xs-4">
                           <%Comment[] comments = Comment.retrieve(id);%>
                               <%if(comments != null && comments.length > 0){%>
                                   <%for (Comment comment : comments){%>
                                       <%if(comment != null){%>
                                            <div class="comment">
                                                <h5><b><%=User.retrieveName(comment.getPosterUser())%></b> <i><%=comment.getTimePosted()%></i></h5>
                                                <p class="msg">
                                                    <%=comment.getMsg()%>
                                                </p>
                                            </div>
                                       <%}%>
                               <%}%>
                           <%}%>
                           <div>
                               <form class="form-group-lg" method="post" action="Profile">
                                   <input class="form-control" type="text" placeholder="enter a message" name="comment">
                                   <button class="form-control btn btn-primary" type="submit">Post</button>
                               </form>
                           </div>
                       </div>
                    </div>
                    <div role="tabpanel" class="tab-pane myTabBody" id="Friends">
                        <div class="col-xs-5">
                           <%Friend[] friends = Friend.retrieveAll(id);%>
                            <%if(friends != null && friends.length > 0){%>
                                <%for(Friend friend : friends){%>
                                    <%if(friend != null){%>
                                        <%String friendName = User.retrieveName(friend.getUserID());%>
                                        <%--<%String profPicName = Customization.retrieveProfilePic(id);%>--%>
                                        <div class="comment">
                                           <form method="get" action="Profile">
                                              <input class="hidden" type="text" name="<%=Controller.VIEWING_ID_PARAMETER%>" value="<%=friend.getUserID()%>">
                                              <div style="height: 60px; width: 60px;">
                                                  <img class="imgFit" src="${pageContext.request.contextPath}/images/pics/<%=myProf%>">
                                              </div>
                                               <h2>Name: <%=friendName%></h2>
                                              <button type="submit" class="form-control btn btn-primary" style="display: table-cell">Go to <%=friendName%>'s Profile</button>
                                           </form>
                                        </div>
                                    <%}%>
                                <%}%>
                            <%}%>
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

       <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/bootstrap.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/main.js"></script>
  </body>
</html>
