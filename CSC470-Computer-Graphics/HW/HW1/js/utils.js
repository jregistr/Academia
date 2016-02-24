/*Takes a list of vectors and returns a list of the values of the components*/
function flattenVectorList(list, degree){
	if(degree == 2){
		var temp = [];
		$.each(list, function(index, value){
			temp.push(value.getX, value.getY);
		});
		return temp;
	}else if(degree == 3){
		var temp = [];
		$.each(list, function(index, value){
			temp.push(value.getX, value.getY, value.getZ);
		});
		return temp;
	}else if(degree == 4){
		var temp = [];
		$.each(list, function(index, value){
			temp.push(value.getX, value.getY, value.getZ, value.getW);
		});
		return temp;
	}else{
		return null;
	}
}

/*Pushes values from all the lists passed into the last list*/
function pushList(){
	var end = arguments[arguments.length - 1];
	for(var i = 0; i < arguments.length - 1; i ++){
		var list = arguments[i];
		$.each(list, function(index, value){
			end.push(value);
		});
	}
}

/*returns a random color in the form of an array of 4 values. Alpha value is always 1*/
function randomColor(){
	return [Math.random(), Math.random(), Math.random(), 1];
}
