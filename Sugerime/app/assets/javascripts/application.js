// This is a manifest file that'll be compiled into application.js, which will include all the files
// listed below.
//
// Any JavaScript/Coffee file within this directory, lib/assets/javascripts, vendor/assets/javascripts,
// or vendor/assets/javascripts of plugins, if any, can be referenced here using a relative path.
//
// It's not advisable to add code directly here, but if you do, it'll appear at the bottom of the
// the compiled file.
//
// WARNING: THE FIRST BLANK LINE MARKS THE END OF WHAT'S TO BE PROCESSED, ANY BLANK LINE SHOULD
// GO AFTER THE REQUIRES BELOW.
//
//= require jquery
//= require jquery-ui
//= require jquery_ujs
//= require_tree .

jQuery(document).ready(function ($) {

    function superSizeSetup(callback){
        var slideImages = [];
        $(".background-images img").each(function() {
            var obj = {};
            obj.image = $(this).attr('src');
            slideImages.push(obj);
        });
    }

    superSizeSetup(function() {
        $.supersized({
            start_slide        :   0,           // Start slide (0 is random)
            vertical_center      :   1,         // Vertically center background
            horizontal_center  :   1,           // Horizontally center background
            fit_landscape      :   0,
            fit_portrait           :   0,
            slides             :    slideImages

        });
    });

});
