<?php
/*
 * Image editor form.
 */

if ( !defined( 'ABSPATH' ) ) {
    die( 'Security check' );
}

if ( !isset( $data ) ) {
    $data = array();
}

$data = array_merge( array(
    'alignment' => 'none',
    'alignment_options' => array(),
    'alt' => '',
    'height' => '',
    'image' => '',
    'image_size' => 'full',
    'preview' => '',
    'size_options' => array(),
    'title' => '',
    'warning_remote' => false,
    'width' => '',
    'url' => false,
    'onload' => '',
        ), (array) $data );

if ($data['warning_remote']) {
    if ( wpcf_is_embedded() ) {
        $warning_remote = __( 'Remote image resize is disabled, so Types will only resize images that you upload.', 'wpcf' );
    } else {
        $warning_remote = sprintf( __( 'Remote image resize is currently disabled, so Types will only resize images that you upload. To change, go to the %sTypes settings page%s.',
                        'wpcf' ),
                '<a href="' . admin_url( 'admin.php?page=wpcf-custom-settings#types-image-settings' ) . '" target="_blank">',
                '</a>' );
    }
}
?>

<div data-bind="template: {name:'tpl-types-modal-image'}"></div>

<!--TYPES MODAL IMAGE-->
<script id="tpl-types-modal-image" type="text/html">

<div class="fieldset">
	<p>
		<label for="image-title" class="input-title"><?php _e( 'Image title', 'wpcf' ); ?></label>
		<input id="image-title" type="text" name="title" value="<?php echo $data['title']; ?>" />
	</p>
	<p>
		<label for="image-alt" class="input-title"><?php _e( 'Alternative text', 'wpcf' ); ?></label>
		<input id="image-alt" type="text" name="alt" value="<?php echo $data['alt']; ?>" />
	</p>
</div>

<div class="fieldset form-inline">
	<h2><?php _e( 'Position and size', 'wpcf' ); ?></h2>
	<p>
	<!--<h2><?php _e( 'Alignment', 'wpcf' ); ?></h2>-->
		<label for="image-alignment"><?php _e( 'Alignment', 'wpcf' ); ?></label>
		<select id="image-alignment" name="alignment">
			<?php foreach ( $data['alignment_options'] as $align => $title ): ?>
				<option id="image-align-<?php echo $align; ?>"<?php if ( $data['alignment'] == $align ) echo 'selected="selected"'; ?>> <?php echo $align; ?></option>
				<label for="image-align-<?php echo $align; ?>"><?php echo $title; ?></label>
			<?php endforeach; ?>
		</select>
	</p>
</div>

<div class="fieldset form-inline">
	<p>
	<!--<h2><?php _e( 'Pre-defined sizes', 'wpcf' ); ?></h2>-->
		<label for="image_size"><?php _e( 'Pre-defined sizes', 'wpcf' ); ?></label>
		<select id="image_size" name="image_size" data-bind="value: image_size, disable: ted.params.warning_remote || false ">
			<?php foreach ( $data['size_options'] as $size => $title ): ?>
				<option id="image_size-<?php echo $size; ?>" value="<?php echo $size; ?>">
					<?php echo $title; ?>
				</option>
			<?php endforeach; ?>
		</select>

<?php if ( $data['warning_remote'] ) : ?>
<!--		Conditional icon displaying for dismissed warning message -->
<i class="icon-warning-sign js-show-tooltip" data-header="<?php _e( 'Image resize disabled', 'wpcf' ); ?>" data-content="<?php echo htmlspecialchars( $warning_remote ); ?>"></i>
<?php endif; ?>

	</p>

	<div class="group-nested" data-bind="visible: image_size() == 'wpcf-custom'">
		<p>
	    	<label for="image-width" class="input-title"><?php _e( 'Width', 'wpcf' ); ?></label>
	    	<input id="image-width" type="text" name="width" value="<?php echo $data['width']; ?>" />
	    </p>
	    <p>
	    	<label for="image-height" class="input-title"><?php _e( 'Height', 'wpcf' ); ?></label>
	    	<input id="image-height" type="text" name="height" value="<?php echo $data['height']; ?>" />
	    </p>
	    <p>
	    	<label for="image-proportional" class="input-title"><?php _e( 'Keep proportional', 'wpcf' ); ?></label>
	    	<input id="image-proportional" type="checkbox" name="proportional" value="1" checked="checked" />
	    </p>
	</div>
</div>

<p class="form-inline">
	<input id="image-url" type="checkbox" name="url" value="1" data-bind="checked: imageUrl, click: imageUrlDisable" />
	<label for="image-url"><?php _e( 'Output only the URL of the re-sized image instead of the img tag', 'wpcf' ); ?></label>
</p>

<!--<input id="image-onload" type="text" name="onload" value="<?php echo $data['onload']; ?>" />
<label for="image-onload"><?php _e( 'Onload callback', 'wpcf' ); ?></label>-->


</script><!--END TYPES MODAL IMAGE-->