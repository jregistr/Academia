<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" >
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}">
    <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'bootstrap-theme.min.css')}" >

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
</head>
<body style="background-image: url(${resource(dir: 'images', file: 'treeBack.jpg')}); background-size: 100%">

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand logo" href="#">Graphs and Trees, YAY!</a>
        </div>
    </div>
</nav>

<div class="container-fluid row" style="margin: 150px auto;">
    <div class="col-md-3">

    </div>
    <div class="col-md-6" style="min-height: 300px">
        <img src="${resource(dir: 'images', file: 'loader.gif')}" height="512" width="512">
    </div>
    <div class="col-md-3">

    </div>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script>
    function checkLoad(){
        $.ajax({
            url:"${createLink(controller:'welcome', action:'isDoneLoading')}",
            type:"GET",
            success:function(response){
                alert(response);
            }
        });
    }
    $( document ).ready(function() {
        setInterval(checkLoad,5000);
    });
</script>
</body>
</html>









