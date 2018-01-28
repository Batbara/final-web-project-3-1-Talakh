$(document).ready(function () {
    var loggedUser = $('#loggedUser').data('logged-user');
    var userIsLogged = loggedUser !== "";

    if (userIsLogged == false) {
        $('#addReviewFields').attr('disabled', 'disabled');
        $('#addReviewButton').addClass("disabled");
    } else {

        $('#addReviewFields').removeAttr('disabled');
        $('#addReviewButton').removeClass("disabled");
    }
    $('#reviewPostSuccessful').on('hidden.bs.modal', function () {
        location.reload(true);
    });
    $("#add-review-form").on("submit", function (event) {
        var form = $(this);
        event.preventDefault();
        var newContent = $('#reviewContent').val().replace(/\r\n|\r|\n/g,"<br />");
        alert(newContent);
        $('#reviewContent').val(newContent);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize().replace(/\\n/g, "<br/><br/>"),
            success: function (data) {
                if (data === 'success') {
                    $('#reviewPostSuccessful').modal('show');
                } else {
                    $('#reviewPostFailed').modal('show');
                }
            },
            error: function (data) {
                $('#reviewPostFailed').modal('show');
            }
        });
    });
});




