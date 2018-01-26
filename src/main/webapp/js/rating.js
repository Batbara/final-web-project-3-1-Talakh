$(function () {
    $('#ratingSetFailed, #ratingSetSuccessful').on('hidden.bs.modal', function () {
        location.reload(true);
    });
    var starElements = $('.starrr');
    var elementsNum = starElements.length;
    for (var element = 0; element < elementsNum; element++) {
        var currStar = starElements[element];

        var showId = $(currStar).attr("class").split(" ").pop();
        var rating = retrieveShowRating(showId);
        var isReadOnly = checkForReadOnly();
        $(currStar).starrr({
            max: 10,
            rating: rating,
            readOnly: isReadOnly,
            change: function (e, value) {

                var showId = $(e.target).attr("class").split(" ").pop();

                var dataToSend = 'command=add_user_rate&' +
                    'showId=' + showId + '&' +
                    'userRate=' + value;

                $.ajax({
                    type: 'post',
                    url: '/mpb',
                    data: dataToSend,
                    success: function (data) {
                        if (data === 'success') {
                            $('#ratingSetSuccessful').modal('show');
                        } else {
                            $('#ratingSetFailed').modal('show');
                        }

                    },
                    error: function (data) {
                        $('#ratingSetFailed').modal('show');

                    }
                });

                e.preventDefault();
            }
        })
    }


});

function retrieveShowRating(showId) {
    var rateElements = $('.rate');
    var elementsNum = rateElements.length;
    for (var element = 0; element < elementsNum; element++) {
        var currRateEl = rateElements[element];

        if (currRateEl.classList.contains(showId)) {
            var rateValueString = $(currRateEl).text();
            rateValueString = rateValueString.split(" ");
            for (var i = 0; i < rateValueString.length; i++) {
                if (rateValueString[i].indexOf(".") !== -1) {
                    return parseInt(rateValueString[i], 10)
                }
            }

        }
    }
    return 0;
}

function checkForReadOnly() {
    var login = $('a[href*="login"]');
    return login.length != 0;

}