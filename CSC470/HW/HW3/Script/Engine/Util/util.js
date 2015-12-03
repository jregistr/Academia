"use strict";

var Util = (function () {

    return {

        pushList: function() {
            try{
                if(arguments.length > 1){
                    var len = arguments.length;
                    var end = arguments[len - 1];
                    for (var i = 0; i < len - 1; i++) {
                        var list = arguments[i];
                        $.each(list, function (index, value) {
                            end.push(value);
                        });
                    }
                }
            }catch(err){
                console.error(err)
            }
        },

        /*Returns an array of size 4 constructed with 3 random numbers and 1 as the last value to form a fully opaque color*/
        randomColor: function () {
            return [Math.random(), Math.random(), Math.random(), 1]
        }

    }

})()