
function viewButton(name){
    $.ajax({
        url : "Profile",
        data:{
            viewing:name
        },
        type:"GET"
    });
}


$(document).ready(function(){

    $('#postComment').click(function(){
        var pos = 0;
        var to = 0;
        var parts = $('#postComment').val().split(",");
        pos = Number(parts[0]);
        to = Number(parts[1]);
        $.ajax({
                url: "Services/Postcomment",
                data : {
//                  user id and user id
                    user :pos,
                    target : to,
                    comment : $('#userComment').val()
                },
                type : "POST",
                 success : function (response) {

                     $('#messages').append(
                             "<div class='comment'> " +
                                 "<h5><b>" + response['poster_user'] +"</b>" + "<i> " + response['time_posted'] + "</i>" + "<h5>" +
                                     "<p class='msg'>" + response['msg'] + "</p>" +
                              "</div>"
                     );
                     $('#userComment').val("");
                 }
        });
    });

    $('#findFriend').click(function(){
        $.ajax({
            url:"Services/UserInf",
            data : {
                infoType : "names",
                begins : $('#findFriendInput').val()
            },
            type : "POST",
            success : function(response){
//                            alert(response);
                //$('#usersResult').empty();
                $.each(response.names,function(index,name){

                    $('#usersResult').append(
                        "<div class='form-horizontal' style='width: 100%; margin: 15px auto' >" +
                            "<button class='disabled btn btn-default' style='text-align: center; font-size: 1.5em; color: #000000; width: 80%'>" + "<b style='color: #000000'>" + name + "</b>" + "</button>" +
                            "<button class='btn btn-success' value=" + name +  " onclick='viewButton(value)'>" + "<span class='glyphicon glyphicon-eye-open'></span>" + "</button>" +
                            "<button class='btn btn-primary' value=" + name + " onclick='addButton(value)'>" + "<span class='glyphicon glyphicon-plus-sign'></span>" + "</button>" +
                         "</div>"
                     );

                 });
            }
        });
    });

    $('#battleButton').click(function(){
        $.ajax({
            url : "Services/Randombattle",
            type : "POST",
            success : function(response){
                location.reload();
            }
        });
    });



});