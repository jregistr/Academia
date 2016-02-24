///<reference path="../../math/Vec3.ts" />
///<reference path="../../math/Vec4.ts" />
///<reference path="../Mesh.ts" />
///<reference path="../procedural/Cube.ts" />
///<reference path="../../../Constants.ts" />
///<reference path="../../geometry/procedural/Cube.ts" />
///<reference path="../../util/Utils.ts" />
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
define(["require", "exports", '../../math/Vec3', '../Mesh', '../../geometry/procedural/Cube', '../../util/Utils'], function (require, exports, V3Mod, MeshMod, CMod, UMOD) {
    //import from modules
    var Vec3 = V3Mod.Vec3;
    var Mesh = MeshMod.Mesh;
    var Cube = CMod.procedural.Cube;
    var Utils = UMOD.Utils;
    //</editor-fold>
    var geometry;
    (function (geometry) {
        var procedural;
        (function (procedural) {
            var CubeCarpet = (function (_super) {
                __extends(CubeCarpet, _super);
                /**
                 * Constructor for CubeCarpet class.
                 * @param position The position to start the carpet at.
                 * @param initSquareSize The size of the initial square the generation is based from.
                 * @param subDivisions The amount of subdivisions.
                 */
                function CubeCarpet(position, initSquareSize, subDivisions) {
                    _super.call(this);
                    this.verts = [];
                    this.colors = [];
                    this.centers = [];
                    this.normals = [];
                    this.texCords = [];
                    this.triCount = 0;
                    this.makeCarpet(new Cube(position, initSquareSize), subDivisions);
                }
                /**
                 * Generates a carpet based on the parameters provided.
                 * @param initialCube The initial cube.
                 * @param subDivisions The number of sub divisions for the carpet.
                 */
                CubeCarpet.prototype.makeCarpet = function (initialCube, subDivisions) {
                    if (subDivisions > 0) {
                        var openSquares = [];
                        this.pushFilledSub(initialCube);
                        Utils.pushListValuesToList(openSquares, CubeCarpet.getEmptySubSquares(initialCube));
                        subDivisions--;
                        for (var i = 0; i < subDivisions; i++) {
                            var temp = [];
                            for (var j = 0; j < openSquares.length; j++) {
                                var open = openSquares[j];
                                this.pushFilledSub(open);
                                Utils.pushListValuesToList(temp, CubeCarpet.getEmptySubSquares(open));
                            }
                            openSquares = temp;
                        }
                    }
                    else {
                        this.triCount += 36;
                        Utils.pushListValuesToList(this.verts, initialCube.vertSeq());
                        Utils.pushListValuesToList(this.colors, initialCube.colorSeq());
                        Utils.pushListValuesToList(this.normals, initialCube.normalSeq());
                        Utils.pushListValuesToList(this.texCords, initialCube.texCordSeq());
                        for (var i = 0; i < 36; i++) {
                            this.centers.push(initialCube.getPosition().x, initialCube.getPosition().y, initialCube.getPosition().z);
                        }
                    }
                };
                /**
                 * Calculates the array of empty squares.
                 * @param cube The cube to base generation from.
                 * @returns {Cube[]} An array of the empty cubes.
                 */
                CubeCarpet.getEmptySubSquares = function (cube) {
                    var p = cube.getPosition();
                    var x = p.x, y = p.y, z = p.z;
                    var s = cube.getSize() / 3;
                    var xms = x - s, xps = x + s;
                    var yms = y - s, yps = y + s;
                    return [
                        new Cube(new Vec3(xms, yps, z), s),
                        new Cube(new Vec3(x, yps, z), s),
                        new Cube(new Vec3(xps, yps, z), s),
                        new Cube(new Vec3(xps, y, z), s),
                        new Cube(new Vec3(xps, yms, z), s),
                        new Cube(new Vec3(x, yms, z), s),
                        new Cube(new Vec3(xms, yms, z), s),
                        new Cube(new Vec3(xms, y, z), s)
                    ];
                };
                /**
                 * Pushes the components of a sub square based of cube.
                 * @param cube The cube to base the sub generation from.
                 */
                CubeCarpet.prototype.pushFilledSub = function (cube) {
                    var subSize = cube.getSize() / 3.0;
                    var p = cube.getPosition();
                    var sub = new Cube(new Vec3(p.x, p.y, p.z), subSize);
                    this.triCount += 36;
                    for (var i = 0; i < 36; i++) {
                        this.centers.push(p.x, p.y, p.z);
                    }
                    Utils.pushListValuesToList(this.verts, sub.vertSeq());
                    Utils.pushListValuesToList(this.colors, sub.colorSeq());
                    Utils.pushListValuesToList(this.normals, sub.normalSeq());
                    Utils.pushListValuesToList(this.texCords, sub.texCordSeq());
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.vertSeq = function () {
                    var temp = this.verts;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.vertSeq32 = function () {
                    var temp = this.verts;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.normalSeq = function () {
                    var temp = this.normals;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.normalSeq32 = function () {
                    var temp = this.normals;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.colorSeq = function () {
                    var temp = this.colors;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.colorSeq32 = function () {
                    var temp = this.colors;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.centerSeq = function () {
                    var temp = this.centers;
                    return temp != null ? new Float32Array(temp) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.texCordSeq = function () {
                    var temp = this.texCords;
                    return temp != null ? temp.splice(0) : null;
                };
                /**
                 *@inheritDoc
                 */
                CubeCarpet.prototype.texCordSeq32 = function () {
                    var temp = this.texCords;
                    return temp != null ? new Float32Array(temp) : null;
                };
                return CubeCarpet;
            })(Mesh);
            procedural.CubeCarpet = CubeCarpet;
        })(procedural = geometry.procedural || (geometry.procedural = {}));
    })(geometry || (geometry = {}));
    return geometry;
});
