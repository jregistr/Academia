var Movie = (function(id, title, progress, rating, ratingClicked, playClicked){

    var outer = document.createElement("div");
    var panel = document.createElement("div");
    out.classList.add("col-lg-3");
    panel.className = "panel panel-info";

    function makeHeader(){
        var div = document.createElement("div");
        div.className = "panel-heading";
        div.appendChild(document.createTextNode("" + title));
        return div;
    }

    function makeFooter(){
        var div = document.createElement("div");
        div.className = "panel-footer";
        var row = document.createElement("div");
        row.className = "row";

        div.appendChild(row);

        var playDiv = document.createElement("div");
        var playBtn = document.createElement("button");
        var span = document.createElement("span");

        playDiv.className = "col-lg-2";
        playBtn.className = "btn btn-danger";
        span.className = "glyphicon glyphicon-play";

        playBtn.appendChild(span);
        playDiv.appendChild(playBtn);
        row.appendChild(playDiv);

        var spacer = document.createElement("div");
        spacer.className = "col-lg-4";

        row.appendChild(spacer);


        return div;
    }

});