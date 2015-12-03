"use strict";

(function (){
    var basePath = "../Script"
    var engFldr = "Engine"
    var apFldr = "Application"
    var lbFldr = "Libs"
    var mathFldr = "Math"
    var utilFldr = "Util"
    var shapsFldr = "Shapes"
    
    var scripts = [
        makePath(engFldr, lbFldr, "jquery-1.11.3.min"),
        makePath(engFldr, lbFldr, "bootstrap.min"),
        makePath(engFldr, mathFldr, "vector"),
        makePath(engFldr, mathFldr, "matrix"),
        makePath(engFldr, utilFldr, "util"),
        makePath(engFldr, shapsFldr, "square"),
        makePath(engFldr, utilFldr, "loaders")
    ];
    
    function doLoad(){
        scripts.forEach(function(value, index, ar){
            var path = '<'+'script src="'+ value +'" type="application/javascript"><' + '/script>';
          //  console.log(path);
            document.write(path);
        });
    }
    
    function runMain(){
        
    }
    
    function makePath(){
        var path = basePath, args = arguments;
        for(var i = 0; i < args.length; i++){
            path += "/" + args[i]
        }
        return path + ".js";
    }
    
    doLoad();
    
})(); 