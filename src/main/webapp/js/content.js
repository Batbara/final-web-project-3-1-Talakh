$(document).ready(function () {
    replaceLineBreaks("reviewContent");
    replaceLineBreaks("synopsis-content");
});

function replaceLineBreaks(className) {
    var elementsToReplace = document.getElementsByClassName(className);
    var i;
    for (i = 0; i < elementsToReplace.length; i++) {
        var element = elementsToReplace[i];
        var str = element.innerHTML;
        var res = str.replace(/\\n/g, "<br/>");
        element.innerHTML = res;
    }
}

$(window).scroll(function() {
    if ($(this).scrollTop() >= 50) {
        $('#return-to-top').fadeIn(200);
    } else {
        $('#return-to-top').fadeOut(200);
    }
});
$('#return-to-top').click(function() {
    $('body,html').animate({
        scrollTop : 0
    }, 500);
});