package com.want_it.home;

import java.io.InputStream;

import com.google.analytics.tracking.android.EasyTracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class PerfilUsuario extends Activity implements OnClickListener{
	private ImageView ivFotoPerfil;
	private TextView tvNickname;
	private Button btnComedor;
	private Button btnSala;
	private Button btnCocina;
	private Button btnBano;
	private Button btnHabitacion;
	private Button btnExteriores;
	private Button btnEstudio;
	private int producto;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_usuario);
		inicializa();
		listener();
		
		String name = Main.usuario;
		String avatar =Main.avatar;
		
		tvNickname.setText(name);
		new DownloadImageTask(ivFotoPerfil).execute(avatar);
	}
	

	private void listener() {
		btnBano.setOnClickListener(this);
		btnCocina.setOnClickListener(this);
		btnComedor.setOnClickListener(this);
		btnEstudio.setOnClickListener(this);
		btnExteriores.setOnClickListener(this);
		btnHabitacion.setOnClickListener(this);
		btnSala.setOnClickListener(this);
		
	}
	


	private void inicializa() {
		tvNickname = (TextView) findViewById(R.id.tvNickname);
		ivFotoPerfil = (ImageView) findViewById(R.id.ivFotoPerfil);
		btnBano=(Button) findViewById(R.id.btnBano);
		btnCocina=(Button) findViewById(R.id.btnCocina);
		btnComedor=(Button) findViewById(R.id.btnComedor);
		btnEstudio=(Button) findViewById(R.id.btnEstudio);
		btnExteriores=(Button) findViewById(R.id.btnExteriores);
		btnHabitacion=(Button) findViewById(R.id.btnHabitacion);
		btnSala=(Button) findViewById(R.id.btnSala);
	}


	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  protected void onPostExecute(Bitmap result) {
			  int width = 0;
			  int height = 0;
			  
		      
			  
			       width = result.getWidth();
			       height = result.getHeight();
			      
			     
			     
			        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
			        Canvas canvas = new Canvas(output);

			        final int color = 0xff424242;
			        final Paint paint = new Paint();
			        final Rect rect = new Rect(10, 10, width, height);
			        final RectF rectF = new RectF(rect);
			        final float roundPx = 180; 

			        paint.setAntiAlias(true);
			        canvas.drawARGB(0, 0, 0, 0);
			        paint.setColor(color);
			        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			        canvas.drawBitmap(result, rect, rect, paint);
			        
			        bmImage.setImageBitmap(output);
		  }
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_usuario, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btnBano:
			producto=0;
			break;
		case R.id.btnCocina:
			producto=1;
			break;
		case R.id.btnComedor:
			producto=2;
			break;
		case R.id.btnEstudio:
			producto=3;
			break;
		case R.id.btnExteriores:
			producto=4;
			break;
		case R.id.btnHabitacion:
			producto=5;
			break;
		case R.id.btnSala:
			producto=6;
			break;
		default:break;
		}

		Intent i=new Intent(getApplicationContext(), PerfilProductoUsuario.class);
		i.putExtra("producto",producto );
		startActivity(i);
		
	}

}
