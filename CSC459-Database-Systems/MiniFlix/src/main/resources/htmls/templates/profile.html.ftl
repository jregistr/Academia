<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MiniFlix</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/logo.png">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="stylesheets/application.css">
    <link rel="stylesheet" href="stylesheets/dashboard.css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/profile">
                MiniFlix
                <span>
                    <img class="nav-image" src="images/logo.png">
                </span>
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Name</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid" style="margin-left: 300px">
    <div class="row" style="margin-top: 10px">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">Home<span class="sr-only">(current)</span></a></li>
                <li><a href="#">History</a></li>
                <li><a href="#">Recomendations</a></li>
            </ul>
        </div>
       <#-- <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Dashboard</h1>
        </div>-->
    </div>
    <div class="row">
        <div class="col-lg-3">
            <div class="panel panel-info">
                <div class="panel-heading">
                    Title
                </div>
                <div class="panel-body">
                    <img class="img-responsive img-thumbnail img-movie" src="http://placehold.it/300x180">
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-lg-2">
                            <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button>
                        </div>
                        <div class="col-lg-4"></div>
                        <div class="col-lg-6">
                            <input type="radio" value="1" name="optradio">
                            <input type="radio" value="2" name="optradio">
                            <input type="radio" value="3" name="optradio">
                            <input type="radio" value="4" name="optradio">
                            <input type="radio" value="5" name="optradio">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="javascripts/lib/jquery.min.js"></script>
<script src="javascripts/lib/bootstrap.min.js"></script>
<script src="javascripts/js/profile.js"></script>
</body>
</html>