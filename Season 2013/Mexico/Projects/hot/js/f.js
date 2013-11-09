/*! js hot it | Funciones | 071113 */
var iu__ = new oiu();
var m__ = new omapa();
$(window).load(function() {
	init();
});
$(window).resize(function() {
	if(iu__ != undefined){
		resize();
	}
});

function init(){
    // función inicial
    m__.draw();
    resize();
    $('#form-init').submit(function(){
    	get_info();
    	return false;
    });
}
function resize(){
    // ajusta el mapa en la pantalla para no mostrar scroll
    iu__.resize();
}

function get_info(){
    // traer informaciÃ³n del api
    var tag = $('#tags').val();
    if(tag != '' && tag != undefined){
	    var url = '/getinfo?tag=' + tag.toString();
	    get_ajax(url, 'put_info');
	}
}

function put_info(txt){
    // coloca la info en el mapa
    var v = $.parseJSON( txt );
    m__.clear();
    for(var i = 0; i < v.objetos.length; i++){
    	var o = v.objetos[i];
    	m__.put_marker(o);
    }
    if(m__.markers.length > 0){
    	m__.map.fitBounds(m__.bounds);
    	// cluster de los mapas
    	m__.put_cluster();
    }
}

function otags(tag, fecha, lon, lat){
	// objetos de los tags
	this.tag = tag;
	this.fecha = fecha;
	this.lon = lon;
	this.lat = lat;
}

function find_location(){
	// buscamos la ubicación del usuario
	m__.get_location();
}


