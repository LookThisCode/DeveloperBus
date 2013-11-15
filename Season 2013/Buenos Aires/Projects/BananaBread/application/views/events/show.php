<div style="margin: 0 10px;overflow:hidden;">
  <div class="show-band-top">
    <div class="pull-left">
      <img src="<?php echo base_url('assets/images/banda1-gr.png'); ?>" alt="">
    </div>
      <p class="title"><?php echo $band->name; ?></p>
      <p style="font-size:18px;"><?php echo $band->description; ?></p>
  </div>

  <div class="next-shows">
    <h3>Pr&oacute;ximos Shows</h3>
    <div class="event-display">
      <div class="day pull-left">18</div>
      <div class="event-description">
        <p class="title">Evento interesante</p>
        <p class="description">Alguna descripcion</p>
      </div>
    </div>
    <div class="event-display">
      <div class="day pull-left">18</div>
      <div class="event-description">
        <p class="title">Evento interesante</p>
        <p class="description">Alguna descripcion</p>
      </div>
    </div>

    <a 
      href="https://www.mercadopago.com/mla/checkout/pay?pref_id=99325445-f24415ee-138b-4c46-873b-1a230e9084b1"
      name="MP-payButton"
      class="orange-ar-m-rn">Venta de entradas</a>
    <script type="text/javascript">
    (function(){function $MPBR_load(){window.$MPBR_loaded !== true && (function(){var s = document.createElement("script");s.type = "text/javascript";s.async = true;s.src = ("https:"==document.location.protocol?"https://www.mercadopago.com/org-img/jsapi/mptools/buttons/":"http://mp-tools.mlstatic.com/buttons/")+"render.js";var x = document.getElementsByTagName('script')[0];x.parentNode.insertBefore(s, x);window.$MPBR_loaded = true;})();}window.$MPBR_loaded !== true ? (window.attachEvent ?window.attachEvent('onload', $MPBR_load) : window.addEventListener('load', $MPBR_load, false)) : null;})();
    </script>


  </div>

  <div class="medias" >
    <div class="title">
      <img src="<?php echo base_url('assets/images/youtube.png'); ?>"/>
      <img class="abierta" src="<?php echo base_url('assets/images/flechita-abajo.png'); ?>" alt="" style="display: none;">
      <img class="cerrada" src="<?php echo base_url('assets/images/flechita-derecha.png'); ?>" alt="">
    </div>
    <div class="description" style="display:none;">
      <div class="span3"><iframe width="100%" height="215" src="//www.youtube.com/embed/7GCLZfTD_xA" frameborder="0" allowfullscreen></iframe></div>
      <div class="span3"><iframe width="100%" height="215" src="//www.youtube.com/embed/F7hVAH3f2g0" frameborder="0" allowfullscreen></iframe></div>
      <div class="span3"><iframe width="100%" height="215" src="//www.youtube.com/embed/Rh0G2dd3tCA" frameborder="0" allowfullscreen></iframe></div>
      <?php if ($is_logged): ?>
        <form action="<?php echo site_url('band/upload/google') ?>" method="post" enctype="multipart/form-data">
          <input type="file" name="file">
          <input type="submit">
        </form>
      <?php endif ?>
    </div>
  </div>

  <div class="medias" >
    <div class="title">
      <img src="<?php echo base_url('assets/images/soundcloud.png'); ?>"/>
      <img class="abierta" src="<?php echo base_url('assets/images/flechita-abajo.png'); ?>" alt="" style="display: none;">
      <img class="cerrada" src="<?php echo base_url('assets/images/flechita-derecha.png'); ?>" alt="">
    </div>
    <div class="description" style="display:none;">
      <iframe width="100%" height="100" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/68323400"></iframe>
      <iframe width="100%" height="100" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/68322738"></iframe>
      <iframe width="100%" height="100" scrolling="no" frameborder="no" src="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/68322084"></iframe>
      <?php if ($is_logged): ?>
        <form action="<?php echo site_url('band/upload/soundcloud2') ?>" method="post" enctype="multipart/form-data">
          <input type="file" name="file">
          <input type="submit">
        </form>
      <?php endif ?>
    </div>
  </div>


  <script>
    $(function() {
      $(".medias").on("click", ".abierta", function() {
        var $this = $(this);
        $this.hide().next().show().closest('.medias').find('.description').hide();
      }).on("click", ".cerrada", function() {
        var $this = $(this);
        $this.hide().prev().show().closest('.medias').find('.description').show();
      })
    });
  </script>

</div>
