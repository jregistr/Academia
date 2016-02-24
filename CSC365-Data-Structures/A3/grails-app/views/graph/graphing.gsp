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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/vis/3.12.0/vis.min.css">

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
            <a class="navbar-brand logo" href="">Graphs and Trees</a>
        </div>
        <img src="http://cdn.osxdaily.com/wp-content/uploads/2013/07/dancing-banana.gif" height="25px" width="20" style="margin-top: 5px">
    </div>
</nav>

<div class="container-fluid row" style="margin: 20px auto;">
    <div class="col-md-3" style="min-height: 600px;">
        <div class="panel panel-primary" style="background-color: lightgrey;">
            <div class="panel-heading">
                <h2 class="panel-title text-info"><strong>Start Point</strong></h2>
            </div>
            <div class="panel-body" style="color: black; min-height: 540px;">
                <label for="selectStart">Urls</label>
                <select class="form-control" id="selectStart" style="max-height: 485px" size=${(urls.size() > 20) ? urls.size() : 40}>
                    <g:each var="url" in="${urls}">
                        <option value="${url}">${url}</option>
                    </g:each>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-primary" style="background-color: lightgrey">
            <div class="panel-heading">
                <h2 class="panel-title text-info"><strong>Ending Point</strong></h2>
            </div>
            <div class="panel-body" style="color: black; height: 540px;">
                <label for="selectEnd">Urls</label>
                <select class="form-control" id="selectEnd" style="max-height: 485px" size=${(urls.size() > 0) ? urls.size() : 40}>
                    <g:each var="endUrl" in="${urls}">
                        <option value="${endUrl}">${endUrl}</option>
                    </g:each>
                </select>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="panel panel-info" style="background-color: lightgrey">
            <div class="panel-heading">
                <h1 class="panel-title text-justify">The magical output:---> Spanning Trees:<strong id="spanning"></strong></h1>
            </div>
            <div id="graphDisplay" class="panel-body" style="color: black; min-height: 540px;overflow: auto">

            </div>
        </div>
    </div>
</div>

<div class="container-fluid row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <button id="findPath" type="submit" class="btn-lg btn-primary form-control" style="height: 100%">Make the magic happen</button>
    </div>
    <div class="col-md-3"></div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vis/3.12.0/vis.min.js"></script>
<script type="application/javascript">
    $( document).ready(function(){

        $.ajax({
           url:'/graph/spanning',
            success: function (response) {
                $('#spanning').append(response["kruskals"]);
            }
        });

        $('#findPath').click(function(){
            var startSelector = $('#selectStart');
            var endSelector = $('#selectEnd');
            var start = startSelector.val();
            var end = endSelector.val();
            startSelector.prop('selectedIndex',0);
            endSelector.prop('selectedIndex',0);

            if(start && end){
                $.ajax({
                    url:'/graph/findPath',
                    data:{
                        start:start,
                        end:end
                    },
                    success: function (response) {
                        var disp = $('#graphDisplay');
                        disp.empty();
                        console.log(response);
                        var nodes = [];
                        var edges = [];
                        var count = 1;
                        $.each(response,function(index,value){
                            var uTit = value["url"];
                            var node = {id:count,label:uTit.substring(29,uTit.length)};
                            nodes.push(node);
                            if(nodes.length > 1){
                                var edge = {from:nodes[nodes.length-2].id,to:nodes[nodes.length-1].id,label:value["distance"]};
                                edges.push(edge);
                            }
                            count++;
                         //   disp.append("<h1>" + value["url"] + "<h1> :" + value["distance"] + "<br>");
                        });

                        var container = document.getElementById('graphDisplay');
                        var data = {
                            nodes:nodes,
                            edges:edges
                        };

                        var options = {
                            width: '600px',
                            height: '600px'
                        };

                        var network = new vis.Network(container, data, options);
                    }
                });
            }

        });
    });
</script>
</body>
</html>









