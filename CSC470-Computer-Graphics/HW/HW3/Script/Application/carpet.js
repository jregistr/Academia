"use strict";

var carpetMaker = (function(){

    function makeCarpet(initialSquare, subdivisions){
        var closedSquares = []
        if(subdivisions > 0){
            var openSquares = []
            closedSquares.push(getFilledSub(initialSquare))
            Util.pushList(getEmptySubs(initialSquare), openSquares)
            subdivisions--
            for(var i = 0; i < subdivisions; i ++){
                var temp = []
                $.each(openSquares, function(index, value){
                    closedSquares.push(getFilledSub(value))
                    Util.pushList(getEmptySubs(value), temp)
                })
                openSquares = temp
            }
            return closedSquares
        }else{
            return [initialSquare]
        }
    }

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