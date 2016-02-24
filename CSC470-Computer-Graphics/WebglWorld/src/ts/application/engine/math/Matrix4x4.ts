/// <reference path="../../../_definitions/jquery/jquery.d.ts" />
///<reference path="./Vec3.ts" />
///<reference path="../../Constants.ts" />

//Define modules
import VectorModule = require('./Vec3');
import ConstantsModule = require('../../Constants')

//Imports from modules defined above.
import $ = require('jquery');
import Vec3 = VectorModule.Vec3;
import Const = ConstantsModule.Constants;

module math{

    /**
     * A class representing 4 by 4 matrix.
     */
    export class Matrix4x4{

        private static get SIZE_MISMATCH():string {return "Size mismatch"}

        private data:number[][];

        /**
         * Constructs a matrix Object out of the row arrays provided.
         * @param row1 The first row.
         * @param row2 The second row.
         * @param row3 The third row.
         * @param row4 The fourth row.
         */
        constructor(row1:number[], row2:number[], row3:number[], row4:number[]){
            if(row1.length == 4 && row2.length == 4 && row3.length == 4 && row4.length == 4){
                this.data = [row1,row2, row3,row4]
            }else{throw (Matrix4x4.SIZE_MISMATCH)}
        }

        /**
         * Constructs a 4x4 identity matrix.
         * @returns {Matrix4x4} A 4x4 identity matrix.
         */
        public static identity():Matrix4x4{
            return new Matrix4x4([1,0,0,0],
                                 [0,1,0,0],
                                 [0,0,1,0],
                                 [0,0,0,1]);
        }

        /**
         * Calculates the product of A and B.
         * @param A The first Matrix4x4.
         * @param B The second Matrix4x4.
         * @returns {Matrix4x4} A matrix that is the product of A and B.
         */
        public static multiply(A:Matrix4x4, B:Matrix4x4):Matrix4x4{
            var temp:number[][] = [[0,0,0,0],
                                   [0,0,0,0],
                                   [0,0,0,0],
                                   [0,0,0,0]];
            for(var i = 0; i < 4; i++){
                for(var j = 0; j < 4; j ++){
                    for(var k = 0; k < 4; k++){
                        temp[i][j] += A.getData()[i][k] * B.getData()[k][j];
                    }
                }
            }
            return new Matrix4x4(temp[0], temp[1], temp[2], temp[3]);
        }

        /**
         * Calculates the determinant of a Matrix4x4
         * @param A The matrix.
         * @returns {number} The determinant of A.
         */
        public static det(A:Matrix4x4):number{
            var m:number[][] = A.getData();
            var a11 = m[0][0], a12 = m[0][1], a13 = m[0][2], a14 = m[0][3],
                a21 = m[1][0], a22 = m[1][1], a23 = m[1][2], a24 = m[1][3],
                a31 = m[2][0], a32 = m[2][1], a33 = m[2][2], a34 = m[2][3],
                a41 = m[3][0], a42 = m[3][1], a43 = m[3][2], a44 = m[3][3];
            return (a11 * a22 * a33 * a44) + (a11 * a23 * a34 * a42) + (a11 * a24 * a32 * a43) +
                (a12 * a21 * a34 * a43) + (a12 * a23 * a31 * a44) + (a12 * a24 * a33 * a41) +
                (a13 * a21 * a32 * a44) + (a13 * a22 * a34 * a41) + (a13 * a24 * a31 * a42) +
                (a14 * a21 * a33 * a42) + (a14 * a22 * a31 * a43) + (a14 * a23 * a32 * a41) -
                (a11 * a22 * a34 * a43) - (a11 * a23 * a32 * a44) - (a11 * a24 * a33 * a42) -
                (a12 * a21 * a33 * a44) - (a12 * a23 * a34 * a41) - (a12 * a24 * a31 * a43) -
                (a13 * a21 * a34 * a42) - (a13 * a22 * a31 * a44) - (a13 * a24 * a32 * a41) -
                (a14 * a21 * a32 * a43) - (a14 * a22 * a33 * a41) - (a14 * a23 * a31 * a42);
        }

        /**
         * Calculates the inverse of a Matrix4x4 if it exists.
         * @param A The matrix.
         * @returns {math.Matrix4x4} The inverse of A.
         */
        public static inverse(A:Matrix4x4):Matrix4x4{
            var a = A.getData();
            var s0 = a[0][0] * a[1][1] - a[1][0] * a[0][1],
                s1 = a[0][0] * a[1][2] - a[1][0] * a[0][2],
                s2 = a[0][0] * a[1][3] - a[1][0] * a[0][3],
                s3 = a[0][1] * a[1][2] - a[1][1] * a[0][2],
                s4 = a[0][1] * a[1][3] - a[1][1] * a[0][3],
                s5 = a[0][2] * a[1][3] - a[1][2] * a[0][3];

            var c5 = a[2][2] * a[3][3] - a[3][2] * a[2][3],
                c4 = a[2][1] * a[3][3] - a[3][1] * a[2][3],
                c3 = a[2][1] * a[3][2] - a[3][1] * a[2][2],
                c2 = a[2][0] * a[3][3] - a[3][0] * a[2][3],
                c1 = a[2][0] * a[3][2] - a[3][0] * a[2][2],
                c0 = a[2][0] * a[3][1] - a[3][0] * a[2][1];

            var det = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
            if(det != 0){
                var b:number[][] = [
                    [0,0,0,0],
                    [0,0,0,0],
                    [0,0,0,0],
                    [0,0,0,0]];
                var invDet = 1.0/det;
                b[0][0] = ( a[1][1] * c5 - a[1][2] * c4 + a[1][3] * c3) * invDet;
                b[0][1] = (-a[0][1] * c5 + a[0][2] * c4 - a[0][3] * c3) * invDet;
                b[0][2] = ( a[3][1] * s5 - a[3][2] * s4 + a[3][3] * s3) * invDet;
                b[0][3] = (-a[2][1] * s5 + a[2][2] * s4 - a[2][3] * s3) * invDet;

                b[1][0] = (-a[1][0] * c5 + a[1][2] * c2 - a[1][3] * c1) * invDet;
                b[1][1] = ( a[0][0] * c5 - a[0][2] * c2 + a[0][3] * c1) * invDet;
                b[1][2] = (-a[3][0] * s5 + a[3][2] * s2 - a[3][3] * s1) * invDet;
                b[1][3] = ( a[2][0] * s5 - a[2][2] * s2 + a[2][3] * s1) * invDet;

                b[2][0] = ( a[1][0] * c4 - a[1][1] * c2 + a[1][3] * c0) * invDet;
                b[2][1] = (-a[0][0] * c4 + a[0][1] * c2 - a[0][3] * c0) * invDet;
                b[2][2] = ( a[3][0] * s4 - a[3][1] * s2 + a[3][3] * s0) * invDet;
                b[2][3] = (-a[2][0] * s4 + a[2][1] * s2 - a[2][3] * s0) * invDet;

                b[3][0] = (-a[1][0] * c3 + a[1][1] * c1 - a[1][2] * c0) * invDet;
                b[3][1] = ( a[0][0] * c3 - a[0][1] * c1 + a[0][2] * c0) * invDet;
                b[3][2] = (-a[3][0] * s3 + a[3][1] * s1 - a[3][2] * s0) * invDet;
                b[3][3] = ( a[2][0] * s3 - a[2][1] * s1 + a[2][2] * s0) * invDet;
                return new Matrix4x4(b[0], b[1], b[2], b[3]);
            }else{throw (Const.INVALID_MATRIX_FOR_OPERATION);}
        }

        /**
         * Calculates the transpose of a Matrix.
         * @param A The matrix.
         * @returns {math.Matrix4x4} The Matrix that is the transpose of A.
         */
        public static transpose(A:Matrix4x4):Matrix4x4{
            var m = A.getData();
            var b:number[][] = [
                [0,0,0,0],
                [0,0,0,0],
                [0,0,0,0],
                [0,0,0,0]];
            for(var i = 0; i < 4; i ++)
                for(var j = 0; j < 4; j ++)
                    b[j][i] = m[i][j];
            return new Matrix4x4(b[0], b[1], b[2], b[3]);
        }

        /**
         * @param x The x position.
         * @param y The y position.
         * @param z The z position.
         * @returns {math.Matrix4x4} A translation matrix constructed with the vector components provided.
         */
        public static translationMatrix(x:number, y:number, z:number):Matrix4x4{
            return new Matrix4x4([1, 0, 0, x],
                                 [0, 1, 0, y],
                                 [0, 0, 1, z],
                                 [0, 0, 0, 1])
        }

        /**
         * @param theta Angle to rotate by.
         * @returns {math.Matrix4x4} A rotation about the Z axis matrix calculated for theta.
         */
        public static rotationMatrixZ(theta:number):Matrix4x4{
            var c = Math.cos(theta);
            var s = Math.sin(theta);
            return new Matrix4x4(
                [c, -s, 0, 0],
                [s,  c, 0, 0],
                [0,  0, 1, 0],
                [0,  0, 0, 1])
        }

        /**
         * @param theta Angle to rotate by.
         * @returns {math.Matrix4x4} A rotation about the Y axis matrix calculated for theta.
         */
        public static rotationMatrixY(theta:number):Matrix4x4{
            var c = Math.cos(theta);
            var s = Math.sin(theta);
            return new Matrix4x4(
                [ c, 0, s, 0],
                [ 0, 1, 0, 0],
                [-s, 0, c, 0],
                [ 0, 0, 0, 1])
        }

        /**
         * @param theta Angle to rotate by.
         * @returns {math.Matrix4x4} A rotation about the X axis matrix calculated for theta.
         */
        public static rotationMatrixX(theta:number):Matrix4x4{
            var c = Math.cos(theta);
            var s = Math.sin(theta);
            return new Matrix4x4(
                [1, 0,  0, 0],
                [0, c, -s, 0],
                [0, s,  c, 0],
                [0, 0,  0, 1]
            )
        }

        /**
         * Calculates a projection matrix with the parameters provided.
         * @param FOV The field of view angle in Radians.
         * @param aspect The aspect ratio.
         * @param near The near clipping distance.
         * @param far The far clipping distance.
         * @returns {math.Matrix4x4} A matrix that creates perspective projection.
         */
        public static perspectiveProjectionMatrix(FOV:number, aspect:number, near:number, far:number):Matrix4x4{
            if(FOV > 0 && aspect > 0 && near != far){
                var f = Math.tan(Math.PI * 0.5 - 0.5 * FOV);
                var rI = 1.0/(near - far);
                return new Matrix4x4(
                    [f/aspect, 0   , 0                 , 0                   ],
                    [0,        f   , 0                 , 0                   ],
                    [0,        0   , (near + far) * rI , near + far * rI * 2 ],
                    [0,        0   , -1                , 0                   ]
                )
            }else{throw (Const.ILLEGAL_ARGUMENTS);}
        }

        /**
         * @param cameraPosition The position of the camera.
         * @param target The position the camera looks at.
         * @param up The direction where up is.
         * @returns {math.Matrix4x4} Returns a look at matrix.
         */
        public static lookAt(cameraPosition:Vec3, target:Vec3, up:Vec3):Matrix4x4{
            var c:Vec3 = cameraPosition;
            var zAxis = Vec3.minus(cameraPosition, target).normalized();
            var xAxis = Vec3.cross(up, zAxis);
            var yAxis = Vec3.cross(zAxis, xAxis);

            return new Matrix4x4(
                [xAxis.x, yAxis.x, zAxis.x, c.x],
                [xAxis.y, yAxis.y, zAxis.y, c.y],
                [xAxis.z, yAxis.z, zAxis.z, c.z],
                [0      , 0      , 0      , 1  ]
            )
        }

        /**
         * @returns {Array} The underlying 2D array for this Matrix4x4.
         */
        public getData():number[][]{
            var temp = [];
            var data = this.data;
            temp.push(data[0].slice(), data[1].slice(), data[2].slice(), data[3].slice());
            return temp;
        }

        /**
         * @returns {Float32Array} An array containing the entries of this Matrix in column format for WebGL.
         */
        public flatten():Float32Array{
            var transed:Matrix4x4 = Matrix4x4.transpose(this);
            var tData = transed.getData();
            var temp:number[] = [];
            $.each(tData, (uI:number, uA:number[]) =>{
                 $.each(uA, (iI:number, val:number)=>{
                     temp.push(val);
                 });
            });
            return new Float32Array(temp);
        }

        public toString():string{
            var d = this.data;
            return "" +
                "|" + d[0][0] + " " + d[0][1] + " " + d[0][2] + " " + d[0][3] + "|\n" +
                "|" + d[1][0] + " " + d[1][1] + " " + d[1][2] + " " + d[1][3] + "|\n" +
                "|" + d[2][0] + " " + d[2][1] + " " + d[2][2] + " " + d[2][3] + "|\n" +
                "|" + d[3][0] + " " + d[3][1] + " " + d[3][2] + " " + d[3][3] + "|";
        }

    }

}
export = math;
