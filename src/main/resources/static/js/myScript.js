/**
 * Created by RENT on 2017-03-18.
 */

$(document).ready(function () {
    $("#switch-category-fields").on("click", function () {
            if ($(this).text() == "New Category") {
                $(this).text("List");
                $("#Category2").attr('type', 'text');
                $("#Category").hide();
            } else {
                $(this).text("New Category");
                $("#Category2").attr('type', 'hidden');
                $("#Category").show();
            }
        }
    );
    $('#form-id').submit(function () {
        var parent =$('#switch-category-fields').parent();
        var valueToSubmit=parent.find(":visible").val();
        $('#category-hidden').val(valueToSubmit);
        return true;
    })
});





