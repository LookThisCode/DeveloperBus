package com.alow.validacao;

import com.alow.model.Grupo;


/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class GrupoVerifier {
	public static String isValid(Grupo ent){
		String retorno = "";
		
		if (ent == null){
			retorno = "Comportamento inesperado. Valor nulo para o grupo.";
			return retorno;
		}
		
		// Nome
		if(ent.getNome() == null || ent.getNome().equals("")){
			retorno = "O nome deve ser preenchido.";
			return retorno;
		}else{
			if (ent.getNome().length() > 60){
				retorno = "O nome deve ter no máximo 60 caracteres.";
				return retorno;
			}
		}
		
		return retorno;
		
	}
	
}
