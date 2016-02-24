"use strict";

/*Builds a square object with it's cordinates around the point provided according to the size*/
var Square = (function(x, y, size){
    
    if(x === undefined || y === undefined || size === undefined){
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
            },
            toString:function(){
                return tL.toString() + "," + bL.toString() + "," + bR.toString() + "," + tR.toString();
            }
        }
        
    }
    
})