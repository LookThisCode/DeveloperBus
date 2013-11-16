package co.com.agronome.proveedores.spi;

import java.util.List;

import javax.inject.Named;

import co.com.agronome.proveedores.modelo.Proveedor;
import co.com.agronome.proveedores.spi.model.ProveedorDTO;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * Consulta de Proveedores por API
 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
 * @project agronome
 * @class ProveedorAPI
 * @date 15/11/2013
 *
 */
@Api(
	    name = "agronome",
	    version = "v1",
	    clientIds = {Ids.WEB_CLIENT_ID, Ids.ANDROID_CLIENT_ID, Ids.IOS_CLIENT_ID},
	    audiences = {Ids.ANDROID_AUDIENCE}
	)
public class ProveedorAPI {
	
	
	/**
	 * Consulta de Tag 
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param tagName
	 * @return
	 */
	@ApiMethod(name = "proveedor.consultarproveedortag", httpMethod = "POST")
	public List<ProveedorDTO> consultarProveedorPorTag(@Named("tagname") String tagName){
		return Proveedor.consultarProveedoresAPI(tagName);
	}
	/**
	 * Consulta de Proveedor por usuario
	 * @author <a href="mailto:rachirib@gmail.com">Ricardo Alberto Chiriboga</a>
	 * @date 15/11/2013
	 * @param usuario
	 * @return
	 */
	@ApiMethod(name = "proveedor.consultarproveedorporusuario", httpMethod = "POST")
	public ProveedorDTO consultarProveedorPorUsuario(@Named("usuario") String usuario){
		return Proveedor.consultarProveedorPorUsuario(usuario);
	}

}
