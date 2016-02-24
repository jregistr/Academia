define(["require", "exports"], function (require, exports) {
    var util;
    (function (util) {
        var loader;
        (function (loader) {
            var WebglLoader = (function () {
                function WebglLoader() {
                    throw ("Do not instantiate this class");
                }
                /**
                 * @param canvas The canvas element.
                 * @returns {WebGLRenderingContext} The webgl rendering context.
                 */
                WebglLoader.getGlContext = function (canvas) {
                    try {
                        return canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
                    }
                    catch (err) {
                        console.log("Failed to grab context");
                        return null;
                    }
                };
                /**
                 * Creates a WebGLProgram and attaches the compiled shaders at vertID and fragID respectively should they exist.
                 * @param vertID The id of the vertex shader on the DOM.
                 * @param fragID The id of the fragment shader on the DOM.
                 * @param gl The WebGLRenderingContext.
                 * @returns {WebGLProgram} Returns a new WebGLProgram.
                 */
                WebglLoader.makeProgram = function (vertID, fragID, gl) {
                    var program = gl.createProgram();
                    var vertexShader = WebglLoader.compileShader(vertID, true, gl);
                    var fragShader = WebglLoader.compileShader(fragID, false, gl);
                    gl.attachShader(program, vertexShader);
                    gl.attachShader(program, fragShader);
                    gl.linkProgram(program);
                    if (!gl.getProgramParameter(program, gl.LINK_STATUS)) {
                        throw ("Error in linking program");
                    }
                    gl.useProgram(program);
                    return program;
                };
                /**
                 * @param id The id where the shader is located.
                 * @param isVert True if this shader is a vertex shader.
                 * @param glContext The webgl context.
                 * @returns {WebGLShader} The compiled shader found at id.
                 */
                WebglLoader.compileShader = function (id, isVert, glContext) {
                    var source = WebglLoader.getSource(id);
                    if (source !== null) {
                        var shader = isVert ? glContext.createShader(glContext.VERTEX_SHADER) : glContext.createShader(glContext.FRAGMENT_SHADER);
                        glContext.shaderSource(shader, source);
                        glContext.compileShader(shader);
                        if (!glContext.getShaderParameter(shader, glContext.COMPILE_STATUS)) {
                            throw ("Error occured in shader compilation. IsVert:" + isVert);
                        }
                        return shader;
                    }
                    else {
                        throw ("No source code.");
                    }
                };
                /**
                 * Grabs the source code for a shader with the particular ID.
                 * @param id ID where shader is located.
                 * @returns {string} The source of the shader located with id.
                 */
                WebglLoader.getSource = function (id) {
                    var markUp = document.getElementById(id);
                    var current = markUp.firstChild;
                    var source = "";
                    while (current) {
                        if (current.nodeType == 3) {
                            source += current.textContent;
                        }
                        current = current.nextSibling;
                    }
                    return source;
                };
                return WebglLoader;
            })();
            loader.WebglLoader = WebglLoader;
        })(loader = util.loader || (util.loader = {}));
    })(util || (util = {}));
    return util;
});
