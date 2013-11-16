package com.bestplacemobile.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import android.util.Log;

public class DateUtils {

	public static String setDateFormat(String date){
		String str = removeTimeZone(date);
		
		String strData = null;
		TimeZone tzUTC = TimeZone.getTimeZone("UTC");
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
		formatoEntrada.setTimeZone(tzUTC);
		SimpleDateFormat formatoSaida = new SimpleDateFormat("EEE, dd/MM/yy, HH:mm");
		
		try {
			strData = formatoSaida.format(formatoEntrada.parse(str));
		} catch (ParseException e) {
		Log.e("Erro parser data", Log.getStackTraceString(e));
		}
		return strData;
	}
	
	public static String removeTimeZone(String data){
		// busca na string e remove o padrão " (+ ou -)dddd" Ex: " +3580"
		return data.replaceFirst("(\\s[+|-]\\d{4})", "");
	}
}
