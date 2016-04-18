<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Stats</title>
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
                <li><a href="/adminprofile"><span class="glyphicon glyphicon-home"></span>Home</a></li>
                <li class="active"><a href="#"><span class="glyphicon glyphicon-hourglass"></span>Stats</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Movies</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="movieTable" data-toggle="table" data-url="/movies"
                           data-pagination="true"
                           data-search="true"
                           data-height="600"
                           data-sort-name="ID"
                           data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="ID" data-sortable="true">ID</th>
                            <th data-field="Title" data-sortable="true">Title</th>
                            <th data-field="Length" data-sortable="true">Length</th>
                            <th data-field="Description">Description</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Stats</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="statsTable" data-toggle="table"
                           data-pagination="true"
                           data-search="true"
                           data-height="200">
                        <thead>
                        <tr>
                            <th data-field="Partial">Partially Watched</th>
                            <th data-field="Fully">Completely Watched</th>
                            <th data-field="AVG">Average Rating</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Recommended To</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <table id="recommendedTo" data-toggle="table"
                           data-pagination="true"
                           data-search="true"
                           data-height="600"
                           data-sort-name="ID"
                           data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="ID" data-sortable="true">User ID</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"><h1>Category Activities</h1></div>
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                 <#--   <div class="input-group">
                        <label for="categories">Category</label>
                        <select class="form-control" id="categories"></select>
                        <span class="input-group-btn">
                            <button class="btn btn-default form-control" type="button">Go!</button>
                        </span>



                    </div>-->
                    <div class="form-group">
                        <div class="input-group ">
                            <label>
                                Category
                                <select class="form-control" id="categories"></select>
                            </label>
                            <button id="changeCat" class="btn btn-default form-control" type="button">Change</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>

</div>

<script src="javascripts/lib/jquery.min.js"></script>
<script src="javascripts/lib/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/bootstrap.table/1.10.1/bootstrap-table.min.js"></script>
<script src="javascripts/js/util.js"></script>
<script src="javascripts/js/adminstats.js"></script>
</body>
</html>