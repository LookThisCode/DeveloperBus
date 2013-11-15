/*! js Hot it | Objeto Interfaz de Usuario | 071113 */


function oiu(){
    // objeto de la interfaz
    this.w = 0;
    this.h = 0;
    
    
    this.get_size = function(){
        this.w = $('html').width() -15;
        this.h = $('html').height() - $('#box-navbar').height() - 10;
    };
    this.resize = function(){
        this.get_size();
        this.r_map();
    };
    this.r_map = function(){
        // da el tamaño al mapa
        $('#box-map').width(this.w);
        $('#box-map').height(this.h);
    };
}

function get_ajax(url,callback,iscache,isget,isasync){
	// ! Manda llamar un ajax obtiene su respuesta y si es done manda llamar una funcion dada
	var return__ = 'fallo|';
	var aux_url = url.split('?');
	var iscache_ = (iscache == undefined) ? false: iscache;				// si tomara la respuesta del cache
	var isget_ = (isget == undefined) ? "POST": isget;					// si el metodo sera get
	var isasync_ = (isasync == undefined) ? true : isasync;				// si sera asincrona la llamada
	if(callback == 'return__'){
		isasync_ = false;
	}
	$.ajax({
		url: aux_url[0],
		data: aux_url[1],
		dataType: "html",
		async: isasync_,
		cache:iscache_,
		type: isget_,
		beforeSend: function(){
            $('#generar').html('Generando...');
		},
		success: function(txt){
		    $('#generar').html('Generar');
			if(callback != 'null'){
				if(callback != 'return__'){
					eval(callback + '(\''+txt+'\')');
				}else{
					return__ = txt;
				}
			}
        },
		error: function(txt, info, otro){
            alert('error: ' + otro);
		}
	});
	return return__;
}
