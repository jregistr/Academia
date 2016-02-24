
module math{

    /**
     * Class representing vectors of 2 components.
     */
    export class Vec2{

        private xComp:number;
        private yComp:number;

        /**
         *Constructor for Vec2.
         * @param x The x component.
         * @param y The y component.
         */
        constructor(x:number, y:number){
            this.xComp = x;
            this.yComp = y;
        }

        /**
         * Adds the two provided vectors.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {math.Vec2} A Vector that is the sum of A and B.
         */
        public static add(A:Vec2, B:Vec2):Vec2{
            return new Vec2(A.x + B.x, A.y + B.y);
        }

        /**
         * Subtracts B from A.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {math.Vec2} A Vector that is the difference of A and B.
         */
        public static minus(A:Vec2, B:Vec2):Vec2{
            return new Vec2(A.x - B.x, A.y - B.y);
        }

        /**
         * Calculates the dot product of A and B.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {number} A scalar that is the dot product of A and B.
         */
        public static dot(A:Vec2, B:Vec2):number{
            return (A.x * B.x) + (A.y * B.y);
        }

        /**
         * Scalar multiplication.
         * @param A The first vector.
         * @param b The second vector.
         * @returns {math.Vec2} A vector that is a scalar product of A and b.
         */
        public static scale(A:Vec2, b:number):Vec2{
            return new Vec2(A.x * b, A.y * b);
        }

        /**
         * Determines if two Vectors are equal.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {boolean} True if A and B are equal.
         */
        public static equals(A:Vec2, B:Vec2){
            return A.x == B.x && A.y == B.y;
        }

        /**
         * @returns {number} The x component of this Vector.
         */
        public get x():number{
            return this.xComp;
        }

        /**
         * @returns {number} The y component of this Vector.
         */
        public get y():number{
            return this.yComp;
        }

        /**
         * @returns {number} The length of this Vector.
         */
        public length():number{
            return Math.sqrt(Vec2.dot(this, this));
        }

        /**
         * @returns {math.Vec2} A vector that is the normalized form of this Vector.
         */
        public normalized():Vec2{
            var l:number = this.length();
            return l != 0 ? new Vec2(this.x/l, this.y/l) : this;
        }

        /**
         * @returns {math.Vec2} A copy of this Vector.
         */
        public clone():Vec2{
            return new Vec2(this.xComp, this.yComp);
        }

        /**
         * Constructs an array containing the components of the vectors in list.
         * @param list The list of Vectors.
         * @returns {number[]} An array containing the components of the vectors in list.
         */
        public static flattenVectorList(list:Vec2[]):number[]{
            if(list != null && list.length > 0){
                var flat:number[] = [];
                for(var i:number = 0; i < list.length; i ++){
                    var temp:Vec2 = list[i];
                    flat.push(temp.x, temp.y);
                }
                return flat;
            }else{
                throw ("Illegal arguments");}
        }

        /**
         * @param list The list of Vectors.
         * @returns {Float32Array} An array containing the components of the vectors in list.
         */
        public static flattenVectorList32(list:Vec2[]):Float32Array{
            return new Float32Array(Vec2.flattenVectorList(list));
        }

        public toString():string{
            return `(${this.x},${this.y})`
        }
    }

}
export = math;
