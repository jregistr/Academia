"use strict";

/**
 * Description for Mat3
 * @public
 * @method Mat3
 * @param {Vec3} col1 The first column vector.
 * @param {Vec3} col2 The second column vector.
 * @param {Vec3} col3 The third column vector.
 * @return {Mat3} Returns a Matrix(3x3) object provided that col1, col2, and col3 are all Vec3 objects.
 */
var Mat3 = (function (col1, col2, col3) {
    try{
        if(!(col1 !== undefined && col2 !== undefined && col3 !== undefined && col1.dim === 3 && col2.dim === 3 && col3.dim === 3)){
            return null
        }else{
            return {
                dimension: 3,
                col1: col1,
                col2: col2,
                col3: col3,
                copy: function () {
                    return Mat3(col1.copy(), col2.copy(), col3.copy())
                },
                toString: function () {
                    var mat = "" +
                        "|" + col1.x + " " + col2.x + " " + col3.x + "|\n" +
                        "|" + col1.y + " " + col2.y + " " + col3.y + "|\n" +
                        "|" + col1.z + " " + col2.z + " " + col3.z + "|"
                    return mat
                }
            }
        }
    }catch(err){
        console.error(err)
        return null
    }
})

/**
 * Description for Mat4
 * @public
 * @method Mat4
 * @param {Vec4} col1 The first column vector.
 * @param {Vec4} col2 The second column vector.
 * @param {Vec4} col3 The third column vector.
 * @param {Vec4} col4 The fourth column vector.
 * @return {Mat4} Returns a Matrix(4x4) object provided that col1, col2, col3, and col4 are all Vec4 objects.
 */
var Mat4 = (function (col1, col2, col3, col4) {
    try{
        if(!(col1 !== undefined && col2 !== undefined && col3 !== undefined && col1.dim === 3 && col2.dim === 3 && col3.dim === 3)){
            return null
        }else{
               return {
                dimension: 4,
                col1: col1,
                col2: col2,
                col3: col3,
                col4: col4,
                copy: function () {
                    return Mat4(col1.copy(), col2.copy(), col3.copy(), col4.copy())
                },
                toString: function () {
                    var mat = "" +
                        "|" + col1.x + " " + col2.x + " " + col3.x + " " + col4.x + "|\n" +
                        "|" + col1.y + " " + col2.y + " " + col3.y + " " + col4.y + "|\n" +
                        "|" + col1.z + " " + col2.z + " " + col3.z + " " + col4.z + "|\n" +
                        "|" + col1.w + " " + col2.w + " " + col3.w + " " + col4.w + "|"
                    return mat
                }
            }
        }
    }catch(err){
        console.error(err)
        return null
    }
})

/*A collection of operations performed on matricies*/
var Mats = (function () {

    /*Returns a new matrix that is the sum of A & B.*/
    function add(A, B) {
        try {
            if (A.dimension === 3 && B.dimension === 3) {
                return Mat3(Vec3(A.col1.x + B.col1.x, A.col1.y + B.col1.y, A.col1.z + B.col1.z),
                    Vec3(A.col2.x + B.col2.x, A.col2.y + B.col2.y, A.col2.z + B.col2.z),
                    Vec3(A.col3.x + B.col3.x, A.col3.y + B.col3.y, A.col3.z + B.col3.z))
            } else {
                return Mat4(Vec4(A.col1.x + B.col1.x, A.col1.y + B.col1.y, A.col1.z + B.col1.z, A.col1.w + B.col1.w),
                    Vec4(A.col2.x + B.col2.x, A.col2.y + B.col2.y, A.col2.z + B.col2.z, A.col2.w + B.col2.w),
                    Vec4(A.col3.x + B.col3.x, A.col3.y + B.col3.y, A.col3.z + B.col3.z, A.col3.w + B.col3.w),
                    Vec4(A.col4.x + B.col4.x, A.col4.y + B.col4.y, A.col4.z + B.col4.z, A.col4.w + B.col4.w))
            }
        } catch (err) {
            return null
        }
    }

    /*Returns a new matrix that is the difference of A & B*/
    function minus(A, B) {
        try {
            if (A.dimension === 3 && B.dimension === 3) {
                return Mat3(Vec3(A.col1.x - B.col1.x, A.col1.y - B.col1.y, A.col1.z - B.col1.z),
                    Vec3(A.col2.x - B.col2.x, A.col2.y - B.col2.y, A.col2.z - B.col2.z),
                    Vec3(A.col3.x - B.col3.x, A.col3.y - B.col3.y, A.col3.z - B.col3.z))
            } else {
                return Mat4(Vec4(A.col1.x - B.col1.x, A.col1.y - B.col1.y, A.col1.z - B.col1.z, A.col1.w - B.col1.w),
                    Vec4(A.col2.x - B.col2.x, A.col2.y - B.col2.y, A.col2.z - B.col2.z, A.col2.w - B.col2.w),
                    Vec4(A.col3.x - B.col3.x, A.col3.y - B.col3.y, A.col3.z - B.col3.z, A.col3.w - B.col3.w),
                    Vec4(A.col4.x - B.col4.x, A.col4.y - B.col4.y, A.col4.z - B.col4.z, A.col4.w - B.col4.w))
            }
        } catch (err) {
            return null
        }
    }
    
    /*Returns a new matrix that is the product of A and B. This will only happen if A & B are Mat3 or Mat4*/
    function multiply(A, B){
        try{
            if(A.dimension === 3 && B.dimension === 3){
                var c0r0 = (A.col1.x * B.col1.x) + (A.col2.x * B.col1.y) + (A.col3.x * B.col1.z)
                var c1r0 = (A.col1.x * B.col2.x) + (A.col2.x * B.col2.y) + (A.col3.x * B.col2.z)
                var c2r0 = (A.col1.x * B.col3.x) + (A.col2.x * B.col3.y) + (A.col3.x * B.col3.z)
                
                var c0r1 = (A.col1.y * B.col1.x) + (A.col2.y * B.col1.y) + (A.col3.y * B.col1.z)
                var c1r1 = (A.col1.y * B.col2.x) + (A.col2.y * B.col2.y) + (A.col3.y * B.col2.z)
                var c2r1 = (A.col1.y * B.col3.x) + (A.col2.y * B.col3.y) + (A.col3.y * B.col3.z)
                
                var c0r2 = (A.col1.z * B.col1.x) + (A.col2.z * B.col1.y) + (A.col3.z * B.col1.z)
                var c1r2 = (A.col1.z * B.col2.x) + (A.col2.z * B.col2.y) + (A.col3.z * B.col2.z)
                var c2r2 = (A.col1.z * B.col3.x) + (A.col2.z * B.col3.y) + (A.col3.z * B.col3.z)
                return Mat3(Vec3(c0r0, c0r1, c0r2), Vec3(c1r0, c1r1, c1r2), Vec3(c2r0, c2r1, c2r2))
            }else{
                var c0r0 = (A.col1.x * B.col1.x) + (A.col2.x * B.col1.y) + (A.col3.x * B.col1.z) + (A.col4.x * B.col1.w)
                var c1r0 = (A.col1.x * B.col2.x) + (A.col2.x * B.col2.y) + (A.col3.x * B.col2.z) + (A.col4.x * B.col2.w)
                var c2r0 = (A.col1.x * B.col3.x) + (A.col2.x * B.col3.y) + (A.col3.x * B.col3.z) + (A.col4.x * B.col3.w)
                var c3r0 = (A.col1.x * B.col4.x) + (A.col2.x * B.col4.y) + (A.col3.x * B.col4.z) + (A.col4.x * B.col4.w)
                
                var c0r1 = (A.col1.y * B.col1.x) + (A.col2.y * B.col1.y) + (A.col3.y * B.col1.z) + (A.col4.x * B.col1.w)
                var c1r1 = (A.col1.y * B.col2.x) + (A.col2.y * B.col2.y) + (A.col3.y * B.col2.z) + (A.col4.x * B.col2.w)
                var c2r1 = (A.col1.y * B.col3.x) + (A.col2.y * B.col3.y) + (A.col3.y * B.col3.z) + (A.col4.x * B.col3.w)
                var c3r1 = (A.col1.y * B.col4.x) + (A.col2.y * B.col4.y) + (A.col3.y * B.col4.z) + (A.col4.x * B.col4.w)
                
                var c0r2 = (A.col1.z * B.col1.x) + (A.col2.z * B.col1.y) + (A.col3.z * B.col1.z) + (A.col4.x * B.col1.w)
                var c1r2 = (A.col1.z * B.col2.x) + (A.col2.z * B.col2.y) + (A.col3.z * B.col2.z) + (A.col4.x * B.col2.w)
                var c2r2 = (A.col1.z * B.col3.x) + (A.col2.z * B.col3.y) + (A.col3.z * B.col3.z) + (A.col4.x * B.col3.w)
                var c3r2 = (A.col1.z * B.col4.x) + (A.col2.z * B.col4.y) + (A.col3.z * B.col4.z) + (A.col4.x * B.col4.w)
                
                var c0r3 = (A.col1.w * B.col1.x) + (A.col2.w * B.col1.y) + (A.col3.w * B.col1.z) + (A.col4.w * B.col1.w)
                var c1r3 = (A.col1.w * B.col2.x) + (A.col2.w * B.col2.y) + (A.col3.w * B.col2.z) + (A.col4.w * B.col2.w)
                var c2r3 = (A.col1.w * B.col3.x) + (A.col2.w * B.col3.y) + (A.col3.w * B.col3.z) + (A.col4.w * B.col3.w)
                var c3r3 = (A.col1.w * B.col4.x) + (A.col2.w * B.col4.y) + (A.col3.w * B.col4.z) + (A.col4.w * B.col4.w)
                return Mat4(Vec4(c0r0, c0r1, c0r2, c0r3), Vec4(c1r0, c1r1, c1r2, c1r3), Vec4(c2r0, c2r1, c2r2, c2r3), Vec4(c3r0, c3r1, c3r2, c3r3))
            }
        }catch(err){return null}
    }
    
    /*Returns a new matrix constructed by multiplying A by b*/
    function scalarMult(A, b){
        try{
            if(A.dimension === 3){
                return Mat3(Vecs.scalarMultiply(A.col1, b), Vecs.scalarMultiply(A.col2, b), Vecs.scalarMultiply(A.col3, b))
            }else{
                return Mat4(Vecs.scalarMultiply(A.col1, b), Vecs.scalarMultiply(A.col2, b), Vecs.scalarMultiply(A.col3, b), Vecs.scalarMultiply(A.col4, b))
            }
        }catch(err){
            return null
        }
    }
    
    function makeProjectionMatrix2d(width, height){
        try{
            return Mat3(Vec3(2/width, 0, 1),Vec3(0, 2/height, 1), Vec3(0, 0, 1));
        }catch(err){return null}
    }
 
    function makeRotationMatrix2D(angleInRad){
        
    }

    return {
        add: add,
        minus:minus,
        multiply:multiply,
        projectionMatrix2D:makeProjectionMatrix2d
    }

})()