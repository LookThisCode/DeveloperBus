package com.alow.service.crud.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alow.dao.AlowDao;
import com.alow.dao.DaoFactory;
import com.alow.exception.DaoException;
import com.alow.exception.EntityExistsException;
import com.alow.model.Alow;
import com.alow.model.Pessoa;
import com.alow.service.crud.CrudServiceAbs;
import com.alow.service.crud.util.CrudUtil;
import com.alow.service.impl.PessoaServiceImpl;
import com.alow.validacao.AlowVerifier;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;

public class AlowCrudServiceImpl extends CrudServiceAbs<Alow,Long>{
	
	@Override
	public AlowDao getDao() {
		return DaoFactory.getAlowDao();
	}

	@Override
	public String isValid(Alow ent) {
		return AlowVerifier.isValid(ent);
	}
	
	@Override
	public List<CrudUtil<Alow>> getCrudUtilExistList(Alow ent){
		List<CrudUtil<Alow>> retorno = new ArrayList<CrudUtil<Alow>>();
		return retorno;
	}

	@Override
	public String hasDependencies(Alow ent){
		String retorno = "";
		return retorno;
	}

	@Override
	public Long getId(Alow ent) {
		if(ent != null){
			return ent.getId();
		}
		return null;
	}
	
	@Override
	public Alow save(Alow ent) throws DaoException,	EntityExistsException {
		//ent.setDataTexto(DateUtil.convertDateToString(ent.getData()));
		//ent.setHoraTexto(DateUtil.convertHourToString(ent.getHora()));
		PessoaServiceImpl pessoaService = new PessoaServiceImpl();
		Pessoa pessoa = pessoaService.getCrud().getDao().getByProperty("googleUserId", ent.getPessoaId().toString());
		Alow retorno = super.save(ent);
		
		/*GrupoService grupoService = new GrupoServiceImpl();
		Grupo grupo = grupoService.getCrud().getDao().get(ent.getGrupoId());
		grupoService.get
		
		String devices[] = (String[]) pessoa.getDevices().toArray();
		*/
		String dev[] = {"APA91bGL4SxBUmCE_kQLvhrQRvVLgZL56H3W1bqPKMdNelOkU2DlMA3jsjWII1rwK15J72hrP6bSFcu-vfhosjJoFBJ2amyVMRFzOSwtKeIExTlzvBBEFYAfQ_QBg8dABitu2NA7D7xki0Irq9t12-fdchfrxmnfgA"};
		//String dev[] = {"APA91bHCZFd4btlYzkpF5yPQ0iF-8Q5VReXr5MpFTVFAzayi-bsRPfDVbWkWPZQoNX99vCCppCDpbQy352bkMWkK-xzUJfArHyWz_VdiyhSGwULg8YB_lhZyzgXM2-1vi3Cmj_8YF8IZ0Mb7gqlKcEgUtKMvN3z9kw"};
		try {
			sendMessage(dev, ent.getTexto(), "Jose Inacio Silva Junior", "http://alowdevbus.appspot.com/img/photo.jpg");
		} catch (IOException e) {
			e.printStackTrace();
			//TODO tratar
		}
		
		return retorno;
	}
	
	
	
	/*public int sendMessage2(String[] ids, String message, String user, String photo) throws IOException {

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
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		
		byte[] postData = formData.toString().getBytes("UTF-8");
		URL url = new URL("https://android.googleapis.com/gcm/send");
		
		HTTPResponse response = fetcher.fetch(url);
		fetcher.
		byte[] content = response.getContent();
        // if redirects are followed, this returns the final URL we are redirected to
		URL finalUrl = response.getFinalUrl();

		// 200, 404, 500, etc
		int responseCode = response.getResponseCode();
		
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization",
				"key=AIzaSyAgv4fGyY5oD4JX_imltdG7gHDfcfakMU4");

		

		//System.out.println("RESULT:" + responseCode);

		
		System.out.println("Response code: " + responseCode);
		return responseCode;
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
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
	
	
		 
	 
	    public  HTTPRequest makePostRequest(String surl, String body){
	        HTTPRequest request = null;
	        URL url;
	        try {
	            url = new URL(surl);
	            request = new HTTPRequest(url, HTTPMethod.POST);
	            
	            request.setPayload(body.getBytes());
	 
	        } catch (MalformedURLException e) {
	            // Do nothing
	        }
	        return request;
	    }
	
}
