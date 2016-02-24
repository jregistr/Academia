
module util {
    export module loader {

        export class WebglLoader {

            constructor(){throw ("Do not instantiate this class")}

            /**
             * @param canvas The canvas element.
             * @returns {WebGLRenderingContext} The webgl rendering context.
             */
            public static getGlContext(canvas:HTMLCanvasElement):WebGLRenderingContext {
                try {
                    return <WebGLRenderingContext>canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
                } catch (err) {
                    console.log("Failed to grab context");
                    return null;
                }
            }

            /**
             * Creates a WebGLProgram and attaches the compiled shaders at vertID and fragID respectively should they exist.
             * @param vertID The id of the vertex shader on the DOM.
             * @param fragID The id of the fragment shader on the DOM.
             * @param gl The WebGLRenderingContext.
             * @returns {WebGLProgram} Returns a new WebGLProgram.
             */
            public static makeProgram(vertID:string, fragID:string, gl:WebGLRenderingContext) {
                var program:WebGLProgram = gl.createProgram();
                var vertexShader:WebGLShader = WebglLoader.compileShader(vertID, true, gl);
                var fragShader:WebGLShader = WebglLoader.compileShader(fragID, false, gl);

                gl.attachShader(program, vertexShader);
                gl.attachShader(program, fragShader);
                gl.linkProgram(program);
                if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
                    throw ("Error in linking program");
                }
                gl.useProgram(program);
                return program
            }

            /**
             * @param id The id where the shader is located.
             * @param isVert True if this shader is a vertex shader.
             * @param glContext The webgl context.
             * @returns {WebGLShader} The compiled shader found at id.
             */
            private static compileShader(id:string, isVert:boolean, glContext:WebGLRenderingContext):WebGLShader {
                var source:string = WebglLoader.getSource(id);
                if (source !== null) {
                    var shader:WebGLShader = isVert ? glContext.createShader(glContext.VERTEX_SHADER) : glContext.createShader(glContext.FRAGMENT_SHADER);
                    glContext.shaderSource(shader, source);
                    glContext.compileShader(shader);
                    if (!glContext.getShaderParameter(shader, glContext.COMPILE_STATUS)) {
                        throw ("Error occured in shader compilation. IsVert:" + isVert);
                    }
                    return shader;
                } else {
                    throw ("No source code.");
                }
            }

            /**
             * Grabs the source code for a shader with the particular ID.
             * @param id ID where shader is located.
             * @returns {string} The source of the shader located with id.
             */
            private static getSource(id:string):string {
                var markUp:HTMLElement = document.getElementById(id);
                var current:Node = markUp.firstChild;
                var source = "";
                while (current) {
                    if (current.nodeType == 3) {
                        source += current.textContent;
                    }
                    current = current.nextSibling;
                }
                return source;
            }
        }

    }

}
export = util;