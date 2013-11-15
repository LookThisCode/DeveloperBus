package mx.developerbus.foodbus.enm;

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
import java.util.EnumSet;

import mx.developerbus.foodbus.R;
import mx.developerbus.foodbus.frgm.Fragment_MiBus;
import mx.developerbus.foodbus.frgm.Fragment_MiMenu;
import mx.developerbus.foodbus.frgm.Fragment_MisPedidos;
import mx.developerbus.foodbus.frgm.Fragment_MisPublicaciones;
import android.support.v4.app.Fragment;
import android.util.SparseArray;


public enum MenuOptionType {

	MiBus(2, R.string.bus, R.drawable.bus, Fragment_MiBus.class), 
	MisPedidos(3, R.string.pedidos, R.drawable.pedido , Fragment_MisPedidos.class),
	MisPublicaciones(4, R.string.publicaciones, R.drawable.publicacion , Fragment_MisPublicaciones.class),
	MiMenu(5, R.string.menu, R.drawable.menu , Fragment_MiMenu.class);
	private int code;
	private int textResource;
	private int iconResource;
	Class<? extends Fragment> fragment;

	private static final SparseArray<MenuOptionType> lookup = new SparseArray<MenuOptionType>();

	static {
		for (MenuOptionType s : EnumSet.allOf(MenuOptionType.class)) {
			lookup.put(s.getCode(), s);
		}
	}

	MenuOptionType(int code, int textResource, int iconResource,
			Class<? extends Fragment> fragment) {
		this.code = code;
		this.textResource = textResource;
		this.iconResource = iconResource;
		this.fragment = fragment;
	}

	public int getCode() {
		return code;
	}

	public static MenuOptionType get(int code) {
		return lookup.get(code);
	}

	public int getTextResource() {
		return textResource;
	}

	public int getIconResource() {
		return iconResource;
	}

	public Class<? extends Fragment> getFragment() {
		return fragment;
	}

	public void setFragment(Class<? extends Fragment> fragment) {
		this.fragment = fragment;
	}

	


}
