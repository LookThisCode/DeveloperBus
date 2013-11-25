package com.alow.validacao;

import com.alow.model.Alow;

public class AlowVerifier {
	private static final Integer textoMaxLength = 400;
	
	public static String isValid(Alow ent){
		String retorno = "";
		
		// Objeto nulo
		if (ent == null){
			retorno = "Comportamento inesperado. Valor nulo para o Alow.";
			return retorno;
		}
		
		// Nome
		if(ent.getTexto() == null || ent.getTexto().equals("")){
			retorno = "O texto do alow deve ser preenchido.";
			return retorno;
		}else{
			if (ent.getTexto().length() > textoMaxLength){
				retorno = "O texto do alow deve ter no m�ximo "+textoMaxLength+" caracteres.";
				return retorno;
			}
		}
		
		/*// Descricao
		if(ent.getDescricao() == null || ent.getDescricao().equals("")){
			retorno = "A descrição deve ser preenchida.";
			return retorno;
		}else{
			if (ent.getDescricao().length() > 500){
				retorno = "A descrição deve conter no máximo 300 caracteres.";
				return retorno;
			}
		}
		
		// Data
		if(ent.getData() == null){
			retorno = "Por favor, preencha o campo data.";
			return retorno;
		}
		
		// Hora
		if(ent.getHora() == null){
			retorno = "Por favor, preencha o campo hora.";
			return retorno;
		}
		*/
		/*// Local
		if(ent.getLocal() == null || bb.getLocal().equals("")){
			retorno = "O local deve ser preenchido.";
			return retorno;
		}else{
			if (bb.getLocal().length() > 50){
				retorno = "O local deve conter no máximo 50 caracteres.";
				return retorno;
			}
		}
		
		// Estado / Cidade
		if(bb.getEstadoId() == null || bb.getEstadoId() == 0l){
			retorno = "O estado é obrigatório";
			return retorno;
		}
		
		if(bb.getCidadeId() == null || bb.getCidadeId() == 0l){
			retorno = "A cidade é obrigatória";
			return retorno;
		}
		
		// Tem Vagas
		if (bb.getTemVagas() == null){
			bb.setTemVagas(false);
		}
		
		// Gratuito
		if (bb.getGratuito() == null){
			bb.setGratuito(false);
		}*/
		
		return retorno;
	}
	
}
