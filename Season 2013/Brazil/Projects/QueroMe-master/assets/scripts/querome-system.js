$(document).ready(function() {

  $('.user img').on('click', function() {
    $(this).toggleClass('active').end();
    return $(this).next().slideToggle();
  });

  $('body').on('click', '[data-confirm]', function() {
    if (confirm($(this).data('confirm'))) {
      return true;
    } else {
      return false;
    }
  });

  $('body').on('click', 'span.prepend, span.append', function() {
    return $(this).siblings('input').focus();
  });

  $('body').on('click', 'ul.categories li', function() {
    $(this).siblings('.active').removeClass('active');
    $(this).addClass('active');
    $(this).parents('ul').next().val($(this).data('value'));
  });

  $('body').on('click', '.image-upload', function() {
    $(this).next().click();
  });

  $('body').on('change', '.image-upload-field', function(event) {
    var img = document.createElement('img');
    var reader = new FileReader();
    reader.onloadend = function() {
      img.src = reader.result;
    }
    reader.readAsDataURL(event.originalEvent.srcElement.files[0]);
    $(this).prev().addClass('selected').html(img);
  });
});