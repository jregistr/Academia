<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MiniFlix</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../images/logo.png">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="../stylesheets/application.css">
    <link rel="stylesheet" href="../stylesheets/dashboard.css">
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
                    <img class="nav-image" src="../images/logo.png">
                </span>
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">${name}</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row" style="margin-top: 10px">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="/profile"><span class="glyphicon glyphicon-home"></span>Home</a></li>
                <li><a href="/profile/myhistory"><span class="glyphicon glyphicon-hourglass"></span>History</a></li>
                <li class="active"><a href="#"><span class="glyphicon glyphicon-cloud"></span>Recomendations</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div id="allmovies" class="row">

               <#-- <div class="col-lg-12 col-mg-12 col-sm-12">
                    <div class="col-lg-12 col-mg-12 col-sm-12">
                        <div class="alert alert-success" role="alert">
                            <strong>Well done!</strong> You successfully read this important alert message.
                        </div>
                    </div>

                    <div class="col-lg-12 col-mg-12 col-sm-12">
                        <div class="col-lg-12 col-mg-12 col-sm-12">
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6"><div class="panel panel-info"><div class="panel-heading panel-heading-size">History Channel Presents: Mountain Men: Season 4, Vol. 1</div><div class="panel-body"><img class="img-responsive img-thumbnail img-movie" src="../images/gif/2.gif"><div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" style="width:90%"></div></div></div></div><div class="panel-footer"><div class="row"><div class="col-lg-2"><button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-play"></span></button></div><div class="col-lg-4"></div><div><input type="radio" name="2" value="1"><input type="radio" name="2" value="2"><input type="radio" name="2" value="3"><input type="radio" name="2" value="4"><input type="radio" name="2" value="5"></div></div></div></div></div>
                        </div>
                        <div class="col-lg-12 col-mg-12 col-sm-12">
                            <ul class="pagination">
                                <li><a>1</a></li>
                                <li><a>1</a></li>
                                <li><a>1</a></li>
                                <li><a>1</a></li>
                                <li><a>1</a></li>
                                <li><a>1</a></li>
                            </ul>
                        </div>
                    </div>
                </div>-->

            </div>
        </div>

    <#-- <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
         <h1 class="page-header">Dashboard</h1>
     </div>-->
    </div>

</div>

<script src="../javascripts/lib/jquery.min.js"></script>
<script src="../javascripts/lib/bootstrap.min.js"></script>
<script src="../javascripts/js/util.js"></script>
<script src="../javascripts/js/movie.js"></script>
<script src="../javascripts/js/catalog.js"></script>
<script src="../javascripts/js/recommend.js"></script>
</body>
</html>