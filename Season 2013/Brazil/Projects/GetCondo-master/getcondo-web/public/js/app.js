$(function() {

    // Confirm deleting resources
    $("form[data-confirm]").submit(function() {
        if ( ! confirm($(this).attr("data-confirm"))) {
            return false;
        }
    });

    $('.item-row').on('keydown','.tab-trigger',function(e) {

        var $this = $(this);
        var $parent = $this.parent().parent();

        var code = e.keyCode || e.which;
        if (code == '9') {

            $parent.clone(true).find("input").val("").end().appendTo($parent.parent());

            $('.part-number:last-child').focus();

            e.preventDefault();
        }

    });

});