
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reader</title>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" >
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.min.css')}">
    <link rel="shortcut icon" href="${resource(dir: 'stylesheets', file: 'bootstrap-theme.min.css')}" >
</head>

<body>
<div class="row" style="margin: 10px auto">
    <g:form controller="reader" action="parse" class="form-inline">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon"><span class="glyphicon glyphicon-globe"></span></div>
                <g:textField name="url" placeholder="Enter site url" class="form-control input-lg"> </g:textField>
                <div class="input-group-addon"><span class="glyphicon glyphicon-arrow-right"></span></div>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Read</button>
    </g:form>
</div>

<div class="row" style="margin: 20px auto">
    <g:form controller="reader" action="distance" class="form-inline">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon"><span class="glyphicon glyphicon-globe"></span></div>
                <g:textField name="first" placeholder = "First" class="form-control input-lg"> </g:textField>
            </div>
            <div class="input-group">
                <div class="input-group-addon"><span class="glyphicon glyphicon-globe"></span></div>
                <g:textField name="second" placeholder = "Second" class="form-control input-lg"> </g:textField>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Compare</button>
    </g:form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>
</html>