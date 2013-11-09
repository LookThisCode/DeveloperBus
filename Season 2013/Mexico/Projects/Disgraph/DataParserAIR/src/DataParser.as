package
{
	import flash.display.MovieClip;
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLLoader;
	import flash.ui.Keyboard;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	
	
	public class DataParser extends MovieClip
	{
		protected var results:Dictionary = new Dictionary();
		protected var resultString:String = "";
		protected var fileSplittedCount:int = 1;
		protected var fileName:String;
		protected var char1:uint = Keyboard.A;
		protected var char2:uint = Keyboard.A;//Este cambia cada vez
		
		public function DataParser()
		{
			//loadFile("espanol.txt");
			//subdivideFile("espanol.txt",2000);
			
			addEventListener(Event.ENTER_FRAME,onEnterFrame);
		}
		
		
		protected function onEnterFrame(e:Event):void
		{
			results = new Dictionary();
			resultString = "";
			
			//cargamos el archivo
			fileName = "splitted_data/espanol_split"+String.fromCharCode(char1).toLowerCase()+String.fromCharCode(char2).toLowerCase();
			loadFile(fileName);
			
			
			if(Keyboard.Z == char2)
			{
				char1++;
			}
			else
			{
				char2++;
			}
			
			if(char1 == Keyboard.J && char2 == Keyboard.B)
			{
				removeEventListener(Event.ENTER_FRAME,onEnterFrame);			
			}
		}
		
		protected function subdivideFile(name:String,lines:int):void
		{
			var data:String = "";
			var tmp:String;
			var i:int = 0;
			var f:int = 0;
			var pos:int = 0
			var loader:URLLoader = new URLLoader(); //Creating a loader object
			loader.addEventListener(Event.COMPLETE, function(e:Event)
			{
				var pos:int = 0;
				do{
					tmp = String(e.target.data).substring(pos,String(e.target.data).indexOf("\n",pos));
					pos += String(e.target.data).indexOf("\n",pos)+1;
					
					if(tmp != "")
					{
						data += tmp + "\n";
					}
					
					if(++i >= lines)
					{
						//Grabamos
						var file:File = File.desktopDirectory.resolvePath("learning_data/learning_data"+(f++)+".txt");
						var ws:FileStream;
						
						ws.open(file,FileMode.WRITE);
						ws.writeUTFBytes(data);
						ws.close();
						trace("save");
					}
				}while(tmp != "");	
			});
			
			/*var file:File = File.desktopDirectory.resolvePath(name);
			
			if(file.exists)
			{
				var f_stream:FileStreamWithLineReader = new FileStreamWithLineReader();
				f_stream.open(file,FileMode.READ);
				var ws:FileStream = new FileStream();
				
				var data:String = "";
				var tmp:String;
				var i:int = 0;
				var f:int = 0;
				var pos:int = 0
				do
				{
					f_stream.position = 12;
					tmp = f_stream.readUTFLine();
					var ba:ByteArray = new ByteArray();
					ba.writeUTF(tmp);
					
					pos += ba.length+2;
					 
					data += tmp + "\n";
					if(++i >= lines)
					{
						//Grabamos
						file = File.desktopDirectory.resolvePath("learning_data/learning_data"+(f++)+".txt");
						
						ws.open(file,FileMode.WRITE);
						ws.writeUTFBytes(data);
						ws.close();
						trace("save");
					}
				}while(tmp != "");
				
				f_stream.close();
				
				trace("done");
			}*/	
		}
		
		protected function loadFile(name:String):void
		{
			var file:File = File.desktopDirectory.resolvePath(name);
			
			if(file.exists)
			{
				var f_stream:FileStream = new FileStream();
				f_stream.open(file,FileMode.READ);
				//todo a una cadena parseable
				trace("parsing:",fileName);
				var byteArray:ByteArray = new ByteArray();
				var data:String;
				f_stream.readBytes(byteArray);
				data = byteArray.readUTFBytes(byteArray.bytesAvailable);
				f_stream.close();
				
				//Data como arreglo
				data.replace("\r","");
				var a_data:Array = data.split("\n");
				trace("Lineas originales:",a_data.length);
				
				//removeSpecialCharacters(["áéíóú äëïöü âêîôû àèìòù áéíóú äëïöü âêîôû àèìòù"]);
				removeSpecialCharacters(a_data);
				
				parseHotChars(a_data);
				
				/*for each(var obj:Object in results)
				{
					trace(JSON.stringify(obj));
				}*/
				
				//Sin retorno de carro
				resultString = resultString.replace(/\r/g,"");
				trace("Lineas:",resultString.split("\n").length);
				//Guardamos el archivo
				file = File.desktopDirectory.resolvePath("learning_data/"+fileName+".txt");
				
				f_stream.open(file,FileMode.WRITE);
				f_stream.writeUTFBytes(resultString);
				f_stream.close();
				
				
				trace("*********DONE********");
			}
		}
		
		protected function parseHotChars(data:Array):void
		{
			var tmp:Array = [];
			var result:Object;
			
			//Ambiguedad fonema-grafema
			for(var i:int = 0; i < data.length; i++)
			{
				//b-v
				tmp = searchAndReplace(data[i],RegExp(/b/),"v");
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/v/),"b"));
				
				//c-z
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/c/),"z"));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/z/),"c"));
				
				//c-k
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/c/),"k"));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/k/),"c"));
				
				//g-j
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/g/),"j"));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/j/),"g"));
				
				//ll-y
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ll/),"y"));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/y/),"ll"));
				
				//r-rr
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/rr/),"r",2));
				
				//h
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ha/),"ah",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/he/),"eh",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/hi/),"ih",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ho/),"oh",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/hu/),"uh",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ah/),"ha",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/eh/),"he",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ih/),"hi",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/oh/),"ho",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/uh/),"hu",2));
				
				//el la lo los las les
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/la/),"al",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/al/),"la",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/le/),"el",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/el/),"le",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/il/),"li",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/li/),"il",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/lo/),"ol",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ol/),"lo",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/lu/),"ul",2));
				tmp = tmp.concat(searchAndReplace(data[i],RegExp(/ul/),"lu",2));
				
				//tmp debe de contener las variantes ya reemplazadas
				if(tmp.length > 0)
				{
					result = {correct:data[i]};
					for(var j:int = 0; j < tmp.length; j++)
					{
						result["error"+j] = tmp[j];
						resultString += '"'+data[i]+'","'+tmp[j]+'",0,"a"\n';
					}
					
					results[data[i]] = result;
				}	
			}
		}
		
		protected function searchAndReplace(target:String,search:RegExp,replace:String,searchLength:int = 1):Array
		{
			var resultIndex:int = 0;
			var tmp:Array = [];
			var clone:String = target.concat();
			var pairs:Array= [];
			var complexPairs:Array = [];
			var s_tmp:String;
			
			search.lastIndex = resultIndex;
			
			while(search.test(clone))
			{
				resultIndex = clone.search(search);
				
				//replace único
				s_tmp = replaceAt(target,resultIndex,replace,searchLength);
				
				//Se agrega como par
				pairs.push({value:s_tmp,index:resultIndex});
				
				//replace acumulado en clone para la iteración
				clone = clone.replace(search,replace);
			}
			
			var count:int = 2;
			
			//Ya tenemos dentro de pairs un reemplazo individual, hay que combinarlos
			while(count <= pairs.length)
			{
				var clength:int = 1;				
				for(var now:int = 0; now < pairs.length; now++)
				{
					s_tmp = String(pairs[now].value).concat();
					clength = 1;
					for(var i:int = now+1;i<pairs.length;i++)
					{
						s_tmp = replaceAt(s_tmp,pairs[i].index,replace,searchLength);
						
						if(++clength == count)
						{
							//Sacamos un nuevo par
							complexPairs.push(s_tmp.concat());
							s_tmp = String(pairs[now].value).concat();
							clength = 1;
						}
					}
				}	
				
				count++;
			}
			
			//Armamos el result
			for(i = 0; i < pairs.length; i++)
			{
				tmp.push(pairs[i].value);
			}
			
			for(i= 0; i < complexPairs.length; i++)
			{
				tmp.push(complexPairs[i]);			
			}
			
			
			return tmp;
		}
		
		protected function replaceAt(target:String,at:int,replace:String,length:int):String
		{
			var s_tmp:String;
			
			//es el caracter 0
			if(at == 0)
			{
				s_tmp = replace + target.substring(length);
			}
			else
			{
				//Intermedio
				s_tmp = target.substring(0,at)+replace+target.substring(at+length);
			}
			
			return s_tmp;
		}
		
		protected function removeSpecialCharacters(data:Array):void
		{
			//Eliminando:áéíóú äëïöü âêîôû àèìòù
			var regExpA:RegExp = /[áäâà]/g;
			var regExpE:RegExp = /[éëêè]/g;
			var regExpI:RegExp = /[íïîì]/g;
			var regExpO:RegExp = /[óöôò]/g;
			var regExpU:RegExp = /[úüûù]/g;
			
			for(var i:int = 0; i< data.length;i++)
			{
				data[i] = String(data[i]).toLowerCase();
				data[i] = String(data[i]).replace(regExpA,"a");
				data[i] = String(data[i]).replace(regExpE,"e");
				data[i] = String(data[i]).replace(regExpI,"i");
				data[i] = String(data[i]).replace(regExpO,"o");
				data[i] = String(data[i]).replace(regExpU,"u");
			}
		}
		
		protected function evaluateAndStorage():void
		{
			var regExp:RegExp = /[b+]/;
			
			
		}
	}
}