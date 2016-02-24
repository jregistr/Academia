
module math{

    /**
     * Class representing vectors of 4 components.
     */
    export class Vec4{

        private xComp:number;
        private yComp:number;
        private zComp:number;
        private wComp:number;

        /**
         * Constructor for Vec4.
         * @param x The x component.
         * @param y The y component.
         * @param z The z component.
         * @param w The w component.
         */
        constructor(x:number, y:number, z:number, w:number){
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
        public static add(A:Vec4, B:Vec4):Vec4{
            return new Vec4(A.x + B.x, A.y + B.y, A.z + B.z, A.w + B.w);
        }

        /**
         * Subtracts B from A.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {math.Vec4} A Vector that is the difference of A and B.
         */
        public static minus(A:Vec4, B:Vec4):Vec4{
            return new Vec4(A.x - B.x, A.y - B.y, A.z - B.z, A.w - B.w);
        }

        /**
         * Calculates the dot product of A and B.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {number} A scalar that is the dot product of A and B.
         */
        public static dot(A:Vec4, B:Vec4):number{
            return (A.x * B.x) + (A.y * B.y) + (A.z * B.z) + (A.w * B.w);
        }

        /**
         * Calculates the scalar product.
         * @param A The first vector.
         * @param b The second vector.
         * @returns {math.Vec4} A vector that is a scalar product of A and b.
         */
        public static scale(A:Vec4, b:number):Vec4{
            return new Vec4(A.x * b, A.y * b, A.z * b, A.w * b);
        }

        /**
         * Determines if two Vectors are equal.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {boolean} True if A and B are equal.
         */
        public static equals(A:Vec4, B:Vec4):boolean{
            return A.x == B.x && A.y == B.y && A.z == B.z && A.w == B.w;
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
         * @returns {number} The z component of this Vector.
         */
        public get z():number{
            return this.zComp;
        }

        /**
         * @returns {number} The w component of this Vector.
         */
        public get w():number{
            return this.wComp;
        }

        /**
         * @returns {number} The length of this Vector.
         */
        public length():number{
            return Math.sqrt(Vec4.dot(this, this));
        }

        /**
         * @returns {math.Vec4} A vector that is the normalized form of this Vector.
         */
        public normalized():Vec4{
            var l:number = this.length();
            return l != 0 ? new Vec4(this.x/l, this.y/l, this.z/l, this.w/l) : this;
        }

        /**
         * @returns {math.Vec4} A copy of this Vector.
         */
        public clone():Vec4{
            return new Vec4(this.xComp, this.yComp, this.zComp, this.wComp);
        }

        /**
         * Constructs an array containing the components of the vectors in list.
         * @param list The list of Vectors.
         * @returns {number[]} An array containing the components of the vectors in list.
         */
        public static flattenVectorList(list:Vec4[]):number[]{
            if(list != null && list.length > 0){
                var flat:number[] = [];
                for(var i:number = 0; i < list.length; i ++){
                    var temp:Vec4 = list[i];
                    flat.push(temp.x, temp.y, temp.z, temp.w);
                }
                return flat;
            }else{
                throw ("Illegal arguments");}
        }

        /**
         * @param list The list of Vectors.
         * @returns {Float32Array} An array containing the components of the vectors in list.
         */
        public static flattenVectorList32(list:Vec4[]):Float32Array{
            return new Float32Array(Vec4.flattenVectorList(list));
        }

        public toString():string{
            return `(${this.x}, ${this.y}, ${this.z}, ${this.w})`
        }
    }

}
export = math;
