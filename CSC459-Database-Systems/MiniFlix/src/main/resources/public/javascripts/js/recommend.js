$(document).ready(function () {

    $.ajax({
        url: '/recommendforthisuser',
        data: {
            uid: Util.getUIDCookie()
        },
        success: function (data) {
            makeData(data);
        },
        error: function (error, other) {
            alert(error);
            alert(other);
            console.log(error);
            console.log(other);
        }
    });

    function makeData(data) {
        var map = new Map();
        data.forEach(function (elem) {
            const category = elem["Category"];
            if(map.has(category)){
                map.get(category).push(new Movie(elem["ID"], elem["Title"], elem["Progress"], elem["Rating"]));
            }else {
                map.set(category, [new Movie(elem["ID"], elem["Title"], elem["Progress"], elem["Rating"])]);
            }
        });

        const parent = document.getElementById("allmovies");
        for(var key of map.keys()){
            parent.appendChild(makeGrouping(key, map.get(key)));
        }
    }

    function makeGrouping(category, movies){
        const parent = document.createElement("div");
        const messageOuter = document.createElement("div");
        messageOuter.className = "col-lg-12 col-mg-12 col-sm-12";
        parent.appendChild(messageOuter);

        const alertDiv = document.createElement("div");
        alertDiv.className = "alert alert-success";
        alertDiv.appendChild(document.createTextNode("We believe you will like these movies because you Watched movie(s) " +
            "From Category:[" + category + "]" + "That you rated highly"));
        messageOuter.appendChild(alertDiv);

        const moviesOuterDiv = document.createElement("div");
        moviesOuterDiv.className = "col-lg-12 col-mg-12 col-sm-12";
        parent.appendChild(moviesOuterDiv);

        const div1 = document.createElement("div");
        div1.className = "col-lg-12 col-mg-12 col-sm-12";
        const div2 = document.createElement("div");
        div2.className = "col-lg-12 col-mg-12 col-sm-12";

        div1.appendChild(document.createTextNode("cool"));
        div2.appendChild(document.createTextNode("cooler"));

        moviesOuterDiv.appendChild(div1);
        moviesOuterDiv.appendChild(div2);

        new Catalog(moviesOuterDiv, movies, 4);

        return parent;
    }



});