package mx.developerbus.foodbus.frgm;

/**
 * Copyright [2013] [Diego Ernesto Franco Chanona, Irving Lopez Perez, Miriam Alejandra Lugo Muciño, Raymundo Juarez Cortes]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.internal.ar;

import mx.developerbus.foodbus.Login_Bus;
import mx.developerbus.foodbus.R;

import mx.developerbus.foodbus.gplus.DownloadImage;
import mx.developerbus.foodbus.gplus.UserGPlus;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_MiBus extends Fragment{

	private ImageView imgPerfilBus;
	private TextView txtPerfilBus;
	private ListView listinformacion;
	private UserGPlus userGPlus;
	
	ImageView img;
	View root;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		userGPlus = Login_Bus.user;
		
		root = inflater.inflate(R.layout.layout_mibus, container, false);
		imgPerfilBus = (ImageView)root.findViewById(R.id.imgPerfilBus);
		new DownloadIcon(imgPerfilBus).execute(userGPlus.getAvatarUrl());
		txtPerfilBus = (TextView)root.findViewById(R.id.txtPerfilBus);
		txtPerfilBus.setText(userGPlus.getName());
		Button compartir = (Button)root.findViewById(R.id.btnCompartir);
		compartir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText texto = (EditText)root.findViewById(R.id.inputPublish);
				userGPlus.setPost(texto.getText().toString());
			}
		});
		
		//ImageView [] coversFriends = new ImageView[5];
//		coversFriends[0] = 
		String [] array = userGPlus.getUrlsAvatarsFriends();
//		if(array.length>5)
//		{
			img = (ImageView)root.findViewById(R.id.user1);
//			new DownloadIcon(img).execute(array[0]);
//			new DownloadIcon((ImageView)root.findViewById(R.id.user2)).execute(array[4]);
//			new DownloadIcon((ImageView)root.findViewById(R.id.user3)).execute(array[3]);
//			new DownloadIcon((ImageView)root.findViewById(R.id.user4)).execute(array[1]);
//			new DownloadIcon((ImageView)root.findViewById(R.id.user5)).execute(array[2]);
//		}
		return root;
	}

	class DownloadIcon extends AsyncTask<String, Void, Bitmap>
	{
		ImageView img;
		
		public DownloadIcon(ImageView img) {
			this.img = img;
		}
		
		public Bitmap descargarImagen (String imageHttpAddress)
		{
		    URL imageUrl = null;
		    Bitmap imagen = null;
		    Log.i("plus","Down"+imageHttpAddress);
		    try{
		        imageUrl = new URL(imageHttpAddress);
		        HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
		        conn.connect();
		        imagen = BitmapFactory.decodeStream(conn.getInputStream());
		        Log.i("plus","Down Bien..");
		    }catch(IOException ex){
		    	Log.i("plus","Down Mal ..");
		    }
		    return imagen;
		}
		
		@Override
		protected Bitmap doInBackground(String... urls) {
			return descargarImagen(urls[0]);
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			img.setImageBitmap(bitmap);
		}
	}
	
}
