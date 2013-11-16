package com.bestplacemobile.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bestplacemobile.models.ItemDistribuidor;
import com.bestplacemobile.utils.ConstantsUtils;

import android.util.Log;

public class LDistribuidores {

	public static String TAG = "LOG_DIST";

	public static ArrayList<ItemDistribuidor> ObtenerDistribuidores() {
		ArrayList<ItemDistribuidor> itemsDist = new ArrayList<ItemDistribuidor>();

		HttpURLConnection httpConnection = null;
		BufferedReader bufferedReader = null;
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL(ConstantsUtils.URL_SEARCH);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");

			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			httpConnection.connect();

			bufferedReader = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				response.append(line);
			}

			Log.d(TAG,
					"GET response code: "
							+ String.valueOf(httpConnection.getResponseCode()));
			Log.d(TAG, "JSON response: " + response.toString());

			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONArray jsonArray = jsonResponse.getJSONArray("distribuidores");
			JSONObject object;
			ItemDistribuidor distribuidor;

			for (int i = 0; i < jsonArray.length(); i++) {
				object = (JSONObject) jsonArray.get(i);
				distribuidor = new ItemDistribuidor();

				distribuidor.setId(Integer.parseInt(object.getString("id")));
				distribuidor.setNIT(object.getString("nit_cedula"));
				distribuidor.setNombreCompania(object.getString("nombre"));
				distribuidor.setNombreRepLegal(object
						.getString("nombre_representante_legal"));
				distribuidor.setUrlLogo(object.getString("logo_o_foto"));
				distribuidor.setCalificacion(object.getString("calificacion"));

				distribuidor.setNumeroComentarios(Integer.parseInt(object
						.getString("calificacion")));

				itemsDist.add(i, distribuidor);
			}

		} catch (Exception e) {
			Log.e(TAG, "GET error: " + Log.getStackTraceString(e));
			return null;

		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}

		return itemsDist;
	}
}
