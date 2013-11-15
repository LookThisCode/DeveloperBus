package co.com.challenge.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.com.challenge.rest.model.CiudadDeveloperBusDTO;

/**
 * 
 * Controlador de Ciudades
 * @author rachirib@gmail.com
 * @project sample-rest
 * @class CiudadesRestController
 * @date 14/11/2013
 *
 */
@Controller
@RequestMapping("/ciudad")
public class CiudadesRestController {
	
	/**
	 * Metodo del controlador para consultar las ciudades 
	 * @author rachirib@gmail.coms
	 * @date 24/09/2013
	 * @return
	 * @throws AppBaseException
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CiudadDeveloperBusDTO> consultarTodoCiudadDeveloperBusDTO() {
		List<CiudadDeveloperBusDTO> lista = new ArrayList<CiudadDeveloperBusDTO>();
		
		lista.add(new CiudadDeveloperBusDTO("BO", "Bogotá"));
		lista.add(new CiudadDeveloperBusDTO("BU", "Buenos Aires"));
		lista.add(new CiudadDeveloperBusDTO("MX", "Mexico D.F."));
		lista.add(new CiudadDeveloperBusDTO("SA", "Sao Paulo"));
		
		return lista;
		
	}
}
