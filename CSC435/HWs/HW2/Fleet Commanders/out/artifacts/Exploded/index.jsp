
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Fleet Heroes:Welcome</title>

    <meta name="description" content="Interact, socialize, compete and do battle with other players online">
    <meta name="keyword" content="battleship,cruiser,submarine,destroyer,carrier,log,social,battle,fight,sink,sink you,compete">
    <meta name="robots" content="index,follow">

  <!-- Shortcut Icon -->
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">

  </head>

  <body class="container-fluid" id="welcome">
    <div class="navbar navbar-default">
        <div class="navbar-brand" style="font-family: Algerian">
            <h4 class="text-primary navbar-left"><strong>Fleet Commanders</strong></h4>
        </div>
        <form class="navbar-form navbar-right" method="post" action="Login">
            <div class="form-group">
                <input id="logUser" type="text" placeholder="Username" name="username">
            </div>
            <div class="form-group">
                <input id="logPass" type="password" placeholder="password" name="password">
            </div>
            <button class="btn-primary" type="submit">Login</button>
        </form>
    </div>

    <div class="row" style="display: table; max-width: 1400px; min-height:1000px; margin: 0 auto">
        <div class="col-lg-8" style="display: table-cell">
            <div class="panel">
                <div class="panel-heading">
                    <h3 class="panel-title">What is Fleet Commanders</h3>
                </div>
                <div class="panel-body">
                    <p>
                        Echo Park Wes Anderson skateboard actually Helvetica sartorial umami Odd Future crucifix Schlitz,
                        sint culpa. Occaecat Etsy +1, cray irony food truck gluten-free Godard plaid
                        try-hard voluptate sed tattooed iPhone meditation. 3 wolf moon Echo
                        Park beard Marfa PBR single-origin coffee, Bushwick fanny pack.
                        Tousled post-ironic chillwave, skateboard whatever Odd Future freegan
                        Tumblr sunt ex squid anim aute magna. Deserunt four loko Blue Bottle mlkshk.
                    </p>
                    <p>
                        Asymmetrical plaid Brooklyn sustainable iPhone, placeat Etsy PBR. Williamsburg esse
                        meggings, vero quinoa yr scenester irony gentrify mumblecore. Trust fund stumptown
                        crucifix Thundercats, et vero Neutra mlkshk ut lo-fi sapiente VHS retro anim. Qui authentic
                        vero heirloom Pitchfork. Yr squid mumblecore locavore aliqua.
                    </p>
                    <p>
                        Echo Park Wes Anderson skateboard actually Helvetica sartorial umami Odd Future crucifix Schlitz,
                        sint culpa. Occaecat Etsy +1, cray irony food truck gluten-free Godard plaid
                        try-hard voluptate sed tattooed iPhone meditation. 3 wolf moon Echo
                        Park beard Marfa PBR single-origin coffee, Bushwick fanny pack.
                        Tousled post-ironic chillwave, skateboard whatever Odd Future freegan
                        Tumblr sunt ex squid anim aute magna. Deserunt four loko Blue Bottle mlkshk.
                    </p>
                    <p>
                        Asymmetrical plaid Brooklyn sustainable iPhone, placeat Etsy PBR. Williamsburg esse
                        meggings, vero quinoa yr scenester irony gentrify mumblecore. Trust fund stumptown
                        crucifix Thundercats, et vero Neutra mlkshk ut lo-fi sapiente VHS retro anim. Qui authentic
                        vero heirloom Pitchfork. Yr squid mumblecore locavore aliqua.
                    </p>
                    <p>
                        Echo Park Wes Anderson skateboard actually Helvetica sartorial umami Odd Future crucifix Schlitz,
                        sint culpa. Occaecat Etsy +1, cray irony food truck gluten-free Godard plaid
                        try-hard voluptate sed tattooed iPhone meditation. 3 wolf moon Echo
                        Park beard Marfa PBR single-origin coffee, Bushwick fanny pack.
                        Tousled post-ironic chillwave, skateboard whatever Odd Future freegan
                        Tumblr sunt ex squid anim aute magna. Deserunt four loko Blue Bottle mlkshk.
                    </p>
                    <p>
                        Asymmetrical plaid Brooklyn sustainable iPhone, placeat Etsy PBR. Williamsburg esse
                        meggings, vero quinoa yr scenester irony gentrify mumblecore. Trust fund stumptown
                        crucifix Thundercats, et vero Neutra mlkshk ut lo-fi sapiente VHS retro anim. Qui authentic
                        vero heirloom Pitchfork. Yr squid mumblecore locavore aliqua.
                    </p>
                </div>
                <div class="panel-footer">
                    <p>
                        Asymmetrical plaid Brooklyn sustainable iPhone, placeat Etsy PBR. Williamsburg esse
                        meggings, vero quinoa yr scenester irony gentrify mumblecore. Trust fund stumptown.
                    </p>
                </div>
            </div>
        </div>
        <div class="col-lg-4" style="display: table-cell;min-height: 620px; background-color: #f5f5f5">
            <form method="post" action="Register" class="form-group-lg" style="margin: 30px auto;">
                <div class="form-group-lg">
                    <label for="regDisp">Display Name</label>
                    <input id="regDisp" class="form-control" type="text" placeholder="display name" name="displayname">
                </div>

                <div class="form-group-lg">
                    <label for="regUser">Username</label>
                    <input class="form-control" id="regUser" type="text" placeholder="Username" name="username">
                </div>

                <div class="form-group-lg">
                    <label for="regPass">Password</label>
                    <input id="regPass" class="form-control" type="password" placeholder="Password" name="password">
                </div>

                <div class="form-group-lg">
                    <label for="regConf">Password</label>
                    <input id="regConf" class="form-control" type="password" placeholder="Password" name="confpass">
                </div>
                <button class="btn-lg btn-success form-control" type="submit">Join for Free</button>
            </form>
        </div>
    </div>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/main.js"></script>
  </body>
</html>
