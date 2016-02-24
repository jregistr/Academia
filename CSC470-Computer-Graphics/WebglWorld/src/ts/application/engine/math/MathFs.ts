module math{
    export class MathFs{

        constructor(){throw ("Do not instanciate this class");}

        /**
         * @param angle The angle in degrees.
         * @returns {number} The angle converted to radians.
         */
        public static degToRad(angle:number):number{
            return angle * Math.PI/180.0;
        }

    }
}
export = math;
