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
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Users</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="usersTable" data-toggle="table" data-url="/users"
                           data-pagination="true"
                           data-search="true"
                           data-height="600"
                           data-sort-name="UserName"
                           data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="UserName" data-sortable="true">User Name</th>
                            <th data-field="Password" data-sortable="true">Password</th>
                            <th data-field="Email" data-sortable="true">Email</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>History</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="userInfoData" data-toggle="table"
                           data-pagination="true"
                           data-search="false"
                           data-height="300"
                           data-sort-name="ID"
                           data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="ID" data-sortable="true"> Movie ID</th>
                            <th data-field="Title" data-sortable="true"> Movie Title</th>
                            <th data-field="Progress" data-sortable="true">Watch Progress</th>
                            <th data-field="Rating" data-sortable="true">User Rating</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Recommendation</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="userRecommendation" data-toggle="table"
                           data-pagination="true"
                           data-search="false"
                           data-height="400"
                           data-sort-name="ID"
                           data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="ID" data-sortable="true"> Movie ID</th>
                            <th data-field="Title" data-sortable="true"> Movie Title</th>
                            <th data-field="Category" data-sortable="true">Category</th>
                        </tr>
                        </thead>
                    </table>
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