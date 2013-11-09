package com.example.artex3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lista extends Activity{
	ListView list;
    String[] web = {
        "Reseña del libro",
            "Video",            
            "Trivia",
            
    } ;
    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
    };

	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista);
        CustomList adapter = new CustomList(Lista.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if(position==0){
            		Intent myIntent = new Intent(view.getContext(), Resenia.class);
                    startActivityForResult(myIntent, 0);	
            	}else if(position==1){
            		String video_path = "http://www.youtube.com/watch?v=h_rhUsDiBCo";
            		Uri uri = Uri.parse(video_path);
            		// With this line the Youtube application, if installed, will launch immediately.
            		// Without it you will be prompted with a list of the application to choose.
            		uri = Uri.parse("vnd.youtube:"  + uri.getQueryParameter("v"));
            		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            		startActivity(intent);            		
            		//Intent myIntent = new Intent(view.getContext(), Video.class);
                    //startActivityForResult(myIntent, 0);            		
            	}else{
            		Intent myIntent = new Intent(view.getContext(), Trivia.class);
                    //startActivityForResult(myIntent, 0);
            		startActivity(myIntent);
            	}
            	                
                //Toast.makeText(Lista.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
