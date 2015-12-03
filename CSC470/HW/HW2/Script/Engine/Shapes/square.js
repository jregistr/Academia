"use strict";

/*Builds a square object with it's cordinates around the point provided according to the size*/
var Square = (function(x, y, size){
    
    if(!(Checks.isNum(x) && Checks.isNum(y) && Checks.isNum(size))){
        return null
    }else{
        
        var halfSize = size/2
        var tL = Vec2(x - halfSize, y + halfSize)
        var bL = Vec2(x - halfSize, y - halfSize)
        var bR = Vec2(x + halfSize, y - halfSize)
        var tR = Vec2(x + halfSize, y + halfSize)
        
        return{
            tl:tL.copy(),
            bl:bL.copy(),
            br:bR.copy(),
            tr:tR.copy(),
            position:function(){
                return Vec2(x, y)
            },
            size:size,
            flatten:function(){
                return[tL.copy(), bL.copy(), bR.copy(), tL.copy(), tR.copy(), bR.copy()]
                 /*return [Vector2(tL.getX, tL.getY), Vector2(bL.getX, bL.getY), Vector2(bR.getX, bR.getY), Vector2(tL.getX, tL.getY), 
                         Vector2(tR.getX, tR.getY),Vector2(bR.getX, bR.getY)];*/
            },
            toString:function(){
                return tL.toString() + "," + bL.toString() + "," + bR.toString() + "," + tR.toString();
               /* return "(" + tL.x + "," + tL.y + ")" + "      "  + "(" + tR.x + "," + tR.y + ")" + "\n\n\n" + "(" + bL.x + "," + bL.y + ")" +
                    "      " + "(" + bR.x + "," + bR.y + ")";*/
            }
        }
        
    }
    
})