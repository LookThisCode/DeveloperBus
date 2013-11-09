var oReq = new XMLHttpRequest();

var response = null;

var sugestions = null;

window.onready = FBInit;

//FBInit();
var app = {
	word:'',
	//url:'https://languagetool.org:8081/',
	url: 'http://localhost:8081/',
	//url: 'http://localhost:8081?language=es&text=',
	lang:'es'
};

//respuesta ortografica

var responseOrtografia = {
		orthographic:false,
		result:'',
		type:'none'
};

var responseDisgrafia = {
	disgraph:false,
	prefered:'',
	result: '',
	type:'none'
};

//respuesta disgrafia


function cleanWord(word)
{
	word = word.replace("á","a");
	word = word.replace("é","e");
	word = word.replace("í","i");
	word = word.replace("ó","o");
	word = word.replace("ú","u");
	word = word.replace("%"," ");
	word = word.replace("$"," ");
	word = word.trim();
	return word;
}

function processDisgraph(word,sugestion)
{
	var exit = {
		disgraph:false,
		prefered:'',
		type:'none'
	};

	var close = false;
	word = cleanWord(word);
	for(var i in sugestion)
	{
		var cont = 0;
		sugestion[i] = cleanWord(sugestion[i]);
		if(sugestion[i]==word)
		{
			exit.disgraph = false;
			exit.prefered = sugestion[i];
			exit.type = 'orthographic';

			close = true;
			if(close)
			{
				break;
			}
		}
		else
		{
			if(sugestion[i].length==word.length)
			{
				for(var j = 0; j < sugestion[i].length; j++)
				{
					if(word.indexOf(sugestion[i].charAt(j))!=-1)
					{
						cont++;
					}
				}
				if(cont==word.length)
				{
					exit.disgraph = true;
					exit.prefered = sugestion[i];
					exit.type = 'interchange';
					close = true;
				}
				if(close)
				{
					break;
				}
			}
			else
			{
				if(sugestion[i].length>word.length)
				{
					for(var j = 0; j < sugestion[i].length; j++)
					{
						if(word.indexOf(sugestion[i].charAt(j))!=-1)
						{
							cont++;
						}
					}
					if(cont==sugestion[i].length)
					{
						exit.disgraph = true;
						exit.prefered = sugestion[i];
						exit.type = 'omition';
						close = true;
					}
					if(close)
					{
						break;
					}
				}
			}
			if(close)
			{
				break;
			}
		}
	}
	return exit;
}

function FBInit()
{
	//alert('listo');
	var exit = '';
	var salida = '';
	
	//$(function(){alert('a')});
	document.addEventListener('keyup',function (event){
		//CUADRO DE TEXTO event.target
	    if( event.keyCode == 32 || event.keyCode == 13 || event.keyCode == 190 || event.keyCode == 188)
	    {
	    	var word = app.word.toLowerCase();
	    	
	    	if (word != null && word != " "){

		    	 $.ajax({
	            
		            type: "GET",
		            url: 'http://localhost:8081/?language=es&text='+word,
		            dataType: "xml",
		            success: function(xml){

		              var errorTag = xml.getElementsByTagName("error")[0];
		              var replacements = errorTag.getAttribute('replacements');
		              sugestions = replacements.split('#');

		              console.dir(sugestions);

		              //var response = null;
		              if (sugestions.length>0)
		              {
		              	response = processDisgraph(word,sugestions);	
		              }

		              //respuesta ortografica
		              if (response.type == "orthographic" ) {
		              	//responseOrtografia = response;
		              	responseOrtografia.result = sugestions;
		              	responseOrtografia.orthographic = true;
		              }

		              console.dir(responseOrtografia);
		              console.log (response);

		              var barra = document.getElementById('DisgraphBar');
		              //var JSONText = JSON.stringify(response);




		             /* salida = '<label><b> Disgrafico: </b></label>' + 
		              			response.disgraph + '<label><b> Sugerencias: </b></label>' + 
		              			sugestions + '<label><b> Sugerido:</b></label>' + 
		              			response.prefered;*/

		             	if (responseOrtografia.orthographic == true){

		             		salida = '<label> Error Ortografico: '+responseOrtografia.orthographic + '</label>'+
		             				'<label>Result: ' + responseOrtografia.result + '</label>';

		             		addNewLine(salida,true);
		             	}

		             	if (response.disgraph == true ){

		             		salida = '<label>Error Disgrafico</label>'+ '<br/><label><b> Sugerencias: </b></label>' + 
		              			sugestions + '<br/><label><b> Palabra Sugerida:</b></label>' + 
		              			response.prefered;
		              		clean();
		              		addNewLine(salida, false);

		             	}



		              //barra.innerHTML = salida;
		            },
		            error: function() {
		            console.log("Ocurrio un error en el parseo del archivo.");
		            }
	       		 });

	       		 app.word = '';
	       		 //fin de validacion $ y %

       		} //fin de validacion de null y vacio 

	    }
	    else
	    {
	    	app.word += String.fromCharCode(event.keyCode);
	    }
	}, false);

	var BarWindow = document.createElement('div');
	BarWindow.id = 'DisgraphBar';
	BarWindow.className = 'BottomBar bounceInDown animated';
	
	//Roberto



	var spellBox = document.createElement('div');
    spellBox.id = "spellBox";
    spellBox.className = 'text_box_left';
    var content = document.createElement('p');
    content.id = "content";
    spellBox.appendChild(content);

    var disBox = document.createElement('div');
    disBox.id = "disBox";
    disBox.className = 'text_box_right';
    var content2 = document.createElement('p');
    content2.id = "content2";
    disBox.appendChild(content2);


    BarWindow.appendChild(spellBox);
    BarWindow.appendChild(disBox);
	
	
	document.getElementsByTagName('body')[0].appendChild(BarWindow);


}

function addNewLine(line,disgraph)
{
    var doc;
 if(disgraph)   
 {
    doc = document.getElementById('content2');
 }
else
{
    doc = document.getElementById('content');    
}
    var tmp = doc.innerHTML;
    tmp += '<br>'+line;
    doc.innerHTML = tmp;
}

function clean(disgraph)
{
     var doc;
 if(disgraph)   
 {
    doc = document.getElementById('content2');
 }
else
{
    doc = document.getElementById('content');    
}
    doc.innerHTML = '';
}