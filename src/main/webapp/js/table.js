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
});

$("#banTime").val(new Date().toJSON().slice(0,19));
$("#banTime").attr("min", new Date().toJSON().slice(0,19));