$( document ).ready(function(){
    
    QUnit.test("Test valid square", function(assert){
        var carpet = carpetMaker.makeCarpet(Square(0, 0, 2), 4);
        assert.ok(585 == carpet.length, "PASSED!");
    });
    
    QUnit.test("test open squares", function(assert){
        var opens = carpetMaker.getEmptySubs(Square(0, 0, 2));
        assert.ok(8 == opens.length, "PASSED!");
    }); 
    
     QUnit.test("test close square", function(assert){
        var closed = carpetMaker.getFilledSub(Square(0, 0, 2));
         var pos = closed.position();
        assert.ok(true == closed != null && pos.x == 0 && pos.y == 0, "PASSED!");
    }); 
    
    QUnit.test("Test Error", function(assert){
        assert.throws(function(){
            carpetMaker.makeCarpet(Vec3(0,0,0), 4);
        },
        new TypeError(),
         "Error caught!")
    });
    
   
});