var engine = (function (){

    var canvas, webGL, vertexShader, fragmentShader, vertexBuffer, colorBuffer, program, verticies = [], colors = [];
    var proceed = true;
	var subs = 0;
	
	var openGrids = [];
	var closedGrids = [];
	
	var processButton, option, preOp, canvasRow;
	
	var initialSquare = Square(Vector2(-1, 1), Vector2(-1,-1), Vector2(1,-1), Vector2(1,1));
   // var initialSquare = Square(Vector2(-0.5, 0.5), Vector2(-0.5, -0.5), Vector2(0.5, -0.5), Vector2(0.5, 0.5))
	
    function init(){
        console.log(Vector2(2,5).copy().toString());
        cacheDom();
        bindEvents();
        initWebGLContext();
        initShaders();
        initUniformsAndAttribs();
		//drawScene();
    }
	
    /**Pre Init  **/
    function cacheDom(){
        canvas = document.getElementById("sheet");
		processButton = document.getElementById('process');
		option = document.getElementById("select");
		preOp = document.getElementById("preOp");
		canvasRow = document.getElementById("canR");
    }

    function bindEvents(){
		processButton.addEventListener('click', function(){
			subs = option.value;
			preOp.style.display = "none";
			canvasRow.style.display = "block";
			drawScene();
		});
    }
	
	
    /** End Pre-Init**/
	
	/*Evens*/
	
	/*End Events*/
    
    
    /** Getters **/
    
    function getShaderSource(id){
        var markUp, source = "", current, shader;
        markUp = document.getElementById(id);
        if(!markUp){
          alert("Not markup");
          return null;
        }

        current = markUp.firstChild;
        while(current){
            if (current.nodeType == 3) {
                source += current.textContent;
            }
            current = current.nextSibling;
        }
        return source;
    }
    
    /**End Getters **/

    
    /**Init Functions**/
    
    function initWebGLContext(){
        if(proceed){
            if(canvas != null){
                try{
                    webGL = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");
                }catch(e){
                    alert("Failed to grab WebGL context");
                    webGL = null;
                    proceed = false;
                }
            }else{
                webGL = null;
                proceed = false;
            }
        }
    }

    function initShaders(){
        if(proceed){
            vertShaderSource = getShaderSource('shader-vs');
            fragShaderSource = getShaderSource('shader-fs');
            if(vertShaderSource && fragShaderSource){
                //Init the vertex shader
                vertexShader = webGL.createShader(webGL.VERTEX_SHADER);
                webGL.shaderSource(vertexShader, vertShaderSource);
                webGL.compileShader(vertexShader);
                if(!webGL.getShaderParameter(vertexShader, webGL.COMPILE_STATUS)){
                    vertexShader = null;
                    proceed = false;
                    alert("An error occured in Vertex Shader compilation");
                }

                //Init the frag shader
                fragmentShader = webGL.createShader(webGL.FRAGMENT_SHADER);
                webGL.shaderSource(fragmentShader, fragShaderSource);
                webGL.compileShader(fragmentShader);
                if(!webGL.getShaderParameter(fragmentShader, webGL.COMPILE_STATUS)){
                    fragmentShader = null;
                    proceed = false;
                    alert("Error occured in Frag Shader Compilation");
                }
            }else{
                proceed = false;
                alert("Not vert or not frag");
            } 
			
			if(proceed){
				initProgram();
			}
			
        }
    }
    
    function initProgram(){
        if(proceed){
            program = webGL.createProgram();
            webGL.attachShader(program, vertexShader);
            webGL.attachShader(program, fragmentShader);
            webGL.linkProgram(program);
            if (!webGL.getProgramParameter(program, webGL.LINK_STATUS)) {
                alert("Could not initialise shaders");
                proceed = false;
            }
            webGL.useProgram(program);
        }
    }
    
    function initUniformsAndAttribs(){
        if(proceed){
			program.aPosition = webGL.getAttribLocation(program, "aPosition");
			program.aColor = webGL.getAttribLocation(program, "aColor");
        }
    }
	
	function initVerticiesAndColors(){
        closedGrids = makeCarpet(subs, initialSquare);
        console.log(closedGrids.length);
		$.each(closedGrids, function(index, value){
            console.log(value.size())
            var c1 = randomColor();
         //   pushList(randomColor(), randomColor(), randomColor(), randomColor(), randomColor(), randomColor(), colors);
            pushList(c1, c1, c1, c1, c1,c1,colors);
			pushList(flattenVectorList(value.toTriangleCords(), 2), verticies);
		});
	}
	
    function initBuffers(){
        if(proceed){
			console.log("Init Buffers");
			initVerticiesAndColors();
			//makeSirpenski(initialTriangle[0], initialTriangle[1], initialTriangle[2], subs);
			var verts32 = new Float32Array(verticies);
			var colors32 = new Float32Array(colors);
			
			vertexBuffer = webGL.createBuffer();
			webGL.bindBuffer(webGL.ARRAY_BUFFER, vertexBuffer);
			webGL.bufferData(webGL.ARRAY_BUFFER, verts32, webGL.STATIC_DRAW);
			webGL.bindBuffer(webGL.ARRAY_BUFFER, null);
			
			colorBuffer = webGL.createBuffer();
			webGL.bindBuffer(webGL.ARRAY_BUFFER, colorBuffer);
			webGL.bufferData(webGL.ARRAY_BUFFER, colors32, webGL.STATIC_DRAW);
			webGL.bindBuffer(webGL.ARRAY_BUFFER, null);
        }
    }
	/**End Init Functions **/
   
	/** Render cycle**/
    function preDraw(){
        if(proceed){
			webGL.enableVertexAttribArray(program.aPosition);
			webGL.bindBuffer(webGL.ARRAY_BUFFER, vertexBuffer);
			webGL.vertexAttribPointer(program.aPosition, 2, webGL.FLOAT, false, 0, 0);
			
			webGL.enableVertexAttribArray(program.aColor);
			webGL.bindBuffer(webGL.ARRAY_BUFFER, colorBuffer);
			webGL.vertexAttribPointer(program.aColor, 4, webGL.FLOAT, false, 0, 0);
			
			webGL.clearColor(1, 1, 1, 1);
            webGL.enable(webGL.DEPTH_TEST);
            webGL.clear(webGL.COLOR_BUFFER_BIT | webGL.DEPTH_BUFFER_BIT);
            webGL.viewport(0,0, canvas.width, canvas.height);
        }
    }
    
    function render(){
		webGL.drawArrays(webGL.TRIANGLES, 0, verticies.length/2);
    }
    
    function drawScene(){
        if(proceed){
			initBuffers();
            preDraw();
            render();
        }
    }
	/*End render cycle*/

	
    return {
        execute:init
    }

});

$( document ).ready(function() {
    engine().execute();
});
