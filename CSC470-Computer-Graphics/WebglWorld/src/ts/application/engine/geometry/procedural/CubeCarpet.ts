///<reference path="../../math/Vec3.ts" />
///<reference path="../../math/Vec4.ts" />
///<reference path="../Mesh.ts" />
///<reference path="../procedural/Cube.ts" />
///<reference path="../../../Constants.ts" />
///<reference path="../../geometry/procedural/Cube.ts" />
///<reference path="../../util/Utils.ts" />

//<editor-fold desc="Imports">
//Define modules
import V3Mod = require('../../math/Vec3');
import V4Mod = require('../../math/Vec4');
import MeshMod = require('../Mesh');
import ConstMod = require('../../../Constants');
import CMod = require('../../geometry/procedural/Cube');
import UMOD = require('../../util/Utils')

//import from modules
import Vec3 = V3Mod.Vec3;
import Vec4 = V4Mod.Vec4;
import Mesh = MeshMod.Mesh;
import Consts = ConstMod.Constants;
import Cube = CMod.procedural.Cube;
import Utils = UMOD.Utils;
//</editor-fold>

module geometry{

    export module procedural{

        export class CubeCarpet extends Mesh{

            private verts:number[] = [];
            private colors:number[] = [];
            private centers:number[] = [];
            private normals:number[] = [];
            private texCords:number[]= [];

            /**
             * Constructor for CubeCarpet class.
             * @param position The position to start the carpet at.
             * @param initSquareSize The size of the initial square the generation is based from.
             * @param subDivisions The amount of subdivisions.
             */
            constructor(position:Vec3, initSquareSize:number, subDivisions:number){
                super();
                this.triCount = 0;
                this.makeCarpet(new Cube(position, initSquareSize), subDivisions);
            }

            /**
             * Generates a carpet based on the parameters provided.
             * @param initialCube The initial cube.
             * @param subDivisions The number of sub divisions for the carpet.
             */
            private makeCarpet(initialCube:Cube, subDivisions:number):void{
                if(subDivisions > 0){
                    var openSquares:Cube[] = [];
                    this.pushFilledSub(initialCube);
                    Utils.pushListValuesToList(openSquares, CubeCarpet.getEmptySubSquares(initialCube));
                    subDivisions--;
                    for(var i = 0; i < subDivisions; i ++){
                        var temp:Cube[] = [];
                        for(var j = 0; j < openSquares.length; j++){
                            var open:Cube = openSquares[j];
                            this.pushFilledSub(open);
                            Utils.pushListValuesToList(temp, CubeCarpet.getEmptySubSquares(open));
                        }
                        openSquares = temp;
                    }
                }else {
                    this.triCount += 36;
                    Utils.pushListValuesToList(this.verts, initialCube.vertSeq());
                    Utils.pushListValuesToList(this.colors, initialCube.colorSeq());
                    Utils.pushListValuesToList(this.normals, initialCube.normalSeq());
                    Utils.pushListValuesToList(this.texCords, initialCube.texCordSeq());
                    for(var i = 0; i < 36; i ++){
                        this.centers.push(initialCube.getPosition().x, initialCube.getPosition().y, initialCube.getPosition().z);
                    }
                }
            }

            /**
             * Calculates the array of empty squares.
             * @param cube The cube to base generation from.
             * @returns {Cube[]} An array of the empty cubes.
             */
            private static getEmptySubSquares(cube:Cube):Cube[]{
                var p = cube.getPosition();
                var x:number = p.x, y:number = p.y, z:number = p.z;
                var s = cube.getSize()/3;

                var xms:number = x - s, xps:number = x + s;
                var yms:number = y - s, yps:number = y + s;
                return[
                    new Cube(new Vec3(xms, yps, z), s),
                    new Cube(new Vec3(x, yps, z), s),
                    new Cube(new Vec3(xps, yps, z), s),

                    new Cube(new Vec3(xps, y, z), s),
                    new Cube(new Vec3(xps, yms, z), s),
                    new Cube(new Vec3(x, yms, z), s),

                    new Cube(new Vec3(xms, yms, z), s),
                    new Cube(new Vec3(xms, y, z), s)
                ];
            }

            /**
             * Pushes the components of a sub square based of cube.
             * @param cube The cube to base the sub generation from.
             */
            private pushFilledSub(cube:Cube):void{
                var subSize:number = cube.getSize()/3.0;
                var p = cube.getPosition();
                var sub = new Cube(new Vec3(p.x, p.y, p.z), subSize);
                this.triCount += 36;
                for(var i = 0; i < 36; i ++){
                    this.centers.push(p.x, p.y, p.z);
                }
                Utils.pushListValuesToList(this.verts, sub.vertSeq());
                Utils.pushListValuesToList(this.colors, sub.colorSeq());
                Utils.pushListValuesToList(this.normals, sub.normalSeq());
                Utils.pushListValuesToList(this.texCords, sub.texCordSeq());
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
            public centerSeq():Float32Array{
                var temp:number[] = this.centers;
                return temp != null ? new Float32Array(temp): null;
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

export = geometry;
