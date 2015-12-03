var Square = (function (tL1, bL1, bR1, tR1) {

    var tL = tL1,
        bL = bL1,
        bR = bR1,
        tR = tR1;

    /*Returns the length of this square by adding the absolute value of the x position of the left and right corner*/
    function size() {
        return tL.getX < 0 ? Math.abs(tL.getX) + tR.getX : tR.getX - tL.getX;
    }

    /**Returns an array of Squares consisting of the 8 empty subsquares inside this Square*/
    function getEmptySubs() {
        var s = size() / 3;
        var x = tL.getX,
            y = tL.getY;
        var s2 = 2 * s,
            s3 = 3 * s;
        return [
			Square(Vector2(x, y), Vector2(x, y - s), Vector2(x + s, y - s), Vector2(x + s, y)),
			Square(Vector2(x, y - s), Vector2(x, y - s2), Vector2(x + s, y - s2), Vector2(x + s, y - s)),
			Square(Vector2(x, y - s2), Vector2(x, y - s3), Vector2(x + s, y - s3), Vector2(x + s, y - s2)),
			Square(Vector2(x + s, y - s2), Vector2(x + s, y - s3), Vector2(x + s2, y - s3), Vector2(x + s2, y - s2)),
			Square(Vector2(x + s2, y - s2), Vector2(x + s2, y - s3), Vector2(x + s3, y - s3), Vector2(x + s3, y - s2)),
			Square(Vector2(x + s2, y - s), Vector2(x + s2, y - s2), Vector2(x + s3, y - s2), Vector2(x + s3, y - s)),
			Square(Vector2(x + s2, y), Vector2(x + s2, y - s), Vector2(x + s3, y - s), Vector2(x + s3, y)),
			Square(Vector2(x + s, y), Vector2(x + s, y - s), Vector2(x + s2, y - s), Vector2(x + s2, y))
		];
    }

    /*Returns the filled subsquare inside this square*/
    function getFilledSub() {
        var s = size() / 3;
        var indiv = size() / 3;
        return Square(Vector2(tL.getX + indiv, tL.getY - indiv), Vector2(tL.getX + indiv, tL.getY - (2 * indiv)),
            Vector2(tL.getX + (2 * indiv), tL.getY - (2 * indiv)), Vector2(tL.getX + (2 * indiv), tL.getY - indiv));
    }

    /*Returns an array of Vector2(s) of triangles that form this exact Square*/
    function toTriangleCords() {
        return [Vector2(tL.getX, tL.getY), Vector2(bL.getX, bL.getY), Vector2(bR.getX, bR.getY), Vector2(tL.getX, tL.getY), Vector2(tR.getX, tR.getY),
			   Vector2(bR.getX, bR.getY)];
    }

    function toString() {
        return tL.toString() + "," + bL.toString() + "," + bR.toString() + "," + tR.toString();
    }

    return {
        topLeft: tL,
        bottomLeft: bL,
        bottomRight: bR,
        topRight: tR,
        size: size,
        toTriangleCords: toTriangleCords,
        getFilledSub: getFilledSub,
        getEmptySubs: getEmptySubs,
        toString: toString
    }

});

/*Makes a carpet by pushing all the squares to be colored into the closed squares and others into opensquares*/
function makeCarpet(divisions, initialSquare) {
    var closedSquares = [];
    if (divisions > 0) {
        var openSquares = [];
        closedSquares.push(initialSquare.getFilledSub());
        pushList(initialSquare.getEmptySubs(), openSquares);
        
        $.each(openSquares, function(inde, va){
            console.log(va.toString())
        })
        
        
        divisions--;
        for (var i = 0; i < divisions; i++) {
            if (openSquares != null && openSquares.length > 0) {
                var temp = [];
                $.each(openSquares, function (index, value) {
                    if(value != null){
                        closedSquares.push(value.getFilledSub());
                        pushList(value.getEmptySubs(), temp);
                    }
                });
                openSquares = temp;
            }
        }
        return closedSquares;
    } else {
        closedSquares.push(initialSquare);
        return closedSquares;
    }
}