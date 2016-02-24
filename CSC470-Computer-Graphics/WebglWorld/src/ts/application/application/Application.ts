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


//<editor-fold desc="Imports">
//Define modules
import matModule = require('../engine/math/Matrix4x4');
import mModule =   require('../engine/math/MathFs');
import glLoaderModule = require('../engine/util/WebglLoader');
import CubeMod = require('../engine/geometry/procedural/Cube');

import V2 = require('../engine/math/Vec2');
import V3 = require('../engine/math/Vec3');
import V4 = require('../engine/math/Vec4');
import UMod = require('../engine/util/Utils');
import CMod = require('../engine/geometry/procedural/CubeCarpet');

//Imports from modules defined above.
import Matrix = matModule.Matrix4x4;
import Mathf = mModule.MathFs;
import glLoader = glLoaderModule.loader.WebglLoader;
import $ = require('jquery');
import Cube = CubeMod.procedural.Cube;
import Carpet = CMod.procedural.CubeCarpet;

import Vec2 = V2.Vec2;
import Vec3 = V3.Vec3;
import Vec4 = V4.Vec4;
import Utils = UMod.Utils;
//</editor-fold>

module application{

    /**
     * Main entry point into program. This class is a singleton.
     */
    export class Application{

        private static instance:Application = null;

        private executed:boolean = false;

        private canvas:HTMLCanvasElement;
        private gl:WebGLRenderingContext;
        private program:WebGLProgram;

        private aPositionLoc :number;
        private aNormalLoc:number;
        private aColorLoc:number;
        private aCenterLoc:number;
        private aTexCordLoc:number;

        private uThetaLoc:WebGLUniformLocation;
        private uModelViewMatrixLoc:WebGLUniformLocation;
        private uProjectionMatrixLoc:WebGLUniformLocation;
        private uNormalMatrixLoc:WebGLUniformLocation;

        private uShininessLoc:WebGLUniformLocation;
        private uLightDirLoc:WebGLUniformLocation;
        private uLightDiffuseLoc:WebGLUniformLocation;
        private uLightAmbientLoc:WebGLUniformLocation;
        private uLightSpecLoc:WebGLUniformLocation;

        private uMaterialAmbientLoc:WebGLUniformLocation;
        private uMaterialSpecLoc:WebGLUniformLocation;

        private uSamplerLoc:WebGLUniformLocation;

        private carpet:Carpet;

        private vertBuffer:WebGLBuffer;
        private normalBuffer:WebGLBuffer;
        private colorBuffer:WebGLBuffer;
        private centerBuffer:WebGLBuffer;
        private texCordBuffer:WebGLBuffer;

        private fov:number;
        private x:number;
        private y:number;
        private z:number;

        private xRot:number;
        private yRot:number;
        private zRot:number;

        private subDivisions:number = 0;
        private reDefine:boolean = false;
        private rotationSpeed:number;
        private theta:number = 0;
        private right:boolean = true;

        private then:number = 0;
        private deltatime:number = 0;
        private angle:number = 0;

        constructor() {
            if(Application.instance){
                throw ("Do not instantiate singleton");
            }
        }

        /**
         * @returns {Application} The current instance of this singleton.
         */
        public static getInstance():Application{
            Application.instance = Application.instance || new Application();
            return Application.instance;
        }

        public main():void{
            if(!this.executed){
                this.executed = true;

                this.canvas = <HTMLCanvasElement>document.getElementById("canvas");
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
                tex.onload = ()=>{
                   // document.appendChild(tex);
                    var gl = this.gl;
                    var cubeTex:WebGLTexture = gl.createTexture();
                    gl.bindTexture(gl.TEXTURE_2D, cubeTex);
                    gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, tex);
                    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
                    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
                    gl.generateMipmap(gl.TEXTURE_2D);
                    gl.activeTexture(gl.TEXTURE0);
                    gl.uniform1i(this.uSamplerLoc, 0);
                  //  gl.bindTexture(gl.TEXTURE_2D, null);

                    /*gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image);
                    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
                    gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_NEAREST);
                    gl.generateMipmap(gl.TEXTURE_2D);
                    gl.bindTexture(gl.TEXTURE_2D, null);*/

                    requestAnimationFrame((time:number) => this.render(time));
                };
                tex.src = "../images/textures/tmblr.jpg";

            }else{
                throw ("Already executed program");}
        }

        private bindEvents():void{
            var fVs:HTMLInputElement = <HTMLInputElement>document.getElementById("fovSlider");
            var xS:HTMLInputElement = <HTMLInputElement>document.getElementById("xSlider");
            var yS:HTMLInputElement = <HTMLInputElement>document.getElementById("ySlider");
            var zS:HTMLInputElement = <HTMLInputElement>document.getElementById("zSlider");

            var xRs:HTMLInputElement = <HTMLInputElement>document.getElementById("xRotate");
            var yRs:HTMLInputElement = <HTMLInputElement>document.getElementById("yRotate");
            var zRs:HTMLInputElement = <HTMLInputElement>document.getElementById("zRotate");

            var sSlider:HTMLInputElement = <HTMLInputElement>document.getElementById("subDivs");
            var rotSpSlider:HTMLInputElement = <HTMLInputElement>document.getElementById("rotSpeed");
            var apBtn:HTMLButtonElement = <HTMLButtonElement>document.getElementById("appBtn");
            var dirBtn:HTMLButtonElement = <HTMLButtonElement>document.getElementById("togDir");

            this.fov = Mathf.degToRad(parseFloat(fVs.value));
            this.x = parseFloat(xS.value);
            this.y = parseFloat(yS.value);
            this.z = parseFloat(zS.value);
            this.subDivisions = parseFloat(sSlider.value);
            this.rotationSpeed = parseFloat(rotSpSlider.value);

            this.xRot = Mathf.degToRad(parseFloat(xRs.value));
            this.yRot = Mathf.degToRad(parseFloat(yRs.value));
            this.zRot = Mathf.degToRad(parseFloat(zRs.value));

            fVs.addEventListener('change', ()=> this.fov = Mathf.degToRad(parseFloat(fVs.value)) );
            xS.addEventListener('change', ()=> this.x = parseFloat(xS.value));
            yS.addEventListener('change', ()=> this.y = parseFloat(yS.value));
            zS.addEventListener('change', ()=> {this.z = parseFloat(zS.value); console.log(this.z)});

            xRs.addEventListener('change', ()=> {
                this.xRot = Mathf.degToRad(parseFloat(xRs.value));
            });

            yRs.addEventListener('change', ()=> {
                this.yRot = Mathf.degToRad(parseFloat(yRs.value));
            });

            zRs.addEventListener('change', ()=> {
                this.zRot = Mathf.degToRad(parseFloat(zRs.value));
            });

            sSlider.addEventListener('change', ()=>this.subDivisions = parseFloat(sSlider.value));
            rotSpSlider.addEventListener('change', ()=> this.rotationSpeed = parseFloat(rotSpSlider.value));
            apBtn.addEventListener('click', ()=> this.reDefine = true);
            dirBtn.addEventListener('click', ()=> this.right = !this.right);
        }

        private createObjects():void{
            this.carpet = new Carpet(new Vec3(0, 0, 0), 2, this.subDivisions);
        }

        private getShaderLocations():void{
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
        }

        private passAttributes():void{
            var gl:WebGLRenderingContext = this.gl;

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
        }

        private updateAttributes():void{
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
        }

        private render(now:number):void{
            now *= 0.0001;
            this.deltatime = now - this.then;
            this.then = now ;

            if(this.reDefine){
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
            var shininess:number = 230.0;

            var lightDir:Vec3 = new Vec3(0.0, -1.0, 1.0);
          //  var lightAmbient:Vec4 = new Vec4(0.03, 0.03, 0.03, 1.0);
            var lightAmbient:Vec4 = new Vec4(0.3, 0.3, 0.3, 1.0);
            var lightDiffuse:Vec4 = new Vec4(1.0, 1.0, 1.0, 1.0);
            var lightSpec:Vec4 = new Vec4(1.0, 1.0, 1.0, 1.0);

           // var materialAmbient:Vec4 = new Vec4(0.03, 0.03, 0.03, 1.0);
            var materialAmbient:Vec4 = new Vec4(0.4, 0.4, 0.4, 1.0);
            var materialSpecular:Vec4 = new Vec4(0.5, 0.8, 0.1, 1.0);

         //   var eyeVector = new Vec3(this.x, this.y, this.z);

            //normal matrix in this case is equal to my camera matrix
            //the normal matrix is the inverse of the model view matrix then transposed which is camera matrix in this case
            gl.uniformMatrix4fv(this.uModelViewMatrixLoc, false, camera.flatten());//pass model view
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
            if(rSpeed > 0){
                gl.uniform1f(this.uThetaLoc, this.theta);
                this.angle += (this.right) ? (rSpeed * this.deltatime) : (-rSpeed * this.deltatime);
                this.theta = this.angle * (Math.PI/180);
            }

            gl.drawArrays(gl.TRIANGLES, 0, this.carpet.count);

            requestAnimationFrame((time:number) => this.render(time));
        }

    }

}
export = application



























