"use strict";

var Util = (function () {

    return {

       /* pushList: function() {
            var end = arguments[arguments.length - 1];
            for (var i = 0; i < arguments.length - 1; i++) {
                var list = arguments[i];
                $.each(list, function (index, value) {
                    end.push(value);
                });
            }
        },*/


        /*Pushes all the elements in the lists provided into the last list*/
              pushList: function () {
                  if(arguments.length > 1){
                      try{
                          var end = arguments[arguments.length - 1].slice()
                          for(var i = 0; i < arguments.length - 1; i ++){
                              var list = arguments[i];
                              $.each(list, function (index, value) {
                                  end.push(value);
                              });
                          }
                          return end;
                      }catch(err){
                          return null
                      }
                  }else{
                      return (arguments.length == 0) ? null : (Array.isArray(arguments[0])) ? arguments[0] : null
                  }
              },

        /*Returns an array of size 4 constructed with 3 random numbers and 1 as the last value to form a fully opaque color*/
        randomColor: function () {
            return [Math.random(), Math.random(), Math.random(), 1]
        }

    }

})()