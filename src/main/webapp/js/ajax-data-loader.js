$(document).ready(function () {

    $.ajax({
        type: 'get',
        url: '/mpb?command=take_country_list',
        success: function (responseJson) {
            var selectCountry = $("#countryList");
            selectCountry.find("option").remove();
            $.each(responseJson, function (key, value) {
                var currOption = $("<option>").val(value.countryId).text(value.countryName);
                $(selectCountry).append(currOption);
            });
            $('#countryList').selectpicker('refresh');
        },
        error: function (responseJson) {
            console.log("error occurred")
        }
    });
    $.ajax({
        type: 'get',
        url: '/mpb?command=take_genre_list',
        success: function (responseJson) {
            var selectGenre = $("#genreList");
            selectGenre.find("option").remove();
            $.each(responseJson, function (key, value) {
                var currOption = $("<option>").val(value.genreId).text(value.genreName);
                $(selectGenre).append(currOption);
            });
            $('#genreList').selectpicker('refresh');
        },
        error: function (responseJson) {
            console.log("error occurred")
        }
    });
    $("#addMovieForm").on("submit", function (event) {
        var form = $(this);
        var newContent = $('#synopsis_en').val().replace(/\r\n|\r|\n/g,"\\n");

        $('#synopsis_en').val(newContent);

        newContent = $('#synopsis_ru').val().replace(/\r\n|\r|\n/g,"\\n");
        $('#synopsis_ru').val(newContent);

        /*$.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
            success: function (data) {
                if (data === 'success') {
                    alert("success");
                } else {
                    alert("failure")
                }
            },
            error: function (data) {
               alert("failure")
            }
        });*/
    });
});
