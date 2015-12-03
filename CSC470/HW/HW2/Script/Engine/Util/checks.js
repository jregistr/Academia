"use strict";

/*A class to hold common preconditions in a unified location.*/
var Checks = (function () {

    /*Returns the dimention of A or -1 if it fails.*/
    function vectorDimension(A) {
        try {
            return A.dim;
        } catch (err) {
            return -1;
        }
    }

    /*Returns true if a is a number.*/
    function isNum(a) {
        return !isNaN(a)
    }

    /*Returns true if all the arguments are defined*/
    function allDefined() {
        var truth = true
        if (arguments != null && arguments.length > 0) {
            $.each(arguments, function (index, value) {
                if (value === undefined) {
                    truth = false
                    return false;
                }
            })
        } else {
            truth = false
        }
        return truth
    }

    /*Returns true if all the arguments are Vectors that are all of the same dimention.*/
    function areValidVectors() {
        var truth = true
        if (arguments != null && arguments.length > 0) {
            if (arguments.length > 1) {
                var last = arguments[0]
                var lastDim = vectorDimension(last)
                if(vectorDimension(last) !== -1){
                    for(var i = 1; i < arguments.length; i ++){
                        var temp = arguments[i]
                        var tempDim = vectorDimension(temp)
                        if(tempDim === -1 || tempDim !== lastDim){
                            truth = false
                            break
                        }
                        last = temp
                        lastDim = tempDim
                    }
                }else{
                    truth = false
                }
            } else {
                var only = arguments[0]
                truth = vectorDimension(only) !== -1
            }
        } else {
            truth = false
        }
        return truth
    }

    return {
        vectorDimension:vectorDimension,
        isNum:isNum,
        allDefined:allDefined,
        areValidVectors:areValidVectors
    }

})();