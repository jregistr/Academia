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
    <link rel="stylesheet" href="stylesheets/cover.css">
</head>

<body>

<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">
                MiniFlix
                <span>
                    <img class="nav-image" src="images/logo.png">
                </span>
            </a>
        </div>
            <div class="navbar-collapse collapse" id="navbar-main">
                <form action="/login" method="post" class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" name="uname" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="pass" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-default">Sign In</button>
                </form>
            </div>
    </div>
</div>

<div class="container wrapper">

    <div class="row">
        <div class="col-lg-8">

        </div>
        <div class="col-lg-4" style="background-color: #f5f5f5">
            <form action="/register" method="post" class="form-group-lg" style="margin: 10px auto;">
                <div class="form-group-lg">
                    <label>
                        Username
                        <input name="uname" placeholder="Enter username" type="text" class="form-control">
                    </label>
                </div>
                <div class="form-group-lg">
                    <label>
                        Password
                        <input name="pass" placeholder="Enter password" type="password" class="form-control">
                    </label>
                </div>
                <div class="form-group-lg">
                    <label>
                        Confirm password
                        <input name="confPass" placeholder="Confirm password" type="password" class="form-control">
                    </label>
                </div>
                <div class="form-group-lg">
                    <label>
                        Email
                        <input name="email" placeholder="Enter email" type="email" class="form-control">
                    </label>
                </div>
                <button class="btn-lg btn-success form-control" type="submit"><span class="glyphicon glyphicon-usd"></span><span class="glyphicon glyphicon-usd"></span>Join for free!!<span class="glyphicon glyphicon-usd"></span><span class="glyphicon glyphicon-usd"></span></button>
            </form>
        </div>
    </div>

</div>


<script src="javascripts/lib/jquery.min.js"></script>
<script src="javascripts/lib/bootstrap.min.js"></script>
</body>
</html>