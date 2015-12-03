"use strict";

var carpetMaker = (function(){
   
/*    function makeCarpet(initialSquare, divisions) {
    var closedSquares = [];
    if (divisions > 0) {
        var openSquares = [];
        closedSquares.push(getFilledSub(initialSquare));
        //pushList(initialSquare.getEmptySubs(), openSquares);
        openSquares = Util.pushList(getEmptySubs(initialSquare), openSquares)
        divisions--;
        for (var i = 0; i < divisions; i++) {
            if (openSquares != null && openSquares.length > 0) {
                var temp = [];
                $.each(openSquares, function (index, value) {
                    if(value != null){
                       // closedSquares.push(getFilledSub(value));
                        closedSquares.push(getFilledSub(value))
                       // pushList(value.getEmptySubs(), temp);
                        temp = Util.pushList(getEmptySubs(value), temp)
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
}*/
    
    
    
    function makeCarpet(initialSquare, subdivisions){
        var closedSquares = []
        if(subdivisions > 0){
            var openSquares = []
            closedSquares.push(getFilledSub(initialSquare))
            openSquares = Util.pushList(getEmptySubs(initialSquare), openSquares)
            
            subdivisions--
            for(var i = 0; i < subdivisions; i ++){
                if(openSquares != null && openSquares.length > 0){
                    var temp = []
                    $.each(openSquares, function(index, value){
                        if(value != null){
                            closedSquares.push(getFilledSub(value))
                            temp = Util.pushList(getEmptySubs(value), temp)
                        }else{console.log("Value null, why???")}
                    })
                    openSquares = temp
                }else{
                    console.log("Open squares is null or has nothing")
                }
            }
            return closedSquares
        }else{
            return [initialSquare]
        }
    }
    
/*    function makeCarpet(initialSquare, divisions) {
        var closedSquares = [];
        if (divisions > 0) {
            var openSquares = [];
            closedSquares.push(getFilledSub(initialSquare));
            Util.pushList(getEmptySubs(initialSquare), openSquares);
            console.log("Hi:" + openSquares.length)
            divisions--;
            for (var i = 0; i < divisions; i++) {
                if (openSquares != null && openSquares.length > 0) {
                    var temp = [];
                    $.each(openSquares, function (ind, val) {
                        if(val != null){
                            closedSquares.push(getFilledSub(val));
                            Util.pushList(getEmptySubs(val), temp);
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
    
    */
    
    function getFilledSub(square){
        try{
            var subSize = square.size/3
            var p = square.position()
            return Square(p.x, p.y, subSize)
        }catch(err){
            console.log("Faile to make sub")
            console.log(err)
            return null
        }
    }
    
    function getEmptySubs(square){
        try{
            var p = square.position()
            var x = p.x, y = p.y, s = square.size/3
            var xs = x + s, ys = y + s, xms = x - s, yms = y-s
            var subSize = square.size/3
            return[
                Square(xms, ys, subSize),Square(xms, y, subSize),Square(xms, yms, subSize),
                Square(x, yms, subSize),
                Square(xs, yms, subSize), Square(xs, y, subSize), Square(xs, ys, subSize),
                Square(x, ys, subSize)
            ]
        }catch(err){
            console.log("Failed to make all the subs")
            console.log(err)
            return null
        }
    }
    
    return {
        makeCarpet:makeCarpet,
        makeFilledSub:getFilledSub
    }
    
})()