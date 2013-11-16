package com.want_it.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.want_it.clases.*;

public class PerfilProductoUsuario extends Activity {
	private Button btnEnviarDeseo;
	private String presupuesto;
	private String materiales;
	private String colores;
	private String largo;
	private String ancho;
	private String ciudad;
	private EditText etPresupuesto;
	private EditText etMateriales;
	private EditText etColores;
	private EditText etLargo;
	private EditText etAncho;
	private TextView tvNickname;
	private BPOServer bpos;
	private TextView tvDeseasCategoria;
	private Main mn;
	private int categoria;
	private String producto;
	private LinearLayout llEncabezadoProducto;
	private AutoCompleteTextView acProducto;
	private String productoEscogido;
	public static String TAG="PerfilProductoUsuario";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil_producto_usuario);
		btnEnviarDeseo=(Button) findViewById(R.id.btnEnviarDeseo);
		etAncho=(EditText) findViewById(R.id.etDimenAncho);
		etColores=(EditText) findViewById(R.id.etColores);
		etLargo=(EditText) findViewById(R.id.etDimeLargo);
		etMateriales=(EditText) findViewById(R.id.etMateriales);
		etPresupuesto=(EditText) findViewById(R.id.etPresupuesto);
		acProducto=(AutoCompleteTextView) findViewById(R.id.acProducto);
		tvNickname=(TextView) findViewById(R.id.tvNickname);
		tvDeseasCategoria=(TextView) findViewById(R.id.tvDeseasCategoria);
		llEncabezadoProducto=(LinearLayout) findViewById(R.id.llEncabezadoProducto);
		Bundle bundle=getIntent().getExtras();
		categoria = bundle.getInt("producto");
		identificarCategoria();
		tvDeseasCategoria.setText("¿Que deseas para tu "+producto+"?");
		String name = Main.usuario;
		productoEscogido=acProducto.getText()+"";
		
		tvNickname.setText(name);
		btnEnviarDeseo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ancho=etAncho.getText()+"";
				colores=etColores.getText()+"";
				largo=etLargo.getText()+"";
				materiales=etMateriales.getText()+"";
				presupuesto=etPresupuesto.getText()+"";
				ciudad="Bogota";
				
				try{
					//bpos.crearOfertar(Long.parseLong(mn.id), producto, presupuesto, largo, ancho, colores, materiales, ciudad,productoEscogido);
					bpos.crearOfertar(mn.id, producto, presupuesto, largo, ancho, colores, materiales, ciudad,productoEscogido);
				}catch(Exception ex){
					Log.e(TAG,"Entramos en un error"+ex);
				}
				
//				Intent i=new Intent(getApplicationContext(),CaracteristicasProducto.class);
//				startActivity(i);
				
			}
		});
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}


	private void identificarCategoria() {
		switch(categoria){
		case 0:
			producto="Baño";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_banio);
			break;
		case 1:
			producto="Concina";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_cocina);
			break;
		case 2:
			producto="Comedor";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_comedor);
			break;
		case 3:
			producto="Estudio";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_estudio);
			break;
		case 4:
			producto="Exteriores";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_exteriores);
			break;
		case 5:
			producto="Habitacion";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_habitacion);
			break;
		case 6:
			producto="Sala";
			llEncabezadoProducto.setBackgroundResource(R.drawable.barra_sala);
			break;
		default:break;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil_producto_usuario, menu);
		return true;
	}

}
