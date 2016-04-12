const movieTable = $('#movieTable');
const recommendedToTable = $('#recommendedTo');
const statsTable = $('#statsTable');
const categoriesSelect = $('#categories');
$('#changeCat').click(()=> {
    changeClicked();
});

$.ajax({
    url: '/categories',
    success: (data)=> {
        data.forEach((elem)=> {
            categoriesSelect.append($("<option></option>")
                .attr("value", elem["ID"])
                .text(elem["Name"]));
        })
    }, error: (error)=> {
        console.log(error);
    }
});

var movieID = -1;

movieTable.bootstrapTable({
    onClickRow: (row)=> {
        movieID = row["ID"];
        applySelectedToTables();
    }
});

function applySelectedToTables() {
    if (movieID != -1) {
        $.ajax({
            url: '/whowasthismovierecto',
            data: {
                mid: movieID
            },
            success: (data)=> {
                recommendedToTable.bootstrapTable('load', data);
            }, error: (error)=> {

            }
        });

        $.ajax({
            url: '/moviestats',
            data: {
                mid: movieID
            },
            success: (data)=> {
                statsTable.bootstrapTable('load', data);
            }, error: (error)=> {
                console.log(error);
            }
        });

        updateSelect();

    }
}

function updateSelect() {
    $.ajax({
        url: '/moviecategory',
        data: {
            mid: movieID
        },
        success: (data)=> {
            categoriesSelect.val(data["ID"]);
        }, error: (error)=> {
            console.log(error);
        }
    })
}

function changeClicked() {
    $.ajax({
        url: '/moviecategoryset',
        data: {
            mid: movieID,
            cat: categoriesSelect.val()
        }
    }).then(()=> {
        updateSelect();
    });
}