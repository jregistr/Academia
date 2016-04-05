var Movie = (function (id, title, progress, rating, ratingClicked, playClicked) {

    const r1 = radio(id, 1);
    const r2 = radio(id, 2);
    const r3 = radio(id, 3);
    const r4 = radio(id, 4);
    const r5 = radio(id, 5);

    const body = (function () {
        var temp = document.createElement("div");
        temp.className = "panel-body";
        return temp;
    })();

    var panelDiv = (function () {
        var temp = document.createElement("div");
        temp.className = "panel panel-info";
        return panelDiv;
    })();

    var headingDiv = (function () {
        var temp = document.createElement("div");
        temp.className = "panel-heading";
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
        return temp;
    })();

    const outer = (function () {
        var temp = document.createElement("div");
        temp.className = "col-lg-3";
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
        radios.forEach(function (elem, index) {
            elem.checked = rate == index
        });
    }

    return {
        getOuter:outer
    }

});