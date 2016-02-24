define(["require", "exports"], function (require, exports) {
    var math;
    (function (math) {
        var MathFs = (function () {
            function MathFs() {
                throw ("Do not instanciate this class");
            }
            /**
             * @param angle The angle in degrees.
             * @returns {number} The angle converted to radians.
             */
            MathFs.degToRad = function (angle) {
                return angle * Math.PI / 180.0;
            };
            return MathFs;
        })();
        math.MathFs = MathFs;
    })(math || (math = {}));
    return math;
});
