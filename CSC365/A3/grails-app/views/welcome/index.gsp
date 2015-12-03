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

<div class="container-fluid row" style="margin: 150px auto;" id="paramsBox">
    <div class="col-md-3">

    </div>
    <div class="col-md-6" style="min-height: 300px;">
        <g:if test="${!loaded}">
            <h1><strong class="text-primary">Websites have not been loaded yet. Press the button below to do so</strong></h1>
            <div style="width: 100%; height: 200px;background-color: white">
                <label class="form-control" for="seed">Enter Seed Site</label>
                <input type="text" class="form-control input-lg" id="seed" name="seed" placeholder="Enter seed website">
                <input type="number" min="0" name="amount" id="amount" class="form-control input-lg" placeholder="Enter amount"/>
                <input type="number" min="0" name="depth" id="depth" class="form-control input-lg" placeholder="Enter depth">
                <button id="doLoad1" class="btn-lg btn-primary" type="button" style="width: 100%; height: 100%">Load</button>
            </div>
        </g:if>
        <g:else>
            <div id="doneLoads">
                <h1><strong class="text-primary">Everything has been loaded, Press the big button to START!!</strong></h1>
                <g:form controller="graph" action="index" style="width: 100%; height: 200px">
                    <button class="btn-lg btn-primary" type="submit" style="height: 200px">Go to Graph Page</button>
                </g:form>
            </div>

           %{-- <div class="form-group-lg" style="margin-top: 25px">
                <g:form controller="welcome" action="load" style="width: 100%; height: 100%" class="form-group-lg">
                    <div class="form-group-lg">
                        <label for="seed2">Enter Seed website</label>
                        <g:textField name="seed" id="seed2" class="form-control" placeholder="Seed website"> </g:textField>
                        <button id="doLoad2" class="btn btn-primary" type="button">Re-Load</button>
                    </div>
                </g:form>
            </div>--}%
        </g:else>
    </div>
    <div class="col-md-3">

    </div>
</div>

<div class="container-fluid row" id="loadIndic" style="margin: 150px auto;">
    <div class="col-md-3">

    </div>
    <div class="col-md-6" style="min-height: 300px">
        <img src="${resource(dir: 'images', file: 'loader.gif')}" height="512" width="512" style="margin: auto">
    </div>
    <div class="col-md-3">

    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="application/javascript" src="${resource(dir: 'javascripts', file: 'main.js')}"></script>

</body>
</html>









