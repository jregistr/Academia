const gifList = [
    "../images/gif/1.gif",
    "../images/gif/2.gif",
    "../images/gif/3.gif",
    "../images/gif/4.gif",
    "../images/gif/5.gif",
    "../images/gif/6.gif",
    "../images/gif/7.gif",
    "../images/gif/8.gif",
    "../images/gif/9.gif",
    "../images/gif/10.gif"
];

var Movie = (function (id, title, progress, rating) {

    const r1 = radio(id, 1);
    const r2 = radio(id, 2);
    const r3 = radio(id, 3);
    const r4 = radio(id, 4);
    const r5 = radio(id, 5);

    const progressBody = document.createElement("div");

    const body = (function () {
        var temp = document.createElement("div");
        temp.className = "panel-body";
        const img = document.createElement("img");
        img.className = "img-responsive img-thumbnail img-movie";
        const ind = Math.round(Math.random() * 9);
        img.setAttribute("src", gifList[4]);
        temp.appendChild(img);
        temp.appendChild(progressBody);
        progressBody.appendChild(makeProgress());
        return temp;
    })();

    var panelDiv = function () {
        var temp = document.createElement("div");
        temp.className = "panel panel-info";
        return temp;
    }();

    var headingDiv = (function () {
        var temp = document.createElement("div");
        temp.className = "panel-heading panel-heading-size";
        temp.appendChild(document.createTextNode(title));
        return temp;
    })();

    const footer = (function () {
        const temp = document.createElement("div");
        temp.className = "panel-footer";
        const row = document.createElement("div");
        row.className = "row";
        temp.appendChild(row);

        const playDiv = document.createElement("div");
        playDiv.className = "col-lg-2";
        const playBtn = document.createElement("button");
        playBtn.type = "button";
        playBtn.className = "btn btn-danger";
        const span = document.createElement("span");
        span.className = "glyphicon glyphicon-play";

        playBtn.appendChild(span);
        playDiv.appendChild(playBtn);
        row.appendChild(playDiv);

        const filler = document.createElement("div");
        filler.className = "col-lg-4";

        row.appendChild(filler);

        const bottomDiv = document.createElement("div");
        bottomDiv.appendChild(r1);
        bottomDiv.appendChild(r2);
        bottomDiv.appendChild(r3);
        bottomDiv.appendChild(r4);
        bottomDiv.appendChild(r5);
        setRadioValue(rating, [r1, r2, r3, r4, r5]);
        row.appendChild(bottomDiv);

        playBtn.addEventListener("click", function () {
            playClicked();
        });
        radioEvent(r1, 1);
        radioEvent(r2, 2);
        radioEvent(r3, 3);
        radioEvent(r4, 4);
        radioEvent(r5, 5);

        return temp;
    })();

    const outer = (function () {
        var temp = document.createElement("div");
        temp.className = "col-lg-3 col-md-3 col-sm-4 col-xs-6";
        temp.appendChild(panelDiv);
        panelDiv.appendChild(headingDiv);
        panelDiv.appendChild(body);
        panelDiv.appendChild(footer);
        return temp;
    })();

    function radio(name, value) {
        var button = document.createElement("input");
        button.type = "radio";
        button.name = "" + name;
        button.value = value;
        return button;
    }

    function setRadioValue(rate, radios) {
        if (rate >= 0) {
            radios.forEach(function (elem, index) {
                elem.checked = rate == (index + 1)
            });
        } else {
            radios.forEach(function (elem) {
                elem.checked = false;
            });
        }
    }

    function radioEvent(btn, index) {
        btn.addEventListener("click", function () {
            ratingClicked(index);
        });
    }

    function makeProgress() {
        const temp = document.createElement("div");
        temp.className = "progress";
        const progBarDiv = document.createElement("div");
        progBarDiv.className = "progress-bar";
        progBarDiv.setAttribute("role", "progressbar");
        progBarDiv.setAttribute("aria-valuenow", "" + progress);
        progBarDiv.setAttribute("aria-valuemin", "0");
        progBarDiv.setAttribute("aria-valuemax", "100");
        const width = "width:" + progress + "%";
        progBarDiv.setAttribute("style", width);
        temp.appendChild(progBarDiv);
        return temp;
    }

    function ratingClicked(btnIndex) {
        const endpoint = rating == -1 ? "/addrating" : "/updaterating";
        $.ajax({
            url: endpoint,
            data: {
                uid: Util.getUIDCookie(),
                mid: id,
                rating: btnIndex
            }
        }).then(function (output) {
            if (output["Success"] == true) {
                queryRatings();
            } else {
                setRadioValue(0, [r1, r2, r3, r4, r5])
            }
        });
    }

    function playClicked() {
        const endpoint = progress == 0 ? "/addhistory" : "/updatehistory";
        $.ajax({
            url: endpoint,
            data: {
                uid: Util.getUIDCookie(),
                mid: id,
                progress: progress + 10
            }
        }).then(function (output) {
            if (output["Success"] == true) {
                queryHistory();
            } else {
                alert("Update Failed");
            }
        });
    }

    function queryHistory() {
        $.ajax({
            url: "/histmovieuser",
            data: {
                uid: Util.getUIDCookie(),
                mid: id
            },
            success: function (output) {
                const val = output["Progress"];
                if (val != undefined) {
                    progressBody.innerHTML = "";
                    progress = val;
                    progressBody.appendChild(makeProgress());
                } else {
                    alert("Val undefined");
                }
            },
            error: function (error) {
                alert(error);
            }
        })
    }

    function queryRatings() {
        $.ajax({
            url: "/ratingmovieuser",
            data: {
                uid: Util.getUIDCookie(),
                mid: id
            },
            success: function (output) {
                const succ = output["Success"];
                if (succ == undefined) {
                    rating = output["Rating"];
                    setRadioValue(rating, [r1, r2, r3, r4, r5])
                } else {

                }
            },
            error: function (error) {
                alert(error);
            }
        })
    }

    return {
        getOuter: function () {
            return outer;
        }
    }

});