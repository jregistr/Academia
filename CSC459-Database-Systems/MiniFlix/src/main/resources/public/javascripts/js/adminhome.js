const $userTable = $('#usersTable');
const $histTable = $('#userInfoData');
const $recomTable = $('#userRecommendation');

$userTable.bootstrapTable({
    onClickRow: (row)=> {
        const id = row["ID"];
        $.ajax({
            url: '/moviesforuserhist',
            data: {
                uid: id
            },
            success: (data)=> {
                $histTable.bootstrapTable('load', data);
            },
            error: (error)=> {

            }
        });

        ///recommendforthisuser
        $.ajax({
            url: '/recommendforthisuser',
            data: {
                uid: id
            },
            success: (data)=> {
                $recomTable.bootstrapTable('load', data);
            },
            error: (error)=> {

            }
        });

    }


});
