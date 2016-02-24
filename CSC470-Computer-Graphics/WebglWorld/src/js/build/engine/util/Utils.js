define(["require", "exports"], function (require, exports) {
    var util;
    (function (util) {
        var Utils = (function () {
            function Utils() {
                throw ("Do not instantiate");
            }
            /**
             * Pushes all values from lists provided into target list.
             * @param target The target to push values to.
             * @param lists The lists whose values will be pushed into target.
             */
            Utils.pushListValuesToList = function (target) {
                var lists = [];
                for (var _i = 1; _i < arguments.length; _i++) {
                    lists[_i - 1] = arguments[_i];
                }
                if (target != null && lists != null && lists.length > 0) {
                    var l = lists.length;
                    for (var i = 0; i < l; i++) {
                        var cur = lists[i];
                        for (var j = 0; j < cur.length; j++) {
                            target.push(cur[j]);
                        }
                    }
                }
                else
                    console.warn("Strange parameters");
            };
            return Utils;
        })();
        util.Utils = Utils;
    })(util || (util = {}));
    return util;
});
