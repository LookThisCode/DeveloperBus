package mx.developerbus.foodbus.frgm;

import mx.developerbus.foodbus.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_MiBus extends Fragment{

	private ImageView imgPerfilBus;
	private TextView txtPerfilBus;
	private ListView listinformacion;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.layout_mibus, container, false);
		
		
		return v;
	}

	
	
}
