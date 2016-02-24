///<reference path="../../math/Vec3.ts" />
///<reference path="../../math/Vec4.ts" />
///<reference path="../Mesh.ts" />
///<reference path="../../../Constants.ts" />
/// <reference path="../../../../_definitions/jquery/jquery.d.ts" />

//<editor-fold desc="Imports">
//Define modules
import V3Mod = require('../../math/Vec3');
import V4Mod = require('../../math/Vec4');
import MeshMod = require('../Mesh');
import ConstMod = require('../../../Constants');

//import from modules
import Vec3 = V3Mod.Vec3;
import Vec4 = V4Mod.Vec4;
import Mesh = MeshMod.Mesh;
import Consts = ConstMod.Constants;
import $ = require('jquery');
//</editor-fold>

module geometry {

    export module procedural {

        /**
         * Class representing a generated Cube mesh.
         */
        export class Cube extends Mesh {
            private defaultCube:Vec3[] =[
                new Vec3(-0.5,  0.5, -0.5),
                new Vec3(-0.5, -0.5, -0.5),
                new Vec3( 0.5, -0.5, -0.5),
                new Vec3( 0.5,  0.5, -0.5),

                new Vec3(-0.5,  0.5, 0.5),
                new Vec3(-0.5, -0.5, 0.5),
                new Vec3( 0.5, -0.5, 0.5),
                new Vec3( 0.5,  0.5, 0.5)
            ];

           /* private indicies:number[] = [
                0, 1, 2, 0, 3, 2, //front



                4, 5, 6, 5, 7, 6 //back*!/
            ];*/



   /*         private defaultCube:Vec3[] = [
                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(-0.5, -0.5, -0.5),
                new Vec3(0.5, -0.5, -0.5),

                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(0.5, 0.5, -0.5),
                new Vec3(0.5, -0.5, -0.5),

                //right
                new Vec3(0.5, 0.5, -0.5),
                new Vec3(0.5, -0.5, -0.5),
                new Vec3(0.5, -0.5, 0.5),

                new Vec3(0.5, 0.5, -0.5),
                new Vec3(0.5, 0.5, 0.5),
                new Vec3(0.5, -0.5, 0.5),

                //back
                new Vec3(-0.5, 0.5, 0.5),
                new Vec3(-0.5, -0.5, 0.5),
                new Vec3(0.5, -0.5, 0.5),

                new Vec3(-0.5, 0.5, 0.5),
                new Vec3(0.5, 0.5, 0.5),
                new Vec3(0.5, -0.5, 0.5),

                //left
                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(-0.5, -0.5, -0.5),
                new Vec3(-0.5, -0.5, 0.5),

                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(-0.5, 0.5, 0.5),
                new Vec3(-0.5, -0.5, 0.5),

                //top
                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(0.5, 0.5, -0.5),
                new Vec3(0.5, 0.5, 0.5),

                new Vec3(-0.5, 0.5, -0.5),
                new Vec3(-0.5, 0.5, 0.5),
                new Vec3(0.5, 0.5, 0.5),

                //bottom
                new Vec3(-0.5, -0.5, -0.5),
                new Vec3(0.5, -0.5, -0.5),
                new Vec3(0.5, -0.5, 0.5),

                new Vec3(-0.5, -0.5, -0.5),
                new Vec3(-0.5, -0.5, 0.5),
                new Vec3(0.5, -0.5, 0.5)
            ];*/


            private normals:number[] = [
                //front
                0.0, 0.0, 1.0,
                0.0, 0.0, 1.0,
                0.0, 0.0, 1.0,

                0.0, 0.0, 1.0,
                0.0, 0.0, 1.0,
                0.0, 0.0, 1.0,

                //right
                1.0, 0.0, 0.0,
                1.0, 0.0, 0.0,
                1.0, 0.0, 0.0,

                1.0, 0.0, 0.0,
                1.0, 0.0, 0.0,
                1.0, 0.0, 0.0,

                //back
                0.0, 0.0, -1.0,
                0.0, 0.0, -1.0,
                0.0, 0.0, -1.0,

                0.0, 0.0, -1.0,
                0.0, 0.0, -1.0,
                0.0, 0.0, -1.0,

                //left
                -1.0, 0.0, 0.0,
                -1.0, 0.0, 0.0,
                -1.0, 0.0, 0.0,

                -1.0, 0.0, 0.0,
                -1.0, 0.0, 0.0,
                -1.0, 0.0, 0.0,

                //top
                0.0, 1.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 1.0, 0.0,

                0.0, 1.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 1.0, 0.0,

                //bottom
                0.0, -1.0, 0.0,
                0.0, -1.0, 0.0,
                0.0, -1.0, 0.0,

                0.0, -1.0, 0.0,
                0.0, -1.0, 0.0,
                0.0, -1.0, 0.0
            ];

            private texCords:number[] = [
                //front
                0.0, 1.0,
                0.0, 0.6,
                0.3, 0.6,

                0.0, 1.0,
                0.3, 1.0,
                0.3, 0.6,

                //right
                0.3, 1.0,
                0.3, 0.6,
                0.6, 0.6,

                0.3, 1.0,
                0.6, 1.0,
                0.6, 0.6,

                //back
                0.6, 1.0,
                0.6, 0.6,
                1.0, 0.6,

                0.6, 1.0,
                1.0, 1.0,
                1.0, 0.6,

                //left
                0.0, 0.6,
                0.0, 0.3,
                0.3, 0.3,

                0.0, 0.6,
                0.3, 0.6,
                0.3, 0.3,

                //top
                0.3, 0.6,
                0.3, 0.3,
                0.6, 0.3,

                0.3, 0.6,
                0.6, 0.6,
                0.6, 0.3,

                //bottom
                0.6, 0.6,
                0.6, 0.3,
                1.0, 0.3,

                0.6, 0.6,
                1.0, 0.6,
                1.0, 0.3
            ];

            private size:number;
            private position:Vec3;

            private verts:number[] = [];
            private colors:number[] = [];

            /**
             * Constructor for Cube class.
             * @param position The position of the Cube.
             * @param size The size of the Cube.
             */
            constructor(position:Vec3, size:number) {
                super();
                if (position != null && size > 0) {
                    this.position = position;
                    this.size = size;
                    this.triCount = 36;

                    var defaultCube:Vec3[] = this.defaultCube;
                    var verts:number[] = [];
                    var colors:number[] = [];

                    var choiceColors:Vec4[] = [
                        new Vec4(Math.random(), Math.random(), Math.random(), 1),
                        new Vec4(Math.random(), Math.random(), Math.random(), 1),
                        new Vec4(Math.random(), Math.random(), Math.random(), 1),
                        new Vec4(Math.random(), Math.random(), Math.random(), 1),
                        new Vec4(Math.random(), Math.random(), Math.random(), 1),
                        new Vec4(Math.random(), Math.random(), Math.random(), 1)
                    ];

                    var count:number = 0;
                    var cIndex = 0;

                    $.each(defaultCube, (index:number, value:Vec3) =>{
                        var result:Vec3 = Vec3.add(Vec3.scale(value, size), position);
                        verts.push(result.x, result.y, result.z);
                        var curCol:Vec4 = choiceColors[cIndex];
                        colors.push(curCol.x, curCol.y, curCol.z, curCol.w);

                        if(++count == 6){
                            cIndex++;
                            count = 0;
                        }
                    });

                    this.verts = verts;
                    this.colors = colors;
                } else {
                    throw (Consts.ILLEGAL_ARGUMENTS)
                }
            }

            /**
             * @returns {number} The cross size of this cube.
             */
            public getSize():number {
                return this.size;
            }

            /**
             * @returns {Vec3} The position of this cube.
             */
            public getPosition():Vec3 {
                return this.position;
            }

            /**
             *@inheritDoc
             */
            public vertSeq():number[] {
                var temp:number[] = this.verts;
                return temp != null ? temp.splice(0) : null;
            }

            /**
             *@inheritDoc
             */
            public vertSeq32():Float32Array {
                var temp:number[] = this.verts;
                return temp != null ? new Float32Array(temp) : null;
            }

            /**
             *@inheritDoc
             */
            public normalSeq():number[] {
                var temp:number[] = this.normals;
                return temp != null ? temp.splice(0) : null;
            }
            
            /**
             *@inheritDoc
             */
            public normalSeq32():Float32Array {
                var temp:number[] = this.normals;
                return temp != null ? new Float32Array(temp) : null;
            }

            /**
             *@inheritDoc
             */
            public colorSeq():number[] {
                var temp:number[] = this.colors;
                return temp != null ? temp.splice(0) : null;
            }

            /**
             *@inheritDoc
             */
            public colorSeq32():Float32Array {
                var temp:number[] = this.colors;
                return temp != null ? new Float32Array(temp) : null;
            }

            /**
             *@inheritDoc
             */
            public texCordSeq():number[] {
                var temp:number[] = this.texCords;
                return temp != null ? temp.splice(0) : null;
            }

            /**
             *@inheritDoc
             */
            public texCordSeq32():Float32Array {
                var temp:number[] = this.texCords;
                return temp != null ? new Float32Array(temp) : null;
            }
        }

    }

}

export  = geometry;