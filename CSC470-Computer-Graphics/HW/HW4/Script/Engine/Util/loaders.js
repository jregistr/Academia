"use strict";

var Loaders = (function () {

    return {
        
    }

})()


Loaders.WebGL = (function () {
    
    function getContext(canvas) {
        if (canvas) {
            try {
                return canvas.getContext("webgl") || canvas.getContext("experimental-webgl")
            } catch (e) {
                alert("Failed to grab WebGL context");
                return null
            }
        } else {
            return null
        }
    }
    
    function getCompiledShader(id, isVert, gl){
        try{
            var source = getSource(id)
            var shader = null
            if(source !== null){
                shader = isVert ? gl.createShader(gl.VERTEX_SHADER) : gl.createShader(gl.FRAGMENT_SHADER)
                gl.shaderSource(shader, source)
                gl.compileShader(shader)
                if(!gl.getShaderParameter(shader, gl.COMPILE_STATUS)){
                    console.log("An error occured in Vertex Shader compilation")
                    shader = null
                }
            }
            return shader
        }catch(err){
            return null;
        }
    }
    
    function makeProgram(vertShaderID, fragShaderID, gl){
        try{
            var prog = gl.createProgram()
            var vS = getCompiledShader(vertShaderID, true, gl)
            var fS = getCompiledShader(fragShaderID, false, gl)
            
            
            
            if(vS && fS){
                gl.attachShader(prog, vS)
                gl.attachShader(prog, fS)
                gl.linkProgram(prog)
                if(!gl.getProgramParameter(prog, gl.LINK_STATUS)){
                    console.log("Could not initialise shaders")
                    prog = null
                }
                gl.useProgram(prog)
            }else{
                prog = null
            }
            return prog
        }catch(err){
            console.log("Failed to make program")
            return null
        }
    }

    function getSource(id) {
        try {
            var markUp, source = "",
                current, shader;
            markUp = document.getElementById(id);
            if (!markUp) {
                alert("Not markup");
                return null;
            }

            current = markUp.firstChild;
            while (current) {
                if (current.nodeType == 3) {
                    source += current.textContent;
                }
                current = current.nextSibling;
            }
            return source;
        } catch (err) {
            console.log(err)
            return null
        }
    }
    
    return {
        loadSource:getSource,
        getShader:getCompiledShader,
        makeProgram:makeProgram,
        getGLContext:getContext
    }

})()


















