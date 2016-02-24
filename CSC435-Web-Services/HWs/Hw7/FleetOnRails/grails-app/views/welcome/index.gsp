<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Welcome</title>
        <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" >
        <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}">
        <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'bootstrap-theme.min.css')}" >
        <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'my.css')}" >
    </head>

    <body style="background-image: url(http://i626.photobucket.com/albums/tt345/thedragonblade/introBack.png);background-size: 100%">
        <div class="container navbar navbar-default">
            <g:form class="navbar-form navbar-right" controller="login" action="index">
                <div class="form-group">
                    <g:textField name="username" placeholder="username"> </g:textField>
                </div>
                <div class="form-group">
                    <g:passwordField name="password" placeholder="password"> </g:passwordField>
                </div>
                <button class="btn-primary" name="action" value="login" type="submit">Login</button>
            </g:form>
        </div>
        <div class="container-fluid row" style="margin-top: 50px">
            <div class="col-lg-8">
                <div class="panel">
                    <div class="panel-heading">
                        <h3 class="panel-title">What is Fleet Commanders</h3>
                    </div>
                    <div class="panel-body">
                        <p>
                            Welcome to this amazing app that is so amazingly amazing. Have I said amazing enough?
                            Wondering what amazing is? Well it's something like this app that is so amazing where you press shiny buttons and nice things happen.
                        </p>

                    </div>
                    <div class="panel-footer">
                        <p>
                            This is the footer of my rant. Good luck to you!
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-lg-4" style="background-color: #f5f5f5">
                <g:form controller="register" action="index" class="form-group-lg" style="margin: 10px auto;">
                    <div class="form-group-lg">
                        <label for="regDisp">Display Name</label>
                        <g:textField name="displayname" id="regDisp" placeholder="Enter display name" type="text" class="form-control"> </g:textField>
                    </div>
                    <div class="form-group-lg">
                        <label for="regUser">Username</label>
                        <g:textField name="username" id="regUser" placeholder="Enter username" type="text" class="form-control"> </g:textField>
                    </div>
                    <div class="form-group-lg">
                        <label for="regPass">Password</label>
                        <g:textField name="password" id="regPass" placeholder="Enter password" type="password" class="form-control"> </g:textField>
                    </div>
                    <div class="form-group-lg">
                        <label for="regConf">Confirm password</label>
                        <g:textField name="confirmpassword" id="regConf" placeholder="Enter password" type="password" class="form-control"> </g:textField>
                    </div>
                    <button class="btn-lg btn-success form-control" type="submit"><span class="glyphicon glyphicon-usd"></span><span class="glyphicon glyphicon-usd"></span>Join for free!!<span class="glyphicon glyphicon-usd"></span><span class="glyphicon glyphicon-usd"></span></button>
                </g:form>
            </div>
        </div>
    </body>
</html>