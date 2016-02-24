///<reference path="../engine/math/Matrix4x4.ts"/>
///<reference path="../engine/math/MathFs.ts" />
///<reference path="../engine/util/WebglLoader.ts"/>
/// <reference path="../../_definitions/jquery/jquery.d.ts" />
///<reference path="../engine/geometry/procedural/Cube.ts" />
///<reference path="../engine/math/Vec2.ts"/>
///<reference path="../engine/math/Vec3.ts"/>
///<reference path="../engine/math/Vec4.ts"/>
///<reference path="../engine/util/Utils.ts" />
///<reference path="../engine/geometry/procedural/CubeCarpet.ts" />
define(["require", "exports", '../engine/math/Matrix4x4', '../engine/math/MathFs', '../engine/util/WebglLoader', '../engine/math/Vec3', '../engine/math/Vec4', '../engine/geometry/procedural/CubeCarpet'], function (require, exports, matModule, mModule, glLoaderModule, V3, V4, CMod) {
    //Imports from modules defined above.
    var Matrix = matModule.Matrix4x4;
    var Mathf = mModule.MathFs;
    var glLoader = glLoaderModule.loader.WebglLoader;
    var Carpet = CMod.procedural.CubeCarpet;
    var Vec3 = V3.Vec3;
    var Vec4 = V4.Vec4;
    //</editor-fold>
    var application;
    (function (application) {
        /**
         * Main entry point into program. This class is a singleton.
         */
        var Application = (function () {
            function Application() {
                this.executed = false;
                this.subDivisions = 0;
                this.reDefine = false;
                this.theta = 0;
                this.right = true;
                this.then = 0;
                this.deltatime = 0;
                this.angle = 0;
                if (Application.instance) {
                    throw ("Do not instantiate singleton");
                }
            }
            /**
             * @returns {Application} The current instance of this singleton.
             */
            Application.getInstance = function () {
                Application.instance = Application.instance || new Application();
                return Application.instance;
            };
            Application.prototype.main = function () {
                var _this = this;
                if (!this.executed) {
                    this.executed = true;
                    this.canvas = document.getElementById("canvas");
                    this.gl = glLoader.getGlContext(this.canvas);
                    this.program = glLoader.makeProgram("shader-vs", "shader-fs", this.gl);
                    this.bindEvents();
                    this.createObjects();
                    this.getShaderLocations();
                    this.vertBuffer = this.gl.createBuffer();
                    this.normalBuffer = this.gl.createBuffer();
                    this.colorBuffer = this.gl.createBuffer();
                    this.centerBuffer = this.gl.createBuffer();
                    this.texCordBuffer = this.gl.createBuffer();
                    this.gl.viewport(0, 0, this.canvas.width, this.canvas.height);
                    this.passAttributes();
                    var tex = new Image();
                    tex.onload = function () {
                        // document.appendChild(tex);
                        var gl = _this.gl;
                        var cubeTex = gl.createTexture();
                        gl.bindTexture(gl.TEXTURE_2D, cubeTex);
                        gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, tex);
                        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
                        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
                        gl.generateMipmap(gl.TEXTURE_2D);
                        gl.activeTexture(gl.TEXTURE0);
                        gl.uniform1i(_this.uSamplerLoc, 0);
                        //  gl.bindTexture(gl.TEXTURE_2D, null);
                        /*gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image);
                        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
                        gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
                        gl.generateMipmap(gl.TEXTURE_2D);
                        gl.bindTexture(gl.TEXTURE_2D, null);*/
                        requestAnimationFrame(function (time) { return _this.render(time); });
                    };
                    tex.src = "../images/textures/tmblr.jpg";
                }
                else {
                    throw ("Already executed program");
                }
            };
            Application.prototype.bindEvents = function () {
                var _this = this;
                var fVs = document.getElementById("fovSlider");
                var xS = document.getElementById("xSlider");
                var yS = document.getElementById("ySlider");
                var zS = document.getElementById("zSlider");
                var xRs = document.getElementById("xRotate");
                var yRs = document.getElementById("yRotate");
                var zRs = document.getElementById("zRotate");
                var sSlider = document.getElementById("subDivs");
                var rotSpSlider = document.getElementById("rotSpeed");
                var apBtn = document.getElementById("appBtn");
                var dirBtn = document.getElementById("togDir");
                this.fov = Mathf.degToRad(parseFloat(fVs.value));
                this.x = parseFloat(xS.value);
                this.y = parseFloat(yS.value);
                this.z = parseFloat(zS.value);
                this.subDivisions = parseFloat(sSlider.value);
                this.rotationSpeed = parseFloat(rotSpSlider.value);
                this.xRot = Mathf.degToRad(parseFloat(xRs.value));
                this.yRot = Mathf.degToRad(parseFloat(yRs.value));
                this.zRot = Mathf.degToRad(parseFloat(zRs.value));
                fVs.addEventListener('change', function () { return _this.fov = Mathf.degToRad(parseFloat(fVs.value)); });
                xS.addEventListener('change', function () { return _this.x = parseFloat(xS.value); });
                yS.addEventListener('change', function () { return _this.y = parseFloat(yS.value); });
                zS.addEventListener('change', function () { _this.z = parseFloat(zS.value); console.log(_this.z); });
                xRs.addEventListener('change', function () {
                    _this.xRot = Mathf.degToRad(parseFloat(xRs.value));
                });
                yRs.addEventListener('change', function () {
                    _this.yRot = Mathf.degToRad(parseFloat(yRs.value));
                });
                zRs.addEventListener('change', function () {
                    _this.zRot = Mathf.degToRad(parseFloat(zRs.value));
                });
                sSlider.addEventListener('change', function () { return _this.subDivisions = parseFloat(sSlider.value); });
                rotSpSlider.addEventListener('change', function () { return _this.rotationSpeed = parseFloat(rotSpSlider.value); });
                apBtn.addEventListener('click', function () { return _this.reDefine = true; });
                dirBtn.addEventListener('click', function () { return _this.right = !_this.right; });
            };
            Application.prototype.createObjects = function () {
                this.carpet = new Carpet(new Vec3(0, 0, 0), 2, this.subDivisions);
            };
            Application.prototype.getShaderLocations = function () {
                //Attrib locations
                this.aPositionLoc = this.gl.getAttribLocation(this.program, "aPosition");
                this.aNormalLoc = this.gl.getAttribLocation(this.program, "aNormal");
                this.aColorLoc = this.gl.getAttribLocation(this.program, "aColor");
                this.aCenterLoc = this.gl.getAttribLocation(this.program, "aCenter");
                this.aTexCordLoc = this.gl.getAttribLocation(this.program, "aTexCord");
                //Uniform locations
                this.uThetaLoc = this.gl.getUniformLocation(this.program, "uTheta");
                this.uModelViewMatrixLoc = this.gl.getUniformLocation(this.program, "uModelViewMatrix");
                this.uProjectionMatrixLoc = this.gl.getUniformLocation(this.program, "uProjectionMatrix");
                this.uNormalMatrixLoc = this.gl.getUniformLocation(this.program, "uNormalMatrix");
                this.uShininessLoc = this.gl.getUniformLocation(this.program, "uShininess");
                this.uLightDirLoc = this.gl.getUniformLocation(this.program, "uLightDir");
                this.uLightDiffuseLoc = this.gl.getUniformLocation(this.program, "uLightDiffuse");
                this.uLightAmbientLoc = this.gl.getUniformLocation(this.program, "uLightAmbient");
                this.uLightSpecLoc = this.gl.getUniformLocation(this.program, "uLightSpecular");
                this.uMaterialAmbientLoc = this.gl.getUniformLocation(this.program, "uMaterialAmbient");
                this.uMaterialSpecLoc = this.gl.getUniformLocation(this.program, "uMaterialSpecular");
                this.uSamplerLoc = this.gl.getUniformLocation(this.program, "uSampler");
            };
            Application.prototype.passAttributes = function () {
                var gl = this.gl;
                gl.enableVertexAttribArray(this.aPositionLoc);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.vertBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.vertSeq32(), gl.STATIC_DRAW);
                gl.vertexAttribPointer(this.aPositionLoc, 3, gl.FLOAT, false, 0, 0);
                gl.enableVertexAttribArray(this.aNormalLoc);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.normalBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.normalSeq32(), gl.STATIC_DRAW);
                gl.vertexAttribPointer(this.aNormalLoc, 3, gl.FLOAT, false, 0, 0);
                gl.enableVertexAttribArray(this.aColorLoc);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.colorBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.colorSeq32(), gl.STATIC_DRAW);
                gl.vertexAttribPointer(this.aColorLoc, 4, gl.FLOAT, false, 0, 0);
                gl.enableVertexAttribArray(this.aCenterLoc);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.centerBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.centerSeq(), gl.STATIC_DRAW);
                gl.vertexAttribPointer(this.aCenterLoc, 3, gl.FLOAT, false, 0, 0);
                gl.enableVertexAttribArray(this.aTexCordLoc);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.texCordBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.texCordSeq32(), gl.STATIC_DRAW);
                gl.vertexAttribPointer(this.aTexCordLoc, 2, gl.FLOAT, false, 0, 0);
            };
            Application.prototype.updateAttributes = function () {
                var gl = this.gl;
                this.createObjects();
                gl.bindBuffer(gl.ARRAY_BUFFER, this.vertBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.vertSeq32(), gl.STATIC_DRAW);
                gl.bindBuffer(gl.ARRAY_BUFFER, null);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.normalBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.normalSeq32(), gl.STATIC_DRAW);
                gl.bindBuffer(gl.ARRAY_BUFFER, null);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.colorBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.colorSeq32(), gl.STATIC_DRAW);
                gl.bindBuffer(gl.ARRAY_BUFFER, null);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.centerBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.centerSeq(), gl.STATIC_DRAW);
                gl.bindBuffer(gl.ARRAY_BUFFER, null);
                gl.bindBuffer(gl.ARRAY_BUFFER, this.texCordBuffer);
                gl.bufferData(gl.ARRAY_BUFFER, this.carpet.texCordSeq32(), gl.STATIC_DRAW);
                gl.bindBuffer(gl.ARRAY_BUFFER, null);
            };
            Application.prototype.render = function (now) {
                var _this = this;
                now *= 0.0001;
                this.deltatime = now - this.then;
                this.then = now;
                if (this.reDefine) {
                    this.updateAttributes();
                    this.reDefine = false;
                }
                var gl = this.gl;
                gl.clearColor(1, 1, 1, 1);
                gl.enable(gl.DEPTH_TEST);
                gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
                //build uniform matricies here
                var aspect = this.canvas.clientWidth / this.canvas.clientHeight;
                var proj = Matrix.perspectiveProjectionMatrix(this.fov, aspect, 1, 2000);
                var translationMatrix = Matrix.translationMatrix(this.x, this.y, this.z);
                var rotX = Matrix.rotationMatrixX(this.xRot);
                var rotY = Matrix.rotationMatrixY(this.yRot);
                var rotZ = Matrix.rotationMatrixZ(this.zRot);
                var view = Matrix.multiply(translationMatrix, rotX);
                view = Matrix.multiply(view, rotY);
                view = Matrix.multiply(view, rotZ);
                var camera = Matrix.inverse(view);
                //End build uniform matricies
                //Build other uniforms
                var shininess = 230.0;
                var lightDir = new Vec3(0.0, -1.0, 1.0);
                //  var lightAmbient:Vec4 = new Vec4(0.03, 0.03, 0.03, 1.0);
                var lightAmbient = new Vec4(0.3, 0.3, 0.3, 1.0);
                var lightDiffuse = new Vec4(1.0, 1.0, 1.0, 1.0);
                var lightSpec = new Vec4(1.0, 1.0, 1.0, 1.0);
                // var materialAmbient:Vec4 = new Vec4(0.03, 0.03, 0.03, 1.0);
                var materialAmbient = new Vec4(0.4, 0.4, 0.4, 1.0);
                var materialSpecular = new Vec4(0.5, 0.8, 0.1, 1.0);
                //   var eyeVector = new Vec3(this.x, this.y, this.z);
                //normal matrix in this case is equal to my camera matrix
                //the normal matrix is the inverse of the model view matrix then transposed which is camera matrix in this case
                gl.uniformMatrix4fv(this.uModelViewMatrixLoc, false, camera.flatten()); //pass model view
                gl.uniformMatrix4fv(this.uProjectionMatrixLoc, false, proj.flatten());
                gl.uniformMatrix4fv(this.uNormalMatrixLoc, false, camera.flatten());
                gl.uniform1f(this.uShininessLoc, shininess);
                gl.uniform3fv(this.uLightDirLoc, new Float32Array([lightDir.x, lightDir.y, lightDir.z]));
                gl.uniform4fv(this.uLightAmbientLoc, new Float32Array([lightAmbient.x, lightAmbient.y, lightAmbient.z, lightAmbient.w]));
                gl.uniform4fv(this.uLightDiffuseLoc, new Float32Array([lightDiffuse.x, lightDiffuse.y, lightDiffuse.z, lightDiffuse.w]));
                gl.uniform4fv(this.uLightSpecLoc, new Float32Array([lightSpec.x, lightSpec.y, lightSpec.z, lightSpec.w]));
                gl.uniform4fv(this.uMaterialAmbientLoc, new Float32Array([materialAmbient.x, materialAmbient.y, materialAmbient.z, materialAmbient.w]));
                gl.uniform4fv(this.uMaterialSpecLoc, new Float32Array([materialSpecular.x, materialSpecular.y, materialSpecular.z, materialSpecular.w]));
                var rSpeed = this.rotationSpeed;
                if (rSpeed > 0) {
                    gl.uniform1f(this.uThetaLoc, this.theta);
                    this.angle += (this.right) ? (rSpeed * this.deltatime) : (-rSpeed * this.deltatime);
                    this.theta = this.angle * (Math.PI / 180);
                }
                gl.drawArrays(gl.TRIANGLES, 0, this.carpet.count);
                requestAnimationFrame(function (time) { return _this.render(time); });
            };
            Application.instance = null;
            return Application;
        })();
        application.Application = Application;
    })(application || (application = {}));
    return application;
});
