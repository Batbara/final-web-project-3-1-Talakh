function submitMoviesOrder() {
    var atrrVal = $('#orderSelection').val();
    $('#order').attr('value',atrrVal);

    $("#orderForm").submit();
}
function submitPerPage() {
    var atrrVal = $('#onPageSelection').val();
    $('#onPage').attr('value',atrrVal);
    $("#onPageForm").submit();

}
$( document ).ready(setSelectedOptions);
function setSelectedOptions(){
    setSelectionFor('orderSelection', 'order');
    setSelectionFor('onPageSelection','onPage');

}
function setSelectionFor(selectTagID,parameter) {
    $('option:selected', 'select[name='+selectTagID+']').removeAttr('selected');
    var optionToSelect = getUrlParameter(parameter);
    $('#'+selectTagID+'').find('option[value='+optionToSelect+']').attr("selected",true);
}
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

$(document).on("submit", "#banForm", function(event) {
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