<%@ page import="com.google.gson.JsonParser; Models.Battle" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Profile</title>
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" >
        <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}">
        <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'bootstrap-theme.min.css')}" >
        <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'my.css')}" >

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body style="background-image: url(${customization.backgroundImage}); background-size: 100%">

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand logo" href="#">Fleet On Rails</a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" style="font-size: 1.3em"> ${user.getUsername().capitalize()} <img src="${customization.profileImage}" style="max-width: 30px; max-height: 30px">  <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="">${user.username}</a></li>
                            <li><a href="${createLink(controller: 'logout',action: 'index')}">Log out</a></li>
                        </ul>
                    </li>
                </ul>


            </div>
        </div>
    </nav>

    <div class="container" style="width: 90%; margin: auto">

        <g:if test="${!viewing}">
            <div class="row profile">
                <div class="col-md-3" style="background-color: white; height: 750px">
                    <div class="profile-sidebar" style="margin: auto">
                        <div class="profile-userpic" style="margin: auto">
                            <img src="${customization.profileImage}" class="img-responsive">
                        </div>
                        <div class="profile-usertitle">
                            <div class="profile-usertitle-name">
                                <h2><strong>${user.getUsername()}</strong><br><small>${user.getDisplayName()}</small></h2>
                            </div>
                        </div>
                        <div class="profile-userbuttons" style="margin: auto">
                            <button type="button" class="btn btn-danger btn-sm">Customize</button>
                        </div>
                        <div class="profile-usermenu">
                            <g:if test="${summary != null && !summary.isEmpty()}">
                                <%
                                    def parser = new JsonParser()
                                    def summaryObj = parser.parse(summary).getAsJsonObject()
                                %>
                                <ul class="nav">
                                    <li class="active" style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Rank:</strong><small><b>${summaryObj.get("rank")}</b></small><br><small class="text-info text-center" style="margin: 0">${summaryObj.get("ladderPoints")} / ${summaryObj.get("ladderNext")}</small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <% def winRatio = summaryObj.get("winRatio").getAsFloat()%>
                                        <h3 class="text-primary"><strong>Won:</strong><small><b>${summaryObj.get("wins")} / ${summaryObj.get("fought")}</b></small> <small class="text-success">${String.format("%.2f",winRatio)}</small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Destroyed:</strong><small><b>${summaryObj.get("sKnocked")}</b></small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Accuracy:</strong><small><b>${String.format("%.2f",summaryObj.get("accuracy").getAsFloat())}</b></small></h3>
                                    </li>
                                </ul>
                            </g:if>
                            <g:else>
                                <ul class="nav">
                                    <li class="active" style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Rank:</strong><small><b>No rank info</b></small><br><small class="text-info text-center" style="margin: 0">none / none</small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Won:</strong><small><b>none / none</b></small> <small class="text-success">${String.format("%.2f",0.0)}</small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Destroyed:</strong><small><b>Some number</b></small></h3>
                                    </li>
                                    <li style="border: 1px inset lightgray; margin-top: 2px">
                                        <h3 class="text-primary"><strong>Accuracy:</strong><small><b>No info</b></small></h3>
                                    </li>
                                </ul>
                            </g:else>
                        </div>
                    </div>
                </div>
                <div class="col-md-9" style="min-height: 750px;">
                    <div style="background-color: white;width: 98%;min-height: 750px; margin: auto;">
                        <ul id="tabs" class="nav nav-tabs" data-tabs="tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#Battles" role="tab" data-toggle="tab">Battles<span class="glyphicon glyphicon-eye-open"></span></a></li>
                            <li role="presentation"> <a href="#Chat" role="tab" data-toggle="tab">Chat<span class="glyphicon glyphicon-tower"></span></a> </li>
                            <li role="presentation"> <a href="#Friends" role="tab" data-toggle="tab">Friends<span class="glyphicon glyphicon-user"></span></a> </li>
                            <li role="presentation"> <a href="#GameInfo" role="tab" data-toggle="tab">Friends<span class="glyphicon glyphicon-user"></span></a> </li>
                        </ul>
                        <div id="my-tab-content" class="tab-content" style="height: 100%">
                            <div role="tabpanel" class="tab-pane row myTabBody active" id="Battles" style="padding-left: 20px">
                               <g:if test="${battles != null && battles.size() > 0}">
                                    <g:each var="battle" in="${battles}">
                                       <g:if test="${battle != null}">
                                           <div class="row">
                                           <g:if test="${battle.blue.getUsername().equals(user.getUsername())}">
                                               <div class="col-xs-6" style="background-color: rgba(0,0,255,0.1)">
                                                   <h2>Ships Knocked Out: ${battle.knockedByBlue}</h2>
                                                   <h2>Ships Lost: ${battle.lostByBlue}</h2>
                                                   <h2>Shots Fired:${battle.firedByBlue}</h2>
                                                   <h2>Shots Hit: ${battle.hitsByBlue}</h2>
                                                   <g:if test="${battle.blueWin}">
                                                       <h1 style="color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                                   </g:if>
                                               </div>

                                               <div class="col-xs-6" style="background-color: rgba(255,0,0,0.1)">
                                                   <h2><strong>${battle.red.getUsername()}</strong></h2>
                                                   <h2>Ships Knocked Out: ${battle.knockedByRed}</h2>
                                                   <h2>Ships Lost: ${battle.lostByRed}</h2>
                                                   <h2>Shots Fired:${battle.firedByRed}</h2>
                                                   <h2>Shots Hit: ${battle.hitsByRed}</h2>
                                                   <g:if test="${!battle.blueWin}">
                                                       <h1 style="color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                                   </g:if>
                                               </div>

                                           </g:if>
                                           <g:else>
                                               <div class="col-xs-6" style="background-color: rgba(255,0,0,0.1)">
                                                   <h2>Ships Knocked Out: ${battle.knockedByRed}</h2>
                                                   <h2>Ships Lost: ${battle.lostByRed}</h2>
                                                   <h2>Shots Fired:${battle.firedByRed}</h2>
                                                   <h2>Shots Hit: ${battle.hitsByRed}</h2>
                                                   <g:if test="${!battle.blueWin}">
                                                       <h1 style="color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                                   </g:if>
                                               </div>


                                               <div class="col-xs-6" style="background-color: rgba(0,0,255,0.1)">
                                                   <h2><strong>${battle.blue.getUsername()}</strong></h2>
                                                   <h2>Ships Knocked Out: ${battle.knockedByBlue}</h2>
                                                   <h2>Ships Lost: ${battle.lostByBlue}</h2>
                                                   <h2>Shots Fired:${battle.firedByBlue}</h2>
                                                   <h2>Shots Hit: ${battle.hitsByBlue}</h2>
                                                   <g:if test="${battle.blueWin}">
                                                       <h1 style="color: green"><span class="glyphicon glyphicon-thumbs-up"></span></h1>
                                                   </g:if>
                                               </div>

                                           </g:else>
                                       </div>
                                       </g:if>
                                    </g:each>
                               </g:if>
                            </div>
                            <div role="tabpanel" class="tab-pane row myTabBody" id="Chat" style="padding: 25px;">
                                <g:if test="${comments != null && comments.size()>0}">
                                    <g:each var="comment" in="${comments}">
                                        <g:if test="${comment != null}">
                                            <div class="comment">
                                                Posted by: <img src="${comment.poster.customization.profileImage}" style="max-width: 15px; max-height: 15px;">  ${comment.poster.username} at <h5><b>${comment.timePosted}</b></h5>
                                                <p class="msg">
                                                    ${comment.msg}
                                                </p>
                                            </div>
                                        </g:if>
                                    </g:each>
                                </g:if>
                                <g:else>
                                    Talk to someone
                                </g:else>
                            </div>
                            <div role="tabpanel" class="tab-pane row myTabBody" id="Friends" style="padding: 25px;">
                                <div class="col-md-8">
                                    <g:if test="${user.friends != null && user.friends.size() >0}">
                                        <g:each var="friend" in="${user.friends}">
                                            <g:if test="${friend != null}">
                                                <div class="comment">
                                                    <h2><strong>${friend.getUsername()}</strong></h2>
                                                    <g:form controller="profile" action="index" params="[viewing:friend.getUsername()]">
                                                        <button class="btn btn-info" type="button">Go to Profile</button>
                                                    </g:form>
                                                </div>
                                            </g:if>
                                        </g:each>
                                    </g:if>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <g:textField name="search" placeholder="search"> </g:textField>
                                        <button class="btn btn-primary">Search</button>
                                    </div>
                                </div>
                            </div>
                            <div role="tabpanel" class="tab-pane row myTabBody" id="GameInfo" style="padding: 20px; overflow: auto">
                                <div class="comment">
                                    <img src="${resource(dir: 'images', file: 'cruiser.png')}" style="max-height: 100px; max-width: 100px">
                                    <p>${Battle.SHIPS_INFO_ARRAY[0]}</p>
                                </div>

                                <div class="comment">
                                    <img src="${resource(dir: 'images', file: 'submarine.png')}" style="max-height: 100px; max-width: 100px">
                                    <p>${Battle.SHIPS_INFO_ARRAY[1]}</p>
                                </div>

                                <div class="comment">
                                    <img src="${resource(dir: 'images', file: 'destroyer.png')}" style="max-height: 100px; max-width: 100px">
                                    <p>${Battle.SHIPS_INFO_ARRAY[2]}</p>
                                </div>

                                <div class="comment">
                                    <img src="${resource(dir: 'images', file: 'battleship.png')}" style="max-height: 100px; max-width: 100px">
                                    <p>${Battle.SHIPS_INFO_ARRAY[3]}</p>
                                </div>

                                <div class="comment">
                                    <img src="${resource(dir: 'images', file: 'carrier.png')}" style="max-height: 100px; max-width: 100px">
                                    <p>${Battle.SHIPS_INFO_ARRAY[4]}</p>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>

        </g:if>

        <div class="row" style="margin-top: 25px;height: 60px;background-color: lightgrey">
            <h1 class="text-center">made with grails for csc435</h1>
        </div>

    </div>



    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    </body>
</html>









