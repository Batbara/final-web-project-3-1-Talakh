window.onload = function () {
    replaceLineBreaks();
};

function replaceLineBreaks() {
    var reviews = document.getElementsByClassName("reviewContent");
    var i;
    for (i = 0; i < reviews.length; i++) {
        var review = reviews[i];
        var str = review.innerHTML;
        var res = str.replace(/\\n/g, "<br/><br/>");
        review.innerHTML = res;
    }
}
// ===== Scroll to Top ====
$(window).scroll(function() {
    if ($(this).scrollTop() >= 50) {        // If page is scrolled more than 50px
        $('#return-to-top').fadeIn(200);    // Fade in the arrow
    } else {
        $('#return-to-top').fadeOut(200);   // Else fade out the arrow
    }
});
$('#return-to-top').click(function() {      // When arrow is clicked
    $('body,html').animate({
        scrollTop : 0                       // Scroll to top of body
    }, 500);
});