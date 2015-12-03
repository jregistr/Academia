var interv = null;
function checkLoad(){
    $.ajax({
        url:'/welcome/isDoneLoading',
        type:"GET",
        success:function(response){
            var done = response["load"];
            if(done == true){
                clearInterval(interv);
                //<a class="btn btn-primary" href="/graph/index"></a>
                $('#loadIndic').hide();
                var aTag = document.createElement("a");
                aTag.className = "form-control btn-lg btn-primary";
                aTag.setAttribute("href","/graph/index");
                aTag.appendChild(document.createTextNode("CLICK THIS TO GO TO GRAPHS"));
                $('body').append(aTag);
            }
        }
    });
}

$( document ).ready(function() {
     $('#loadIndic').hide();

    var pressedLoad = false;

    $('#doLoad1').click(function () {//when either of the load buttons get's clicked
        pressedLoad = true;
        $('#paramsBox').hide();
        $('#loadIndic').show();
        var seed = $('#seed').val();
        var amount = $('#amount').val();
        var depth = $('#depth').val();
        $.ajax({
            url:'/welcome/load',
            data:{
                seed:seed,
                amount:amount,
                depth:depth
            }
        });
        interv = setInterval(checkLoad,5000);
    });



});