package com.alow.servlet.c2dm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.Alow;
import com.alow.model.Pessoa;

/**
 * Servlet implementation class SendMessageGCMServlet
 */
public class SendMessageGCMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String serverDir = getServletContext().getRealPath("/");
		System.out.println("ServerPath: " + serverDir);
		System.out.println("SendMessageServlet.doPost");

		String user = request.getParameter("username");
		String photo = request.getParameter("photo");
		String message = request.getParameter("message");
		System.out.println("message:" + message);

		Pessoa pessoa = new Pessoa();
		Alow alow = new Alow();
		alow.setTexto("1234");
		alow.setPessoaId(1l);

		PrintWriter out = response.getWriter();
		out.print("<html><body><h1>Mensagens enviadas:</h1>");
		/*
		 * Set<String> deviceKeys = devices.keySet(); String[] ids = new
		 * String[deviceKeys.size()]; int count = 0; for (String key :
		 * deviceKeys) { ids[count++] = devices.get(key); }
		 */
		List<String> deviceKeys = pessoa.getDevices();
		String[] ids = new String[deviceKeys.size()];
		int count = 0;
		for (String id : pessoa.getDevices()) {
			ids[count++] = id;
		}
		sendMessage(ids, message, user, photo);

		out.print("</body></html>");
	}

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
