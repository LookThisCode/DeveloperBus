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
    // ajusta el mapa en la pantalla par ano mostrar scroll
    iu__.resize();
}

function get_info(){
    // traer información del api
    var tag = $('#tags').val();
    var url = '/getinfo?tag=' + tag.toString();
    get_ajax(url, 'put_info');
}

function put_info(txt){
    // coloca la info en el mapa
    alert(txt);
}
