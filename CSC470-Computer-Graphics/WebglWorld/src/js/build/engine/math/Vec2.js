define(["require", "exports"], function (require, exports) {
    var math;
    (function (math) {
        /**
         * Class representing vectors of 2 components.
         */
        var Vec2 = (function () {
            /**
             *Constructor for Vec2.
             * @param x The x component.
             * @param y The y component.
             */
            function Vec2(x, y) {
                this.xComp = x;
                this.yComp = y;
            }
            /**
             * Adds the two provided vectors.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec2} A Vector that is the sum of A and B.
             */
            Vec2.add = function (A, B) {
                return new Vec2(A.x + B.x, A.y + B.y);
            };
            /**
             * Subtracts B from A.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec2} A Vector that is the difference of A and B.
             */
            Vec2.minus = function (A, B) {
                return new Vec2(A.x - B.x, A.y - B.y);
            };
            /**
             * Calculates the dot product of A and B.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {number} A scalar that is the dot product of A and B.
             */
            Vec2.dot = function (A, B) {
                return (A.x * B.x) + (A.y * B.y);
            };
            /**
             * Scalar multiplication.
             * @param A The first vector.
             * @param b The second vector.
             * @returns {math.Vec2} A vector that is a scalar product of A and b.
             */
            Vec2.scale = function (A, b) {
                return new Vec2(A.x * b, A.y * b);
            };
            /**
             * Determines if two Vectors are equal.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {boolean} True if A and B are equal.
             */
            Vec2.equals = function (A, B) {
                return A.x == B.x && A.y == B.y;
            };
            Object.defineProperty(Vec2.prototype, "x", {
                /**
                 * @returns {number} The x component of this Vector.
                 */
                get: function () {
                    return this.xComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec2.prototype, "y", {
                /**
                 * @returns {number} The y component of this Vector.
                 */
                get: function () {
                    return this.yComp;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * @returns {number} The length of this Vector.
             */
            Vec2.prototype.length = function () {
                return Math.sqrt(Vec2.dot(this, this));
            };
            /**
             * @returns {math.Vec2} A vector that is the normalized form of this Vector.
             */
            Vec2.prototype.normalized = function () {
                var l = this.length();
                return l != 0 ? new Vec2(this.x / l, this.y / l) : this;
            };
            /**
             * @returns {math.Vec2} A copy of this Vector.
             */
            Vec2.prototype.clone = function () {
                return new Vec2(this.xComp, this.yComp);
            };
            /**
             * Constructs an array containing the components of the vectors in list.
             * @param list The list of Vectors.
             * @returns {number[]} An array containing the components of the vectors in list.
             */
            Vec2.flattenVectorList = function (list) {
                if (list != null && list.length > 0) {
                    var flat = [];
                    for (var i = 0; i < list.length; i++) {
                        var temp = list[i];
                        flat.push(temp.x, temp.y);
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
            Vec2.flattenVectorList32 = function (list) {
                return new Float32Array(Vec2.flattenVectorList(list));
            };
            Vec2.prototype.toString = function () {
                return "(" + this.x + "," + this.y + ")";
            };
            return Vec2;
        })();
        math.Vec2 = Vec2;
    })(math || (math = {}));
    return math;
});
