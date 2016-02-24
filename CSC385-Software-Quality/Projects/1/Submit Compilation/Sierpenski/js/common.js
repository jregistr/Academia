var Vector2 = (function(x, y){
	
	var x = x, y = y;
	
	function toArray(){
		return [x, y];
	}
	
	function copy(){
		return Vector2(x, y);
	}
	
	function toString(){
		return "(" + x + "," + y + ")";
	}
	
	return{type:2,getX:x, getY:y, toArray:toArray, copy:copy, toString:toString}
	
});

var Vector3 = (function(x, y, z){
	
	var x = x, y = y, z = z;
	
	function toArray(){
		return [x, y, z];
	}
	
	function copy(){
		return Vector3(x, y, z);
	}
	
	function toString(){
		return "(" + x + "," + y + "," + z + ")";
	}
	
	return{ type:3, getX:x, getY:y, getZ:z, toArray:toArray, copy:copy, toString:toString}
	
});

var Vector4 = (function(x, y, z, w){
	
	var x = x, y = y, z = z, w = w;
	
	function toArray(){
		return [x, y, z, w];
	}
	
	function copy(){
		return Vector4(x, y, z, w);
	}
	
	function toString(){
		return "(" + x + "," + y + "," + z + "," + w + ")";
	}
	
	return{type:4, getX:x, getY:y, getZ:z, getW:w, toArray:toArray, copy:copy, toString:toString}
	
});

