package com.alow.servlet.c2dm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.Pessoa;
import com.alow.service.PessoaService;
import com.alow.service.impl.PessoaServiceImpl;

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = -6707472868138646794L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("RegistrationServlet.doPost");
			String deviceId = request.getParameter("deviceid");
			String registrationId = request.getParameter("registrationid");
			String googleUserId = request.getParameter("googleuserid");

			System.out.println("RegistrationServlet.doPost->Registrando(");
			System.out.println("deviceid=" + deviceId);
			System.out.println("registrationid=" + registrationId + ")");
			System.out.println("googleUserId=" + googleUserId + ")");

			PessoaService service = new PessoaServiceImpl();
			Pessoa pessoa = service.generateOrUpdatePessoa(googleUserId, deviceId, registrationId);
			if (pessoa == null) {
				response.sendError(500, "Nao retorno a pessoa");
			}
		} catch (Exception e) {
			response.sendError(500, e.getMessage());
		}
	}
}
