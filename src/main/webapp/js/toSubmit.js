function submitShowsListOrder() {
    var atrrVal = $('#orderSelection').val();
    $('#order').attr('value',atrrVal);

    $("#orderForm").submit();
}
function submitPerPage() {
    var atrrVal = $('#onPageSelection').val();
    $('#onPage').attr('value',atrrVal);
    $("#onPageForm").submit();

}

$("#form-login").on("submit", function (event) {

    event.preventDefault();
});
$("#banForm").on("submit", function(event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if(data === 'success') {
                $('#banSuccessAlert').show();
            } else {
                $('#banFailureAlert').show();
            }
        },
        error: function (data) {
            $('#banFailureAlert').show();
        }
    });

    event.preventDefault();

});
$("#unbanForm").on("submit", function(event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if(data === 'success') {
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
$("#changeStatusForm").on("submit", function(event) {
    var form = $(this);

    $.ajax({
        type: form.attr('method'),
        url: form.attr('action'),
        data: form.serialize(),
        success: function (data) {
            if(data === 'success') {
                $('#statusChangeSuccessAlert').show();
            } else {
                $('#statusChangeFailureAlert').show();
            }
        },
        error: function (data) {
            $('#statusChangeFailureAlert').show();
        }
    });

    event.preventDefault();

});