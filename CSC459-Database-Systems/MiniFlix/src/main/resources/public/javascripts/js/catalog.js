var Catalog = (function (baseDiv, movies, maxPerPage) {

   // const  = 30;
    const pageCount = movies.length <= maxPerPage ? 1 : Math.ceil(movies.length / maxPerPage);
    var currentPage = 0;
    const ul = document.createElement("ul");
   // const baseDiv = document.getElementById(baseDivID);

    var pageArray = getCurrentPageMovies(currentPage);

    function getCurrentPageMovies(page) {
        const pageBeginIndex = maxPerPage * page;
        const endPageIndex = pageBeginIndex + maxPerPage < movies.length ? pageBeginIndex + maxPerPage : movies.length;
        return movies.slice(pageBeginIndex, endPageIndex);
    }

    //const outer = document.getElementById("movies");
    const outer = baseDiv.getElementsByTagName("div").item(0);

    function drawData() {
        outer.innerHTML = "";
        pageArray.forEach(function (elem) {
            outer.appendChild(elem.getOuter())
        });
    }

    function pageClicked(pageNum) {
        ul.childNodes.item(currentPage).classList.remove("active");
        currentPage = pageNum;
        ul.childNodes.item(pageNum).classList.add("active");
        pageArray = getCurrentPageMovies(currentPage);
        drawData();
    }

    (function () {
        const pagesDiv = baseDiv.getElementsByTagName("div").item(1);
        const fill1 = document.createElement("div");
        fill1.className = "col-lg-2";
        const fill2 = document.createElement("div");
        fill2.className = "col-lg-2";
        const parent = document.createElement("div");
        parent.className = "col-lg-8";
        pagesDiv.appendChild(fill1);
        pagesDiv.appendChild(parent);
        pagesDiv.appendChild(fill2);

        function makePageListItem(num) {
            const li = document.createElement("li");
            const href = document.createElement("a");
            href.setAttribute("href", "#");
            href.appendChild(document.createTextNode("" + (num + 1)));
            li.appendChild(href);
            return li;
        }

        function addListener(button, pageNum) {
            button.addEventListener("click", function () {
                pageClicked(pageNum);
            });
        }

        if (pageCount > 1) {
            ul.className = "pagination";
            const first = (function () {
                const temp = makePageListItem(0);
                temp.className = "active";
                return temp;
            })();
            addListener(first, 0);
            ul.appendChild(first);

            for (var i = 1; i < pageCount; i++) {
                (function (e) {
                    const cur = makePageListItem(e);
                    ul.appendChild(cur);
                    addListener(cur, e);
                })(i)
            }
            parent.appendChild(ul);
        }

    })();

    drawData();

    return {
        getOuter: outer
    }

});