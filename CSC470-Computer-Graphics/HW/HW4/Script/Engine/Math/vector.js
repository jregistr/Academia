/*<Region description="Vector object definition">*/
"use strict";

/**
* Description for Vec2
* @public
* @method Vec2
* @param {Number} x The x component.
* @param {Number} y The y component.
* @return {Vec2} Returns a Vector object of constructed with the x and y component provided
*/
var Vec2 = (function (x, y) {
    if (x === undefined || y === undefined)
        return null;
    else
        return {
            dim: 2, x: x, y: y, toArray: function () { return [x, y] },
            copy: function () { return Vec2(x, y) },
            toString: function () { return "(" + x + "," + y + ")" }
        }
});

/**
* Description for Vec3
* @public
* @method Vec3
* @param {Number} x The x component.
* @param {Number} y The y component.
* @param {Number} z The z component.
* @return {Vec3} Returns a Vector object of constructed with the x, y and z component provided.
*/
var Vec3 = (function (x, y, z) {
    if (x === undefined || y === undefined || z === undefined)
        return null;
    else
        return {
            dim: 3, x: x, y: y, z: z, toArray: function () { return [x, y, z] },
            copy: function () { return Vec3(x, y, z) },
            toString: function () { return "(" + x + "," + y + "," + z + ")" }
        }
});

/**
* @public
* @method Vec4
* @param {Number} x The x component.
* @param {Number} y The y component.
* @param {Number} z The z component.
* @param {Number} w The w component.
* @return {Vec4} Returns a Vector object of constructed with the x, y, z and w component provided.
*/
var Vec4 = (function (x, y, z, w) {
    if (x === undefined || y === undefined || z === undefined || w === undefined)
        return null;
    else
        return {
            dim: 4, x: x, y: y, z: z, w: w, toArray: function () { return [x, y, z, w] },
            copy: function () { return Vec4(x, y, z, w) },
            toString: function () { return "(" + x + "," + y + "," + z + "," + w + ")" }
        }
});
/*</Region>*/
/**
 * A collection of common Vector operations.
 * @class Vecs
 * @static
 */
var Vecs = (function () {

    /**
     * Returns a new Vector object by adding the components of A and B or,
     * null if the params are not both Vectors of the same dimension.
     */
    function add(A, B) {
        try {
            var dA = A.dim,
                dB = B.dim;
            if (dA !== undefined && dB !== undefined && dA === dB) {
                if (dA === 2) {
                    return Vec2(A.x + B.x, A.y + B.y);
                } else if (dA === 3) {
                    return Vec3(A.x + B.x, A.y + B.y, A.z + B.z);
                } else {
                    return Vec4(A.x + B.x, A.y + B.y, A.z + B.z, A.w + B.w);
                }
            } else
                return null;
        } catch (err) {
            console.error(err);
            return null;
        }
    }

    /**
     * Returns a new Vector object by substracting the components of A and B, or
     * null (if the params aren't both Vector objects of the same dimension).
     */
    function minus(A, B) {
        try {
            var dA = A.dim,
                dB = B.dim
            if (dA !== undefined && dB !== undefined && dA === dB) {
                if (dA === 2) {
                    return Vec2(A.x - B.x, A.y - B.y);
                } else if (dA === 3) {
                    return Vec3(A.x - B.x, A.y - B.y, A.z - B.z);
                } else {
                    return Vec4(A.x - B.x, A.y - B.y, A.z - B.z, A.w - B.w);
                }
            } else
                return null;
        } catch (err) {
            console.error(err);
            return null;
        }
    }

    /*
     * Returns a new Vector by multiplying all components of A by b, or null (if
     * the first param is not a Vector and the second is not a Number).
     */
    function scalarMultiply(A, b) {
        try {
            var dA = A.dim
            if (dA !== undefined) {
                if (dA === 2) {
                    return Vec2(A.x * b, A.y * b)
                } else if (dA === 3) {
                    return Vec3(A.x * b, A.y * b, A.z * b)
                } else {
                    return Vec4(A.x * b, A.y * b, A.z * b, A.w * b)
                }
            } else
                return null
        } catch (err) {
            console.error(err)
            return null
        }
    }

    /**
     * Returns the dot product of A and B or null (if both A and B are not Vectors in the same dimension)
     */
    function dot(A, B) {
        try {
            var dA = A.dim,
                dB = B.dim
            if (dA !== undefined && dB !== undefined && dA === dB) {
                if (dA === 2) {
                    return (A.x * B.x) + (A.y * B.y);
                } else if (dA === 3) {
                    return (A.x * B.x) + (A.y * B.y) + (A.z * B.z);
                } else {
                    return (A.x * B.x) + (A.y * B.y) + (A.z * B.z) + (A.w * B.w);
                }
            } else
                return null
        } catch (err) {
            console.error(err)
            return null
        }
    }

    /*
     * Returns the cross product of A and B or null (if both A and B are not Vectors in the same dimension).
     */
    function cross(A, B) {
        try {
            var dA = A.dim,
                dB = B.dim
            if (dA !== undefined && dB !== undefined && dA === 3 && dB === 3) {
                return Vec3(A.y * B.z - A.z * B.y, -(A.x * B.z - A.z * B.x), A.x * B.y - A.y * B.x)
            } else
                return null;
        } catch (err) {
            console.error(err)
            return null
        }
    }

    function length(A) {
        try {
            return Math.sqrt(dot(A,A))
        } catch (err) {
            console.error(err)
            return null
        }
    }
    
    function normalize(A){
        try{
            var dA = A.dim
            var l = length(A)
            if(dA === 2){
                return l != 0 ? Vec2(A.x/l, A.y/l) : Vec3(0, 0, 0)
            }else if(dA === 3){
                return l != 0 ? Vec3(A.x/l, A.y/l, A.z/l) : Vec3(0, 0, 0)
            }else{
                return l != 0 ? Vec4(A.x/l, A.y/l, A.z/l, A.w/l) : Vec3(0, 0, 0)
            }
        }catch(err){
            console.error(err)
            return null
        }
    }

    /*
     * Returns an array containing all the components of the vectors in list.
     */
    function flatten(list) {
        try {
            if (list.length > 1) {
                var dA = list[0].dim
                var temp = []
                $.each(list, function (index, elem) {
                    if (dA === 2) {
                        temp.push(elem.x, elem.y)
                    } else if (dA === 3) {
                        temp.push(elem.x, elem.y, elem.z)
                    } else {
                        temp.push(elem.x, elem.y, elem.z, elem.w)
                    }
                })
                return temp
            } else if (list.length == 1) {
                var el = list[0]
                if (el.dim === 2)
                    return [el.x, el.y]
                else if (el.dim === 3)
                    return [el.x, el.y, el.z]
                else
                    return [el.x, el.y, el.z, el.w]
            } else
                return null
        } catch (err) {
            console.error(err)
            return null
        }
    }

    return {
        add:add,
        minus:minus,
        scalarMultiply:scalarMultiply,
        dot:dot,
        cross:cross,
        length:length,
        normalize:normalize,
        flatten:flatten
    }

})();