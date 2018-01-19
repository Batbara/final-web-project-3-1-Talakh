function showProfileMenu() {
    var profileDropdown  = document.getElementById("profileDropdown");
    profileDropdown.classList.toggle("showProfile");
   /* profileButton.onmouseover = profileButton.onmouseout = function (event) {
        if (event.type == 'mouseover') {

            event.target.style.background = "#494141"
        }
        if (event.type == 'mouseout') {
            event.relatedTarget.style.background = "#595151"
        }
    }
    if(profileDropdown.classList.contains("show")){

        profileButton[0].style.backgroundColor="#494141";
    }*/
    window.onclick = function(event) {
        event.preventDefault();
            if (!event.target.matches('.profile-button')) {

                var dropdowns = document.getElementsByClassName("profile-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('showProfile')) {
                        var profileButton = document.getElementsByClassName("profile-button");
                        profileButton[0].style.backgroundColor = "#595151";
                        openDropdown.classList.remove('showProfile');
                    }
                }
            }
    };
    return false;
}
window.onload =
    function (ev) {

        var profileButton = document.getElementsByClassName("profile-button");
        var buttonWidth = $(profileButton).width();
        var showMargin = -buttonWidth/2;
        $('.profile-content').css('margin-left',showMargin+'px');

        profileButton.onmouseover = profileButton.onmouseout = function (event) {
            if (event.type === 'mouseover') {

                event.target.style.background = "#494141"
            }
            if (event.type === 'mouseout') {

                event.relatedTarget.style.background = "#595151"
            }
        };
    };
// Close the dropdown if the user clicks outside of it
