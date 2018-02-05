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
        var newContent = $('#reviewContent').val().replace(/\r\n|\r|\n/g, "\\n");
        alert(newContent);
        $('#reviewContent').val(newContent);
        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
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


    $('#postSuccessAlert, #deleteReviewSuccessAlert').on('close.bs.alert', function () {
        location.reload(true);
    });
});

function deleteReview(button) {
    var reviewId = $(button).attr("class").split(" ").pop();
    var form = $('#deleteReviewForm'+reviewId);
    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {
                $('#deleteReviewSuccessAlert').show();
            } else {
                $('#reviewFailureAlert').show();
            }
        },
        error: function (data) {
            $('#reviewFailureAlert').show();
        }
    });

}

function postReview(button) {
    var reviewId = $(button).attr("class").split(" ").pop();
    var form = $('#postReviewForm'+reviewId);
    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {
                $('#postSuccessAlert').show();

            } else {
                $('#reviewFailureAlert').show();
            }
        },
        error: function (data) {
            $('#reviewFailureAlert').show();
        }
    });


}


