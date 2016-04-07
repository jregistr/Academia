<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="images/logo.png">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
    <link href="https://cdn.jsdelivr.net/bootstrap.table/1.10.1/bootstrap-table.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="stylesheets/application.css">
    <link rel="stylesheet" href="stylesheets/dashboard.css">
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/adminprofile">
                MiniFlix
                <span>
                    <img class="nav-image" src="images/logo.png">
                </span>
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Admin</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row" style="margin-top: 10px">

        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#"><span class="glyphicon glyphicon-home"></span>Home</a></li>
                <li><a href="/adminstats"><span class="glyphicon glyphicon-hourglass"></span>Stats</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                </div>
            </div>
            <div class="row">
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                </div>
            </div>
            <div class="row">
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                </div>
            </div>
        </div>

    </div>

</div>

<script src="javascripts/lib/jquery.min.js"></script>
<script src="javascripts/lib/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/bootstrap.table/1.10.1/bootstrap-table.min.js"></script>
<script src="javascripts/js/util.js"></script>
<script src="javascripts/js/adminhome.js"></script>

</body>
</html>