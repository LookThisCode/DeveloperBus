(function($) {
  $(document).on('ready', function(){
    var $form = $('#form-what-do-you-need');
    $form.find('#what').on('focus', function(){
      $form.find('fieldset').slideDown();
    });
  });



})(jQuery);