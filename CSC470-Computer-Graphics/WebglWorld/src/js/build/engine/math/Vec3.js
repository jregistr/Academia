define(["require", "exports"], function (require, exports) {
    var math;
    (function (math) {
        /**
         * Class representing vectors of 3 components.
         */
        var Vec3 = (function () {
            /**
             * Constructor for Vec3.
             * @param x The x component.
             * @param y The y component.
             * @param z The z component.
             */
            function Vec3(x, y, z) {
                this.xComp = x;
                this.yComp = y;
                this.zComp = z;
            }
            /**
             * Adds the two provided vectors.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec3} A Vector that is the sum of A and B.
             */
            Vec3.add = function (A, B) {
                return new Vec3(A.x + B.x, A.y + B.y, A.z + B.z);
            };
            /**
             * Subtracts B from A.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {math.Vec3} A Vector that is the difference of A and B.
             */
            Vec3.minus = function (A, B) {
                return new Vec3(A.x - B.x, A.y - B.y, A.z - B.z);
            };
            /**
             * Calculates the dot product of A and B.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {number} A scalar that is the dot product of A and B.
             */
            Vec3.dot = function (A, B) {
                return (A.x * B.x) + (A.y * B.y) + (A.z * B.z);
            };
            /**
             * Calculates the cross product.
             * @param A The first Vector.
             * @param B The second Vector.
             * @returns {math.Vec3} A Vector that is the cross product of A and B.
             */
            Vec3.cross = function (A, B) {
                return new Vec3(A.y * B.z - A.z * B.y, -(A.x * B.z - A.z * B.x), A.x * B.y - A.y * B.x);
            };
            /**
             * Calculates the scalar product.
             * @param A The first vector.
             * @param b The second vector.
             * @returns {math.Vec3} A vector that is a scalar product of A and b.
             */
            Vec3.scale = function (A, b) {
                return new Vec3(A.x * b, A.y * b, A.z * b);
            };
            /**
             * Determines if two Vectors are equal.
             * @param A The first vector.
             * @param B The second vector.
             * @returns {boolean} True if A and B are equal.
             */
            Vec3.equals = function (A, B) {
                return A.x == B.x && A.y == B.y && A.z == B.z;
            };
            Object.defineProperty(Vec3.prototype, "x", {
                /**
                 * @returns {number} The x component of this Vector.
                 */
                get: function () {
                    return this.xComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec3.prototype, "y", {
                /**
                 * @returns {number} The y component of this Vector.
                 */
                get: function () {
                    return this.yComp;
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Vec3.prototype, "z", {
                /**
                 * @returns {number} The z component of this Vector.
                 */
                get: function () {
                    return this.zComp;
                },
                enumerable: true,
                configurable: true
            });
            /**
             * @returns {number} The length of this Vector.
             */
            Vec3.prototype.length = function () {
                return Math.sqrt(Vec3.dot(this, this));
            };
            /**
             * @returns {math.Vec3} A vector that is the normalized form of this Vector.
             */
            Vec3.prototype.normalized = function () {
                var l = this.length();
                return l != 0 ? new Vec3(this.x / l, this.y / l, this.z / l) : this;
            };
            /**
             * @returns {math.Vec3} A copy of this Vector.
             */
            Vec3.prototype.clone = function () {
                return new Vec3(this.xComp, this.yComp, this.zComp);
            };
            /**
             * Constructs an array containing the components of the vectors in list.
             * @param list The list of Vectors.
             * @returns {number[]} An array containing the components of the vectors in list.
             */
            Vec3.flattenVectorList = function (list) {
                if (list != null && list.length > 0) {
                    var flat = [];
                    for (var i = 0; i < list.length; i++) {
                        var temp = list[i];
                        flat.push(temp.x, temp.y, temp.z);
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
            Vec3.flattenVectorList32 = function (list) {
                return new Float32Array(Vec3.flattenVectorList(list));
            };
            Vec3.prototype.toString = function () {
                return "(" + this.x + "," + this.y + ", " + this.z + ")";
            };
            return Vec3;
        })();
        math.Vec3 = Vec3;
    })(math || (math = {}));
    return math;
});
