///<reference path="../../math/Vec3.ts" />
///<reference path="../../math/Vec4.ts" />
///<reference path="../Mesh.ts" />
///<reference path="../../../Constants.ts" />
/// <reference path="../../../../_definitions/jquery/jquery.d.ts" />
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
define(["require", "exports", '../../math/Vec3', '../../math/Vec4', '../Mesh', '../../../Constants', 'jquery'], function (require, exports, V3Mod, V4Mod, MeshMod, ConstMod, $) {
    //import from modules
    var Vec3 = V3Mod.Vec3;
    var Vec4 = V4Mod.Vec4;
    var Mesh = MeshMod.Mesh;
    var Consts = ConstMod.Constants;
    //</editor-fold>
    var geometry;
    (function (geometry) {
        var procedural;
        (function (procedural) {
            /**
             * Class representing a generated Cube mesh.
             */
            var Cube = (function (_super) {
                __extends(Cube, _super);
                /**
                 * Constructor for Cube class.
                 * @param position The position of the Cube.
                 * @param size The size of the Cube.
                 */
                function Cube(position, size) {
                    _super.call(this);
                    /*  private defaultCube:Vec3[] =[
                          new Vec3(-0.5,  0.5, -0.5),
                          new Vec3(-0.5, -0.5, -0.5),
                          new Vec3( 0.5, -0.5, -0.5),
                          new Vec3( 0.5,  0.5, -0.5),
          
                          new Vec3(-0.5,  0.5, 0.5),
                          new Vec3(-0.5, -0.5, 0.5),
                          new Vec3( 0.5, -0.5, 0.5),
                          new Vec3( 0.5,  0.5, 0.5)
                      ];*/
                    /* private indicies:number[] = [
                         0, 1, 2, 0, 3, 2, //front
         
         
         
                         4, 5, 6, 5, 7, 6 //back*!/
                     ];*/
                    this.defaultCube = [
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
                    ];
                    this.normals = [
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
                    this.texCords = [
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
                    this.verts = [];
                    this.colors = [];
                    if (position != null && size > 0) {
                        this.position = position;
                        this.size = size;
                        this.triCount = 36;
                        var defaultCube = this.defaultCube;
                        var verts = [];
                        var colors = [];
                        var choiceColors = [
                            new Vec4(Math.random(), Math.random(), Math.random(), 1),
                            new Vec4(Math.random(), Math.random(), Math.random(), 1),
                            new Vec4(Math.random(), Math.random(), Math.random(), 1),
                            new Vec4(Math.random(), Math.random(), Math.random(), 1),
                            new Vec4(Math.random(), Math.random(), Math.random(), 1),
                            new Vec4(Math.random(), Math.random(), Math.random(), 1)
                        ];
                        var count = 0;
                        var cIndex = 0;
                        $.each(defaultCube, function (index, value) {
                            var result = Vec3.add(Vec3.scale(value, size), position);
                            verts.push(result.x, result.y, result.z);
                            var curCol = choiceColors[cIndex];
                            colors.push(curCol.x, curCol.y, curCol.z, curCol.w);
                            if (++count == 6) {
                                cIndex++;
                                count = 0;
                            }
                        });
                        this.verts = verts;
                        this.colors = colors;
                    }
                    else {
                        throw (Consts.ILLEGAL_ARGUMENTS);
                    }
                }
                /**
                 * @returns {number} The cross size of this cube.
                 */
                Cube.prototype.getSize = function () {
                    return this.size;
                };
                /**
                 * @returns {Vec3} The position of this cube.
                 */
                Cube.prototype.getPosition = function () {
                    return this.position;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.vertSeq = function () {
                    var temp = this.verts;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.vertSeq32 = function () {
                    var temp = this.verts;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.normalSeq = function () {
                    var temp = this.normals;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.normalSeq32 = function () {
                    var temp = this.normals;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.colorSeq = function () {
                    var temp = this.colors;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.colorSeq32 = function () {
                    var temp = this.colors;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.texCordSeq = function () {
                    var temp = this.texCords;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                Cube.prototype.texCordSeq32 = function () {
                    var temp = this.texCords;
                    return temp != null ? new Float32Array(temp) : null;
                };
                return Cube;
            })(Mesh);
            procedural.Cube = Cube;
        })(procedural = geometry.procedural || (geometry.procedural = {}));
    })(geometry || (geometry = {}));
    return geometry;
});
