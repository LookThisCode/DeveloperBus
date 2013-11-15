package com.cinesbus.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;

public class Util {

	AlertDialog dialogMessage;
	Context ctx;
	Class<?> clas;
	public int maxwidth = 1000;
	public int maxheigth = 750;

	public Util(Context ctx) {
		this.ctx = ctx;
	}

	public void dialog(int icono, String title, String msj, String ok) {
		AlertDialog.Builder warning = new AlertDialog.Builder(ctx)
				.setIcon(icono).setTitle(title).setMessage(msj)
				.setPositiveButton(ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialogMessage.dismiss();
					}

				});
		dialogMessage = warning.create();
		dialogMessage.show();
	}

	public void dialogOkClass(int icono, String title, String msj, String ok,
			Class<?> clase) {
		clas = clase;
		AlertDialog.Builder warning = new AlertDialog.Builder(ctx)
				.setIcon(icono).setTitle(title).setMessage(msj)
				.setPositiveButton(ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialogMessage.dismiss();
						Intent i = new Intent(ctx, clas);
						ctx.startActivity(i);
						((Activity) ctx).finish();
					}

				});
		dialogMessage = warning.create();
		dialogMessage.show();
	}

	int getWidthPixels(double valuePorcentajeWidth) {
		Display display = ((Activity) ctx).getWindowManager()
				.getDefaultDisplay();
		int width = display.getWidth();
		int porcenToPixels = (int) (width * valuePorcentajeWidth);
		return porcenToPixels;
	}

	int getHeightPixels(double valuePorcentajeHeight) {
		Display display = ((Activity) ctx).getWindowManager()
				.getDefaultDisplay();
		int height = display.getHeight();
		int porcenToPixels = (int) (height * valuePorcentajeHeight);
		return porcenToPixels;
	}

	public String idUnico() {

		Date dNow = new Date();
		long lnMilisegundos = dNow.getTime();
		Date dNow2 = new Date("01/01/2013");
		long lnMilisegundos2 = dNow2.getTime();
		long lnMilisegundos3 = lnMilisegundos - lnMilisegundos2;
		String id = lnMilisegundos3 + "";
		return id;
	}

	@SuppressLint("SimpleDateFormat")
	public String now() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String fecha = ft.format(dNow);
		return fecha;
	}

	@SuppressLint("SimpleDateFormat")
	public String nowISO() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");// 8634105-05:00
		String fecha = ft.format(dNow);
		return fecha;
	}

	@SuppressLint("SimpleDateFormat")
	public String nowFecha() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyyy");
		String fecha = ft.format(dNow);
		return fecha;
	}

	@SuppressLint("SimpleDateFormat")
	public String nowMilisegundos() {
		Date dNow = new Date();
		long lnMilisegundos = dNow.getTime();
		String fecha = lnMilisegundos + "";
		return fecha;
	}

	public Date nowDate() {
		Date dNow = new Date();
		return dNow;
	}

	public String convertirURL(String str) {

		String url;
		if (str == null) {
			str = "";
		}
		try {
			url = URLEncoder.encode(str, "UTF-8").toString();
		} catch (UnsupportedEncodingException e1) {
			url = str;
		}
		return url;
	}

	public int getWidthSize(double valuePorcentajeWidth, float w) {
		int width = (int) w;
		int porcenToPixels = (int) (width * valuePorcentajeWidth);
		return porcenToPixels;
	}

	public int getHeightSize(double valuePorcentajeHeight, float h) {
		int height = (int) h;
		int porcenToPixels = (int) (height * valuePorcentajeHeight);
		return porcenToPixels;
	}

	public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public Bitmap getRoundedCornerBitmap(Bitmap bitmap, boolean square) {
		int width = 0;
		int height = 0;

		if (square) {
			if (bitmap.getWidth() < bitmap.getHeight()) {
				width = bitmap.getWidth();
				height = bitmap.getWidth();
			} else {
				width = bitmap.getHeight();
				height = bitmap.getHeight();
			}
		} else {
			height = bitmap.getHeight();
			width = bitmap.getWidth();
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		final float roundPx = 10;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

}
