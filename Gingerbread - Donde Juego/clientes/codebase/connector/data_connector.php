<?php
/*
This software is allowed to use under GPL or you need to obtain Commercial or Enterise License
to use it in non-GPL project. Please contact sales@dhtmlx.com for details
*/
?><?php
/*
	@author dhtmlx.com
	@license GPL, see license.txt
*/
require_once("base_connector.php");

class CommonDataProcessor extends DataProcessor{
	protected function get_post_values($ids){
		if (isset($_GET['action'])){
			$data = array();
			if (isset($_POST["id"])){
				$dataset = array();
				foreach($_POST as $key=>$value)
					$dataset[$key] = ConnectorSecurity::filter($value);

				$data[$_POST["id"]] = $dataset;
			}
			else
				$data["dummy_id"] = $_POST;
			return $data;
		}
		return parent::get_post_values($ids);
	}
	
	protected function get_ids(){
		if (isset($_GET['action'])){
			if (isset($_POST["id"]))
				return array($_POST['id']);
			else
				return array("dummy_id");
		}
		return parent::get_ids();
	}
	
	protected function get_operation($rid){
		if (isset($_GET['action']))
			return $_GET['action'];
		return parent::get_operation($rid);
	}
	
	public function output_as_xml($results){
		if (isset($_GET['action'])){
			LogMaster::log("Edit operation finished",$results);
			ob_clean();
			$type = $results[0]->get_status();
			if ($type == "error" || $type == "invalid"){
				echo "false";
			} else if ($type=="insert"){
				echo "true\n".$results[0]->get_new_id();
			} else 
				echo "true";
		} else
			return parent::output_as_xml($results);
	}
};

/*! DataItem class for DataView component
**/
class CommonDataItem extends DataItem{
	/*! return self as XML string
	*/
	function to_xml(){
		if ($this->skip) return "";
		return $this->to_xml_start().$this->to_xml_end();
	}

	function to_xml_start(){
		$str="<item id='".$this->get_id()."' ";
		for ($i=0; $i < sizeof($this->config->text); $i++){ 
			$name=$this->config->text[$i]["name"];
			$str.=" ".$name."='".$this->xmlentities($this->data[$name])."'";
		}

		if ($this->userdata !== false)
			foreach ($this->userdata as $key => $value)
				$str.=" ".$key."='".$this->xmlentities($value)."'";

		return $str.">";
	}
}


/*! Connector class for DataView
**/
class DataConnector extends Connector{

	/*! constructor
		
		Here initilization of all Masters occurs, execution timer initialized
		@param res 
			db connection resource
		@param type
			string , which hold type of database ( MySQL or Postgre ), optional, instead of short DB name, full name of DataWrapper-based class can be provided
		@param item_type
			name of class, which will be used for item rendering, optional, DataItem will be used by default
		@param data_type
			name of class which will be used for dataprocessor calls handling, optional, DataProcessor class will be used by default. 
	*/	
	public function __construct($res,$type=false,$item_type=false,$data_type=false,$render_type=false){
		if (!$item_type) $item_type="CommonDataItem";
		if (!$data_type) $data_type="CommonDataProcessor";

		$this->sections = array();

		if (!$render_type) $render_type="RenderStrategy";
		parent::__construct($res,$type,$item_type,$data_type,$render_type);

	}

	protected $sections;
	public function add_section($name, $string){
		$this->sections[$name] = $string;
	}

	protected function parse_request_mode(){
		//do nothing, at least for now
	}
	
	//parse GET scoope, all operations with incoming request must be done here
	protected function parse_request(){
		if (isset($_GET['action'])){
			$action = $_GET['action'];
			//simple request mode
			if ($action == "get"){
				//data request
				if (isset($_GET['id'])){
					//single entity data request
					$this->request->set_filter($this->config->id["name"],$_GET['id'],"=");
				} else {
					//loading collection of items
				}
			} else {
				//data saving
				$this->editing = true;
			}
		} else {
			if (isset($_GET['editing']) && isset($_POST['ids']))
				$this->editing = true;			
			
			parent::parse_request();
		}
	
		if (isset($_GET["start"]) && isset($_GET["count"]))
			$this->request->set_limit($_GET["start"],$_GET["count"]);

	}
	
	/*! renders self as  xml, starting part
	*/
	protected function xml_start(){
		$start = "<data";
		foreach($this->attributes as $k=>$v)
			$start .= " ".$k."='".$v."'";
		$start.= ">";

		foreach($this->sections as $k=>$v)
			$start .= "<".$k.">".$v."</".$k.">\n";
		return $start;
	}
};

class JSONDataConnector extends DataConnector{

	public function __construct($res,$type=false,$item_type=false,$data_type=false){
		if (!$item_type) $item_type="JSONCommonDataItem";
		if (!$data_type) $data_type="CommonDataProcessor";
		$this->data_separator = ",\n";
		parent::__construct($res,$type,$item_type,$data_type);
	}

	/*! assign options collection to the column
		
		@param name 
			name of the column
		@param options
			array or connector object
	*/
	public function set_options($name,$options){
		if (is_array($options)){
			$str=array();
			foreach($options as $k => $v)
				$str[]='{"id":"'.$this->xmlentities($k).'", "value":"'.$this->xmlentities($v).'"}';
			$options=implode(",",$str);
		}
		$this->options[$name]=$options;
	}

	/*! generates xml description for options collections
		
		@param list 
			comma separated list of column names, for which options need to be generated
	*/
	protected function fill_collections(){
		$options = array();
		foreach ($this->options as $k=>$v) { 
			$name = $k;
			$option="\"{$name}\":[";
			if (!is_string($this->options[$name]))
				$option.=substr($this->options[$name]->render(),0,-2);
			else
				$option.=$this->options[$name];
			$option.="]";
			$options[] = $option;
		}
		$this->extra_output .= implode($this->data_separator, $options);
	}

	protected function resolve_parameter($name){
		if (intval($name).""==$name)
			return $this->config->text[intval($name)]["db_name"];
		return $name;
	}

	protected function output_as_xml($res){
		$start = "";
		$end = "{ \"data\":[\n".substr($this->render_set($res),0,-2)."\n]";

		$collections = $this->fill_collections();
		if (!empty($this->extra_output))
			$end .= ', "collections": {'.$this->extra_output.'}';


		$is_sections = sizeof($this->sections) && $this->is_first_call();
		if ($this->dload || $is_sections || sizeof($this->attributes)){
			$start = $start.$end;
			$end="";

			$attributes = "";
			foreach($this->attributes as $k=>$v)
				$end .= ", ".$k.":\"".$v."\"";

			if ($is_sections){
				//extra sections
				foreach($this->sections as $k=>$v)
					$end.= ", ".$k.":".$v;
			}

			if ($this->dload){
				//info for dyn. loadin
				if ($pos=$this->request->get_start())
					$end .= ", \"pos\":".$pos;
				else
					$end .= ", \"pos\":0, \"total_count\":".$this->sql->get_size($this->request);
			}
		}
		$end .= " }";
		$out = new OutputWriter($start, $end);
		$out->set_type("json");
		$this->event->trigger("beforeOutput", $this, $out);
		$out->output("", true, $this->encoding);
	}
}

class JSONCommonDataItem extends DataItem{
	/*! return self as XML string
	*/
	function to_xml(){
		if ($this->skip) return "";
		
		$data = array(
			'id' => $this->get_id()
		);
		for ($i=0; $i<sizeof($this->config->text); $i++){
			$extra = $this->config->text[$i]["name"];
			$data[$extra]=$this->data[$extra];
		}

		if ($this->userdata !== false)
			foreach ($this->userdata as $key => $value)
				$data[$key]=$value;

		return json_encode($data);
	}
}


/*! wrapper around options collection, used for comboboxes and filters
**/
class JSONOptionsConnector extends JSONDataConnector{
	protected $init_flag=false;//!< used to prevent rendering while initialization
	public function __construct($res,$type=false,$item_type=false,$data_type=false){
		if (!$item_type) $item_type="JSONCommonDataItem";
		if (!$data_type) $data_type=""; //has not sense, options not editable
		parent::__construct($res,$type,$item_type,$data_type);
	}
	/*! render self
		process commands, return data as XML, not output data to stdout, ignore parameters in incoming request
		@return
			data as XML string
	*/	
	public function render(){
		if (!$this->init_flag){
			$this->init_flag=true;
			return "";
		}
		$res = $this->sql->select($this->request);
		return $this->render_set($res);
	}
}


class JSONDistinctOptionsConnector extends JSONOptionsConnector{
	/*! render self
		process commands, return data as XML, not output data to stdout, ignore parameters in incoming request
		@return
			data as XML string
	*/	
	public function render(){
		if (!$this->init_flag){
			$this->init_flag=true;
			return "";
		}
		$res = $this->sql->get_variants($this->config->text[0]["db_name"],$this->request);
		return $this->render_set($res);
	}
}



class TreeCommonDataItem extends CommonDataItem{
	protected $kids=-1;

	function to_xml_start(){
		$str="<item id='".$this->get_id()."' ";
		for ($i=0; $i < sizeof($this->config->text); $i++){ 
			$name=$this->config->text[$i]["name"];
			$str.=" ".$name."='".$this->xmlentities($this->data[$name])."'";
		}
		
		if ($this->userdata !== false)
			foreach ($this->userdata as $key => $value)
				$str.=" ".$key."='".$this->xmlentities($value)."'";

		if ($this->kids === true)
			$str .=" dhx_kids='1'";
		
		return $str.">";
	}

	function has_kids(){
		return $this->kids;
	}

	function set_kids($value){
		$this->kids=$value;
	}
}


class TreeDataConnector extends DataConnector{
	protected $parent_name = 'parent';

	/*! constructor
		
		Here initilization of all Masters occurs, execution timer initialized
		@param res 
			db connection resource
		@param type
			string , which hold type of database ( MySQL or Postgre ), optional, instead of short DB name, full name of DataWrapper-based class can be provided
		@param item_type
			name of class, which will be used for item rendering, optional, DataItem will be used by default
		@param data_type
			name of class which will be used for dataprocessor calls handling, optional, DataProcessor class will be used by default. 
	 *	@param render_type
	 *		name of class which will provides data rendering
	*/	
	public function __construct($res,$type=false,$item_type=false,$data_type=false,$render_type=false){
		if (!$item_type) $item_type="TreeCommonDataItem";
		if (!$data_type) $data_type="CommonDataProcessor";
		if (!$render_type) $render_type="TreeRenderStrategy";
		parent::__construct($res,$type,$item_type,$data_type,$render_type);
	}

	//parse GET scoope, all operations with incoming request must be done here
	protected function parse_request(){
		parent::parse_request();

		if (isset($_GET[$this->parent_name]))
			$this->request->set_relation($_GET[$this->parent_name]);
		else
			$this->request->set_relation("0");

		$this->request->set_limit(0,0); //netralize default reaction on dyn. loading mode
	}

	/*! renders self as  xml, starting part
	*/
	protected function xml_start(){
		return "<data parent='".$this->request->get_relation()."'>";
	}	
}


class JSONTreeDataConnector extends TreeDataConnector{

	public function __construct($res,$type=false,$item_type=false,$data_type=false,$render_type){
		if (!$item_type) $item_type="JSONTreeCommonDataItem";
		if (!$data_type) $data_type="CommonDataProcessor";
		if (!$render_type) $render_type="JSONTreeRenderStrategy";
		parent::__construct($res,$type,$item_type,$data_type,$render_type);
	}

	protected function output_as_xml($res){
		$data = array();
		$data["parent"] = $this->request->get_relation();
		$data["data"] = $this->render_set($res);
		$out = new OutputWriter(json_encode($data), "");
		$out->set_type("json");
		$this->event->trigger("beforeOutput", $this, $out);
		$out->output("", true, $this->encoding);
	}

}


class JSONTreeCommonDataItem extends TreeCommonDataItem{
	/*! return self as XML string
	*/
	function to_xml_start(){
		if ($this->skip) return "";
		
		$data = array( "id" => $this->get_id() );
		for ($i=0; $i<sizeof($this->config->text); $i++){
			$extra = $this->config->text[$i]["name"];
			$data[$extra]=$this->data[$extra];
		}

		if ($this->userdata !== false)
			foreach ($this->userdata as $key => $value)
				$data[$key]=$value;

		if ($this->kids === true)
			$data["dhx_kids"] = 1;

		return $data;
	}

	function to_xml_end(){
		return "";
	}
}


?>