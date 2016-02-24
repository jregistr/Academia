define(["require", "exports"], function (require, exports) {
    var geometry;
    (function (geometry) {
        /**
         * A class representing a simple webgl mesh object.
         */
        var Mesh = (function () {
            function Mesh() {
            }
            Object.defineProperty(Mesh.prototype, "count", {
                /**
                 * @returns {number} The number of tris for this mesh.
                 */
                get: function () {
                    return this.triCount;
                },
                enumerable: true,
                configurable: true
            });
            return Mesh;
        })();
        geometry.Mesh = Mesh;
    })(geometry || (geometry = {}));
    return geometry;
});
