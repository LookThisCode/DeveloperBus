package com.alow.service.crud.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.alow.dao.DaoFactory;
import com.alow.dao.PessoaDao;
import com.alow.model.Pessoa;
import com.alow.service.crud.CrudServiceAbs;
import com.alow.service.crud.util.CrudUtil;
import com.alow.validacao.PessoaVerifier;

public class PessoaCrudServiceImpl extends CrudServiceAbs<Pessoa,Long>{
	/*private ControladoraDao controladoraDao = DaoFactory.getControladoraDao();
	private PortaDao portaDao = DaoFactory.getPortaDao();
	private UsuarioDao usuarioDao = DaoFactory.getUsuarioDao();*/
	
	@Override
	public PessoaDao getDao() {
		return DaoFactory.getPessoaDao();
	}

	@Override
	public String isValid(Pessoa ent) {
		return PessoaVerifier.isValid(ent);
	}
	
	@Override
	public List<CrudUtil<Pessoa>> getCrudUtilExistList(Pessoa ent){
		List<CrudUtil<Pessoa>> retorno = new ArrayList<CrudUtil<Pessoa>>();
		/*CrudUtil<Pessoa> crudUtilNome = new CrudUtil<Pessoa>();
		crudUtilNome.setLista(getDao().getBateBolasDoUsuarioPorNome(ent));
		crudUtilNome.setMensagem("Já existe um Bate-Bola com o nome "+ent.getNome());
		retorno.add(crudUtilNome);*/
		return retorno;
	}

	@Override
	public String hasDependencies(Pessoa ent){
		String retorno = "";
		/*ArrayList<ElementoConfig> ents = getElementosConfigDoElemento(ent.getId());
		if(ents != null && ents.size() > 0){
			retorno = "Háï¿½ ao menos uma configuraï¿½ï¿½o para esse Dispositivo";
		}*/
		return retorno;
	}

	@Override
	public Long getId(Pessoa ent) {
		if(ent != null){
			return ent.getId();
		}
		return null;
	}
	
	/*@Override
	public Pessoa save(Pessoa ent) throws DaoException,	EntityExistsException {
		//ent.setDataTexto(DateUtil.convertDateToString(ent.getData()));
		//ent.setHoraTexto(DateUtil.convertHourToString(ent.getHora()));
		return super.save(ent);
	}*/
	
	public int sendMessage(String[] ids, String message, String user, String photo) throws IOException {

		String stringIds = "";
		for (int i = 0; i < ids.length; i++) {
			if (i == 0) {
				stringIds = "\"" + ids[i] + "\"";
			} else {
				stringIds = ",\"" + ids[i] + "\"";
			}
		}

		String formData = "{ \"data\": { \"message\" : \"" + message + "\", "
				+ "\"sender\":\"" + user + "\"," + "\"photo\":\"" + photo
				+ "\"," + "\"time\": " + System.currentTimeMillis() + " }"
				+ "\"registration_ids\": [" + stringIds + "] }";

		System.out.println("FORM-DATA: " + formData);

		byte[] postData = formData.toString().getBytes("UTF-8");
		URL url = new URL("https://android.googleapis.com/gcm/send");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization",
				"key=AIzaSyAgv4fGyY5oD4JX_imltdG7gHDfcfakMU4");

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		String string = toString(conn.getInputStream());

		System.out.println("RESULT:" + string);

		int responseCode = conn.getResponseCode();
		System.out.println("Response code: " + responseCode);
		return responseCode;
	}

	private String toString(InputStream is) throws IOException {

		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}
}
