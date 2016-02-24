"use strict";

$( document ).ready(function() {

    var Application = (function(){
        
        /*webgl utilities*/
        var canvas, gl, program, verticies, colors, centers
        
        /*Buffers*/
        var vertexBuffer, colorBuffer, centerBuffer
        
        /*document elements*/
        var subSlider, rotationSlider,yawSlider,pitchSlider, dirButton1, dirButton2, applyButton
        
        /*webgl program inputs*/
        var subDivision, nexSub, rotationSpeed, direction, updateVertsAndColors
        
        /*etc*/
        var continueExecution = true, then = 0, deltaTime = 0, theta = 0, angle = 0
        
       // var modelView, proj
        
        var radius = 4, yawAngle = 0, pitchAngle = 0, vertPos, horPoz, forPos  
        
        var cameraMatrix, perspective
        
        
        
        function init(){
            console.log(Mats.toArray(Mats.translate(2,2,2)).toString())
            cacheDom()
            bindEvents()
            initGlProg()
            setShaderInputLocations()
            initBuffersAndSceneData()
            additionalPresettings()
            requestAnimationFrame(render)
        }
        
        function makeMatricies(){
            var proj = Mats.perspective(45, 1, 0.3, 10)
            perspective = Mats.toArray(proj)
            var camPos = Vec3(radius*Math.sin(yawAngle)*Math.cos(pitchAngle), 
                              radius*Math.sin(yawAngle)*Math.sin(pitchAngle), radius*Math.cos(yawAngle))
            var at = Vec3(0.0, 0.0, 0.0)
            var up = Vec3(0.0, 1.0, 0.0)
            var modelViewMatrix = Mats.lookAt(camPos , at, up)
            cameraMatrix = Mats.toArray(modelViewMatrix)
        }
        
        function test(){
           var radius = 4, ntheta = 0, phi = 0, fovy = 45, aspect = 1, near = 0.3, far = 3
           
           var camPos = Vec3(radius*Math.sin(ntheta)*Math.cos(phi), radius*Math.sin(ntheta)*Math.sin(phi), radius*Math.cos(ntheta))
           var at = Vec3(0.0, 0.0, 0.0)
           var up = Vec3(0.0, 1.0, 0.0)
           
           var modelViewMatrix = Mats.lookAt(camPos , at, up)
           
           var projMatrix = Mats.perspective(fovy, aspect, near, far)
         
           console.log("Proj:\n" + Mats.toArray(projMatrix).toString())
           console.log("Model View:\n" + Mats.toArray(modelViewMatrix).toString())
           
           modelView = Mats.toArray(modelViewMatrix)
           proj = Mats.toArray(projMatrix)
           
        }
        
        function cacheDom(){
            try{
                canvas = document.getElementById("canvas")
                subSlider = document.getElementById("subSlider")
                rotationSlider = document.getElementById("speedSlider")
                yawSlider = document.getElementById("camYaw")
                pitchSlider = document.getElementById("camPitch")
                dirButton1 = document.getElementById("dirLeft")
                dirButton2 = document.getElementById("dirRight")
                applyButton = document.getElementById("applyBtn")
                subDivision = subSlider.value
                nexSub = subDivision
                updateVertsAndColors = false
                rotationSpeed = rotationSlider.value
                direction = 1
            }catch(err){
                continueExecution = false
            }
        }
        
        function bindEvents(){
            if(continueExecution){
                dirButton1.addEventListener('click', function(){
                    $(dirButton2).removeClass('active')
                    $(dirButton1).addClass('active')
                })

                dirButton2.addEventListener('click', function(){
                    $(dirButton1).removeClass('active')
                    $(dirButton2).addClass('active')
                })

                applyButton.addEventListener('click', function(){
                    nexSub = subSlider.value
                    if(nexSub != subDivision){
                        subDivision = nexSub
                        updateVertsAndColors = true
                    }
                    rotationSpeed = rotationSlider.value
                    direction = $(dirButton1).hasClass('active') ? -1 : 1;
                    yawAngle = Maths.degToRad(yawSlider.value)
                    pitchAngle = Maths.degToRad(pitchSlider.value)
                })
            }
        }
        
        function initGlProg(){
            if(continueExecution){
                gl = Loaders.WebGL.getGLContext(canvas)
                if(gl != null){
                    program = Loaders.WebGL.makeProgram("shader-vs", "shader-fs", gl)
                    if(!program){
                        console.log("No program")
                        continueExecution = false
                    }
                }else{
                    continueExecution = false
                    console.log("Gl is null")
                }
            }else{
                throw("Failed")
            }
        }
        
        function initVertsAndColors(){
            verticies = []
            colors = []
            centers = []
            var squares = carpetMaker.makeCarpet(Square(0, 0, 2), subDivision)
            $.each(squares, function(index, value){
                var color = Util.randomColor()
                Util.pushList(color, color, color, color, color, color, colors)
                Util.pushList(Vecs.flatten(value.flatten()), verticies)
                var center = Vecs.flatten([value.position()]), size = value.size
                Util.pushList(center, center, center, center, center, center, centers)
            })
        }

        function initBuffersAndSceneData(){
            if(continueExecution){
                try{
                    vertexBuffer = gl.createBuffer()
                    colorBuffer = gl.createBuffer()
                    centerBuffer = gl.createBuffer()
                    initVertsAndColors()
                    gl.enableVertexAttribArray(program.aPosition)
                    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(verticies), gl.STATIC_DRAW)
                    gl.vertexAttribPointer(program.aPosition, 2, gl.FLOAT, false, 0, 0)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)
                    
                    gl.enableVertexAttribArray(program.aColor)
                    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW)
                    gl.vertexAttribPointer(program.aColor, 4, gl.FLOAT, false, 0, 0)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)
                    
                    gl.enableVertexAttribArray(program.aCenter)
                    gl.bindBuffer(gl.ARRAY_BUFFER, centerBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(centers), gl.STATIC_DRAW)
                    gl.vertexAttribPointer(program.aCenter, 2, gl.FLOAT, false, 0, 0)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)
               
                }catch(err){
                    console.log(err)
                    continueExecution = false
                }
            }
        }
        
        function setShaderInputLocations(){
            if(continueExecution){
                program.aPosition = gl.getAttribLocation(program, "aPosition")
                program.aColor = gl.getAttribLocation(program, "aColor")
                program.aCenter = gl.getAttribLocation(program, "aCenter")
                program.theta = gl.getUniformLocation(program, "uTheta")
                program.uView = gl.getUniformLocation(program, "uView")
                program.uProj = gl.getUniformLocation(program, "uProj")
            }
        }
        
        function additionalPresettings(){
            if(continueExecution){
                gl.viewport(0,0, canvas.width, canvas.height)
            }
        }
        
        function preRender(now){
            try{
                if(updateVertsAndColors){
                    updateVertsAndColors = false
                    initVertsAndColors()
                    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(verticies), gl.STATIC_DRAW)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)

                    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)
                    
                    gl.bindBuffer(gl.ARRAY_BUFFER, centerBuffer)
                    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(centers), gl.STATIC_DRAW)
                    gl.bindBuffer(gl.ARRAY_BUFFER, null)
                }

                gl.clearColor(1, 1, 1, 1);
                gl.enable(gl.DEPTH_TEST);
                gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

                now *= 0.0001
                deltaTime = now - then
                then = now
            }catch(err){
                console.log(err)
                continueExecution = false
            }
        }
        
        function render(now){
            if(continueExecution){
                preRender(now)
                if(rotationSpeed > 0){
                    gl.uniform1f(program.theta, theta)
                    angle += (direction > 0) ? (rotationSpeed * deltaTime) : (-rotationSpeed * deltaTime)
                    theta = angle * (Math.PI/180)
                }
                makeMatricies()
                gl.uniformMatrix4fv(program.uView, false, new Float32Array(cameraMatrix))
                gl.uniformMatrix4fv(program.uProj, false, new Float32Array(perspective))
                
                gl.drawArrays(gl.TRIANGLES, 0, verticies.length/2)
                requestAnimationFrame(render)
            }
        }

        return{
            execute:init
        }
        
    })
    
    Application().execute()
    
});
