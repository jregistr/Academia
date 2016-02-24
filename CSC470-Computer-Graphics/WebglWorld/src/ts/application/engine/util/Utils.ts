
module util{
    export class Utils{

        constructor (){
            throw ("Do not instantiate");}

        /**
         * Pushes all values from lists provided into target list.
         * @param target The target to push values to.
         * @param lists The lists whose values will be pushed into target.
         */
        public static pushListValuesToList(target:any[], ...lists:any[]):void{
            if(target != null && lists != null && lists.length > 0){
                var l = lists.length;
                for(var i = 0; i < l; i ++){
                    var cur:any[] = lists[i];
                    for(var j = 0; j < cur.length; j++){
                        target.push(cur[j]);
                    }
                }
            }else
                console.warn("Strange parameters");
        }

    }
}
export = util;
