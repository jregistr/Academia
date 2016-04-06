$(document).ready(function () {
    document.getElementById("movies").appendChild(new Movie(109, "The cool Movie", 10, 3).getOuter());
    /*$("#movies").appendChild(new Movie().getOuter());*/
    /*const bla = (function () {
        return "Cool";
    })();
    console.log(bla);*/
});