
module geometry{

    /**
     * A class representing a simple webgl mesh object.
     */
    export abstract class Mesh{
        protected triCount:number;
        protected vertexBuffer:WebGLBuffer;
        protected colorBuffer:WebGLBuffer;

        /**
         * @returns {number} The number of tris for this mesh.
         */
        public get count():number{
            return this.triCount;
        }

        /**
         * @returns {number[]} An array containing the components of the vertices for this mesh.
         */
        public abstract vertSeq():number[];

        /**
         * @returns {Float32Array} A float 32 array containing the components of the vertices for this mesh.
         */
        public abstract vertSeq32():Float32Array;

        /**
         * @returns {number[]} An array containing the components of the normals for this mesh.
         */
        public abstract normalSeq():number[];

        /**
         * @returns {Float32Array} An array containing the components of the normals for this mesh.
         */
        public abstract normalSeq32():Float32Array;

        /**
         * @returns {number[]} An array containing the components of the vertex colors for this mesh.
         */
        public abstract colorSeq():number[];

        /**
         * @returns {Float32Array} A float 32 array containing the components of the vertex colors for this mesh.
         */
        public abstract colorSeq32():Float32Array;

        /**
         * @returns {number[]} An array containing the components of the vertex texture cordinates for this mesh.
         */
        public abstract texCordSeq():number[];

        /**
         * @returns {Float32Array} An array containing the components of the vertex texture cordinates for this mesh.
         */
        public abstract texCordSeq32():Float32Array;
    }

}
export = geometry;
