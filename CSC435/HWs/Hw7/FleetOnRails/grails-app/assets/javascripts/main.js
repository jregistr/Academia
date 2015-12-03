function addfriend(user,target){
    //alert("USER:" + user + " TARG:" + target);
    $.ajax({
        url:'/setter/index',
        data:{
            type:"friends",
            from:user,
            target:target,
            item:'add'
        },
        success:function(response){
            console.log(response);
            var list = $('#friendList');
            list.empty();
            $.each(response["friends"],function(index, value){
                //alert(value["userName"]);
                var friendBlock = document.createElement("div");
                friendBlock.className = "comment";
                var fName = document.createElement("h2");
                var fNameStrong = document.createElement("strong");
                fNameStrong.appendChild(document.createTextNode(value["userName"]));
                fName.appendChild(fNameStrong);
                friendBlock.appendChild(fName);

                var gotoForm = document.createElement("form");
                gotoForm.setAttribute("action","/profile/" + value["userName"]);
                gotoForm.setAttribute("method","post");

                var gotoButton = document.createElement("button");
                gotoButton.setAttribute("type","submit");
                gotoButton.className = "btn btn-info";
                gotoButton.appendChild(document.createTextNode("Go to Profile"));
                gotoForm.appendChild(gotoButton);
                friendBlock.appendChild(gotoForm);

                list.append(friendBlock);
            });
        }
    });
}

var selected = "";

function selectImage(url){
    selected = url;
}

/*
*
*  <div id="loadingContent" class="row" style="margin: auto">
     <div class="col-md-12" style="margin:auto;">
          <img class="img-responsive img-thumbnail" src="https://media.jumpingjack.com/JumpingJack/loading.gif">
     </div>
  </div>
* */

function fillLoading(){
    var container = $('#imageContainer');
    container.empty();
    var loadDiv = document.createElement("div");
    loadDiv.className = "row";
    loadDiv.setAttribute('style',"margin: auto");

    var col = document.createElement("div");
    col.className = "col-md-12";
    col.setAttribute('style',"margin: auto");

    var img = document.createElement("img");
    img.className = "img-responsive img-thumbnail";
    img.setAttribute('src',"https://media.jumpingjack.com/JumpingJack/loading.gif");

    col.appendChild(img);
    loadDiv.appendChild(col);
    container.append(loadDiv);

}

function fill(list){
    var counter = 0;
    var lastRow = document.createElement("div");
    lastRow.className = "row";
    var container = $('#imageContainer');
    container.empty();
    $.each(list,function(index,value){
        if(counter == 4){
            counter = 0;
            container.append(lastRow);
            lastRow = document.createElement("div");
            lastRow.className = "row";
        }

        var temp = document.createElement("div");
        temp.className = "col-lg-3 col-md-4 col-xs-6 thumb";
        temp.setAttribute('onclick',"selectImage(" + '\'' + value + '\'' + ")");

        var atag = document.createElement("a");
        atag.className="thumbnail";
        atag.setAttribute('href',"#");

        var img = document.createElement("img");
        img.className = "img-responsive";
        img.setAttribute('src',value);
        // img.setAttribute("style","max-height:400px;max-width:300px;");

        atag.appendChild(img);
        temp.appendChild(atag);
        lastRow.appendChild(temp);
        counter++;
    });
    container.append(lastRow);
}

$( document ).ready(function() {

    $('#post').click(function(){
        var message = $('#message');
        var msg = message.val();
        var from = $('#from').val();
        var target = $('#target').val();
        message.val("");

        console.log(msg + "\n" + from + "\n" + target);

        $.ajax({
            url:'/setter/index',
            data:{
                type:"comments",
                from: from,
                target:target,
                item:msg
            },
            success:function(response){
                var panel = document.createElement("div");
                panel.className = "panel";

                var panelHead = document.createElement("div");
                panelHead.className = "panel-heading";

                var h4User = document.createElement("h4");
                h4User.className="pull-left";
                h4User.appendChild(document.createTextNode(response["Poster"]));
                panelHead.appendChild(h4User);

                var h4Time = document.createElement("h4");
                h4User.className = "pull-right";
                h4Time.appendChild(document.createTextNode(response["Time"]));
                panelHead.appendChild(h4Time);
                panel.appendChild(panelHead);

                var panelBody =document.createElement("div");
                panelBody.className="panel-body";

                var h4MSG = document.createElement("h4");
                h4MSG.className = "text-info pull-left";
                h4MSG.appendChild(document.createTextNode(response["Msg"]));
                panelBody.appendChild(h4MSG);
                panel.appendChild(panelBody);
                var messages = $('#messages');
                messages.append(panel);
                messages.scrollTop = messages.scrollHeight;
            },
            error:function(error){
                alert("Error:")
            }
        });
    });

    $('#searchButton').click(function (){
        var searchBox = $('#search');
        var search = searchBox.val();
        searchBox.val("");
        var resultSet = $('#findResult');
        resultSet.empty();
        $.ajax({
            url:'/getter/index',
            data:{
                type:"like",
                target:search
            },
            success:function(response){
                console.log(response);
                $.each(response["users"],function (index,value){
                    var resultForm = document.createElement("form");
                    resultForm.setAttribute('action','/getter/index');
                    resultForm.setAttribute('method','post');

                    var group = document.createElement("div");
                    group.className = "form-group";

                    var nameButton = document.createElement("button");
                    nameButton.className = "btn btn-default";
                    nameButton.appendChild(document.createTextNode(value["userName"]));
                    group.appendChild(nameButton);
                    nameButton.disabled = true;

                    var viewButton = document.createElement("button");
                    viewButton.className = "btn btn-primary";
                    viewButton.setAttribute("type","submit");
                    viewButton.appendChild(document.createTextNode("View"));
                    group.appendChild(viewButton);

                    var addButton = document.createElement("button");
                    addButton.className = "btn btn-info";
                    addButton.setAttribute('type','button');
                    var fromU = $('#from').val();
                    var userN = value["userName"];
                    addButton.setAttribute('onclick',"addfriend(" + '\'' + fromU + "\'," + "\'" + userN + "\'" + ")");
                    addButton.appendChild(document.createTextNode("Add"));
                    group.appendChild(addButton);
                    resultForm.appendChild(group);
                    resultSet.append(resultForm);
                });
            },
            error:function(error){
                alert("Error:" + error);
            }
        });
    });

    $('#customize').click(function(){
        $('#imagePicker').modal('show');
        $.ajax({
            url:'/getter/index',
            data:{
                type:"defaults",
                target:"none"
            },
            success:function(response){
              fill(response);
            }
        });
    });


    $('#custSearchButton').click(function(){
        var searchBox = $('#custSearch');
        var search = searchBox.val();
        searchBox.val("");
        if(search != null && !(search === "")){
            fillLoading();
            $.ajax({
                url:'/getter/index',
                data:{
                    type:'images',
                    target:search
                },
                success: function (response) {
                    fill(response);
                }
            });
        }
    });

    $('#setProfile').click(function(){
        $('#imagePicker').modal('hide');
        if(selected != null && !(selected === "")){
            var from = $('#from').val();
            $.ajax({
                url:'/setter/index',
                data:{
                    type:"customization",
                    from:from,
                    target:"profile",
                    item:selected
                },
                success: function (response) {
                    console.log(response);
                    var profDiv = $('#myProf');
                    profDiv.empty();
                    var img = document.createElement("img");
                    img.className = "img-responsive img-circle";
                    img.setAttribute('src',selected);
                    profDiv.append(img);
                },
                error:function(error){
                    console.log("ERROR:" +  error);
                }
            });
        }else{
            alert("Select an image");
        }
    });

    $('#setBack').click(function(){
        $('#imagePicker').modal('hide');
        if(selected != null && !(selected === "")){
            var from = $('#from').val();
            $.ajax({
                url:'/setter/index',
                data:{
                    type:"customization",
                    from:from,
                    target:"background",
                    item:selected
                },
                success: function (response) {
                    console.log(response);
                    $('body').css({backgroundImage:"url(" + selected + ")"});
                },
                error:function(error){
                    console.log("ERROR:" +  error);
                }
            });
        }else{
            alert("Select an image");
        }
    });

});