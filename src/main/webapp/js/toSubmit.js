function submitShowsListOrder() {
    var atrrVal = $('#orderSelection').val();
    $('#order').attr('value', atrrVal);

    $("#orderForm").submit();
}

function submitPerPage() {
    var atrrVal = $('#onPageSelection').val();
    $('#onPage').attr('value', atrrVal);
    $("#onPageForm").submit();

}

$("#loginForm, #registerForm").on("submit", function (event) {
    var form = $(this);
    var submitButton = $(form).find(':submit');
    if (submitButton.hasClass('disabled') === true) {
        return;
    }
    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data.redirect) {
                window.location.href = data.redirect;
                return;
            }

            if(!$.trim( $('#errorContainer').html() ).length ) {
                $('#errorContainer').append('<div class="alert alert-warning alert-dismissable fade in" id="loginErrorContainer">\n' +
                    '                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>\n' +
                    '                            <span id="errorText"></span>\n' +
                    '                        </div>');

            }
                $("#errorText").html(Object.values(data)[0]);


        },
        statusCode: {
            500: function () {
                window.location.href = "/server_error";
            }
        }
    });
    event.preventDefault();
});
$("#deleteShowForm").on("submit", function (event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {
                $("#deleteShowFormWrapper").hide();
                showAnimated("#deleteShowSuccessAlert");
            } else {
                $('#deleteShowFailureAlert').show();
            }
        },
        error: function (data) {
            $('#deleteShowFailureAlert').show();
        }
    });

    event.preventDefault();

});
$("#banForm").on("submit", function (event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {
                $('#banButton').hide();
                $('#banSuccessAlert').show();
            } else {

                showAnimated('#banFailureAlert');
            }
        },
        error: function (data) {
            showAnimated('#banFailureAlert');
        }
    });

    event.preventDefault();

});
$("#unbanForm").on("submit", function (event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {
                $('#unbanDialog').modal('hide');
            } else {

                $('#unbanFailureAlert').show();
            }
        },
        error: function (data) {

            $('#unbanFailureAlert').show();
        }
    });

    event.preventDefault();

});
$("#changeStatusForm").on("submit", function (event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if (data === 'success') {

                showAnimated('#statusChangeSuccessAlert');
            } else {

                showAnimated('#statusChangeFailureAlert');
            }
        },
        error: function (data) {

            showAnimated('#statusChangeFailureAlert');
        }
    });

    event.preventDefault();

});

$("#addTvShowForm, #addMovieForm").on("submit", function (event) {
    var form = $(this);

    var isDisabled = $('#addButton').hasClass("disabled");
    if (isDisabled === true) {
        return false;
    }
    ajaxShowAdding(form);

    event.preventDefault();

});
$(function () {

    $('.selectpicker').on('change', function () {
        var selected = $(this).find("option:selected").val();
        if (selected === 'FINISHED') {
            $('#finishedYearFormGroup').slideDown(500, function () {
            });
        } else {
            $('#finishedYearFormGroup').slideUp(500, function () {
            });
        }
    });

});

function showAnimated(alertId) {
    $(alertId).fadeTo(2000, 500).slideUp(500, function () {
        $(alertId).slideUp(500);
    });
}

function ajaxShowAdding(form) {
    var data = new FormData(form[0]);
    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.redirect) {
                window.location.href = data.redirect;
                return;
            }
            alert(data.addShowError);

        },
        statusCode: {
            500: function () {
                window.location.href = "/server_error";
            }
        }
    });
}