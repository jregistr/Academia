define(["require", "exports"], function (require, exports) {
    var constants;
    (function (constants) {
        var Constants = (function () {
            function Constants() {
            }
            Object.defineProperty(Constants, "ILLEGAL_ARGUMENTS", {
                get: function () {
                    return "Illegal arguments";
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Constants, "INVALID_MATRIX_FOR_OPERATION", {
                get: function () {
                    return "Invalid matrix for such operation.";
                },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Constants, "SIZE_MISMATCH", {
                get: function () { return "Size mismatch"; },
                enumerable: true,
                configurable: true
            });
            Object.defineProperty(Constants, "UNSUPPORTED_OPERATION", {
                get: function () {
                    return "Operation unsupported";
                },
                enumerable: true,
                configurable: true
            });
            return Constants;
        })();
        constants.Constants = Constants;
    })(constants || (constants = {}));
    return constants;
});
