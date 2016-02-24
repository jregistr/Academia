define(["require", "exports"], function (require, exports) {
    var math;
    (function (math) {
        /**
         * Class representing vectors of 4 components.
         */
        var Vec4 = (function () {
            /**
             * Constructor for Vec4.
             * @param x The x component.
             * @param y The y component.
             * @param z The z component.
             * @param w The w component.
             */
            function Vec4(x, y, z, w) {
                this.xComp = x;
                this.yComp = y;
                this.zComp = z;
                this.wComp = w;
            }
            /**
             * Adds the two provided vectors.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec4} A Vector that is the sum of A and B.
             */
            Vec4.add = function (A, B) {
                return new Vec4(A.x + B.x, A.y + B.y, A.z + B.z, A.w + B.w);
            };
            /**
             * Subtracts B from A.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec4} A Vector that is the difference of A and B.
             */
            Vec4.minus = function (A, B) {
                return new Vec4(A.x - B.x, A.y - B.y, A.z - B.z, A.w - B.w);
            };
            /**
             * Calculates the dot product of A and B.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {number} A scalar that is the dot product of A and B.
             */
            Vec4.dot = function (A, B) {
                return (A.x * B.x) + (A.y * B.y) + (A.z * B.z) + (A.w * B.w);
            };
            /**
             * Calculates the scalar product.
             * @param A The first vector.
             * @param b The second vector.
             * @returns {math.Vec4} A vector that is a scalar product of A and b.
             */
            Vec4.scale = function (A, b) {
                return new Vec4(A.x * b, A.y * b, A.z * b, A.w * b);
            };
            /**
             * Determines if two Vectors are equal.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {boolean} True if A and B are equal.
             */
            Vec4.equals = function (A, B) {
                return A.x == B.x && A.y == B.y && A.z == B.z && A.w == B.w;
            };
            Object.defineProperty(Vec4.prototype, "x", {
                /**
                 * @returns {number} The x component of this Vector.
                 */
                get: function () {
                    return this.xComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec4.prototype, "y", {
                /**
                 * @returns {number} The y component of this Vector.
                 */
                get: function () {
                    return this.yComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec4.prototype, "z", {
                /**
                 * @returns {number} The z component of this Vector.
                 */
                get: function () {
                    return this.zComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec4.prototype, "w", {
                /**
                 * @returns {number} The w component of this Vector.
                 */
                get: function () {
                    return this.wComp;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * @returns {number} The length of this Vector.
             */
            Vec4.prototype.length = function () {
                return Math.sqrt(Vec4.dot(this, this));
            };
            /**
             * @returns {math.Vec4} A vector that is the normalized form of this Vector.
             */
            Vec4.prototype.normalized = function () {
                var l = this.length();
                return l != 0 ? new Vec4(this.x / l, this.y / l, this.z / l, this.w / l) : this;
            };
            /**
             * @returns {math.Vec4} A copy of this Vector.
             */
            Vec4.prototype.clone = function () {
                return new Vec4(this.xComp, this.yComp, this.zComp, this.wComp);
            };
            /**
             * Constructs an array containing the components of the vectors in list.
             * @param list The list of Vectors.
             * @returns {number[]} An array containing the components of the vectors in list.
             */
            Vec4.flattenVectorList = function (list) {
                if (list != null && list.length > 0) {
                    var flat = [];
                    for (var i = 0; i < list.length; i++) {
                        var temp = list[i];
                        flat.push(temp.x, temp.y, temp.z, temp.w);
                    }
                    return flat;
                }
                else {
                    throw ("Illegal arguments");
                }
            };
            /**
             * @param list The list of Vectors.
             * @returns {Float32Array} An array containing the components of the vectors in list.
             */
            Vec4.flattenVectorList32 = function (list) {
                return new Float32Array(Vec4.flattenVectorList(list));
            };
            Vec4.prototype.toString = function () {
                return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
            };
            return Vec4;
        })();
        math.Vec4 = Vec4;
    })(math || (math = {}));
    return math;
});
