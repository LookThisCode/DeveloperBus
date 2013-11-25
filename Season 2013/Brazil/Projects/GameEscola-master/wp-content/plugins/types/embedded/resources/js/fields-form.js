/* 
 * Group edit page JS
 * 
 * This file should be used from now on as dedicated JS for group edit page.
 * Avoid adding new functionalities to basic.js
 * 
 * Thanks!
 * 
 * @since Types 1.1.5
 * @autor srdjan
 */


jQuery(document).ready(function($){
    // Invoke drag on mouse enter
    $('#wpcf-fields-sortable').on('mouseenter', '.js-types-sortable', function(){
        if (!$(this).parent().hasClass('ui-sortable')) {
            $(this).parent().sortable({
                revert: true,
                handle: 'img.js-types-sort-button',
                containment: 'parent'
            });
        }
    });
    // Sort and Drag
    jQuery('#wpcf-fields-sortable').sortable({
        revert: true,
        handle: 'img.wpcf-fields-form-move-field',
        containment: 'parent'
    });
    jQuery('.wpcf-fields-radio-sortable').sortable({
        revert: true,
        handle: 'img.js-types-sort-button',
        containment: 'parent'
    });
    jQuery('.wpcf-fields-checkboxes-sortable').sortable({
        revert: true,
        handle: 'img.js-types-sort-button',
        containment: 'parent'
    });
    jQuery('.wpcf-fields-select-sortable').sortable({
        revert: true,
        handle: 'img.js-types-sort-button',
        containment: 'parent'
    });
});