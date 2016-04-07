$(document).ready(function () {

    var catalog = null;

    $.ajax({
        url: '/moviesforuserhist',
        data: {
            uid: Util.getUIDCookie()
        },
        success:function(data){
            const movies = [];
            data.forEach(function (elem) {
                movies.push(new Movie(elem["ID"], elem["Title"], elem["Progress"], elem["Rating"]));
            });
            catalog = new Catalog(document.getElementById("allmovies"), movies, 30);
        },
        error:function(error, other){
            alert(error);
            alert(other);
            console.log(error);
            console.log(other);
        }
    });

});