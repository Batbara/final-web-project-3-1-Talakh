$(".bannedUser").on("click", function () {
   var targetID= $(this).get(0).id;

    var dropdowns = document.getElementsByClassName("banContent");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
        var row = dropdowns[i];
        if (row.classList.contains(targetID)) {
          $(row).toggle();
        }
    }
});
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();
    $('.alert').hide();
    $('#banDialog, #unbanDialog').on('hidden.bs.modal', function () {
       location.reload(true);
    })
});

$("#banTime").val(new Date().toJSON().slice(0,19));
$("#banTime").attr("min", new Date().toJSON().slice(0,19));
$("#unbanTime").attr("min", new Date().toJSON().slice(0,19));

$(".banButton").on("click", function () {
    return retrieveUserNameAndID("#banDialog", this, "#userBanID", "#banUserTitle");

});
$(".unbanButton").on("click", function () {
    return retrieveUserNameAndID("#unbanDialog", this, "#userUnbanID", "#unbanUserName");
});

function retrieveUserNameAndID(dialogID, button, idToSet, userNameToSet) {
    $(dialogID).modal('show');
    var buttonClasses = $(button).attr("class").split(" ");
    var idToMatch = buttonClasses[buttonClasses.length-1];

    var userIDs = document.getElementsByClassName("userID");

    for (var idElement = 0; idElement < userIDs.length; idElement++) {
        var currID = userIDs[idElement];
        if (currID.classList.contains(idToMatch)) {
            var idValue = $(currID).text();
            $(idToSet).attr("value", idValue);
        }
    }

    var userNames = document.getElementsByClassName("userName");
    for (var nameElement = 0; nameElement < userNames.length; nameElement++) {
        var currUserName = userNames[nameElement];
        if (currUserName.classList.contains(idToMatch)) {
            var userName = $(currUserName).text();
            $(userNameToSet).text(userName);
        }
    }
    return false;
}