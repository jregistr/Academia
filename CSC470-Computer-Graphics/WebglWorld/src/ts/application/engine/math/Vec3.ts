
module math{

    /**
     * Class representing vectors of 3 components.
     */
    export class Vec3{

        private xComp:number;
        private yComp:number;
        private zComp:number;

        /**
         * Constructor for Vec3.
         * @param x The x component.
         * @param y The y component.
         * @param z The z component.
         */
        constructor(x:number, y:number, z:number){
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
        public static add(A:Vec3, B:Vec3):Vec3{
            return new Vec3(A.x + B.x, A.y + B.y, A.z + B.z);
        }

        /**
         * Subtracts B from A.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {math.Vec3} A Vector that is the difference of A and B.
         */
        public static minus(A:Vec3, B:Vec3):Vec3{
            return new Vec3(A.x - B.x, A.y - B.y, A.z - B.z);
        }

        /**
         * Calculates the dot product of A and B.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {number} A scalar that is the dot product of A and B.
         */
        public static dot(A:Vec3, B:Vec3):number{
            return (A.x * B.x) + (A.y * B.y) + (A.z * B.z);
        }

        /**
         * Calculates the cross product.
         * @param A The first Vector.
         * @param B The second Vector.
         * @returns {math.Vec3} A Vector that is the cross product of A and B.
         */
        public static cross(A:Vec3, B:Vec3):Vec3{
            return new Vec3(A.y * B.z - A.z * B.y, -(A.x * B.z - A.z * B.x), A.x * B.y - A.y * B.x);
        }
        /**
         * Calculates the scalar product.
         * @param A The first vector.
         * @param b The second vector.
         * @returns {math.Vec3} A vector that is a scalar product of A and b.
         */
        public static scale(A:Vec3, b:number):Vec3{
            return new Vec3(A.x * b, A.y * b, A.z * b);
        }

        /**
         * Determines if two Vectors are equal.
         * @param A The first vector.
         * @param B The second vector.
         * @returns {boolean} True if A and B are equal.
         */
        public static equals(A:Vec3, B:Vec3):boolean{
            return A.x == B.x && A.y == B.y && A.z == B.z;
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
         * @returns {number} The length of this Vector.
         */
        public length():number{
            return Math.sqrt(Vec3.dot(this, this));
        }

        /**
         * @returns {math.Vec3} A vector that is the normalized form of this Vector.
         */
        public normalized():Vec3{
            var l:number = this.length();
            return l != 0 ? new Vec3(this.x/l, this.y/l, this.z/l) : this;
        }

        /**
         * @returns {math.Vec3} A copy of this Vector.
         */
        public clone():Vec3{
            return new Vec3(this.xComp, this.yComp, this.zComp);
        }

        /**
         * Constructs an array containing the components of the vectors in list.
         * @param list The list of Vectors.
         * @returns {number[]} An array containing the components of the vectors in list.
         */
        public static flattenVectorList(list:Vec3[]):number[]{
            if(list != null && list.length > 0){
                var flat:number[] = [];
                for(var i:number = 0; i < list.length; i ++){
                    var temp:Vec3 = list[i];
                    flat.push(temp.x, temp.y, temp.z);
                }
                return flat;
            }else{
                throw ("Illegal arguments");}
        }

        /**
         * @param list The list of Vectors.
         * @returns {Float32Array} An array containing the components of the vectors in list.
         */
        public static flattenVectorList32(list:Vec3[]):Float32Array{
            return new Float32Array(Vec3.flattenVectorList(list));
        }

        public toString():string{
            return `(${this.x},${this.y}, ${this.z})`
        }
    }

}
export = math;
