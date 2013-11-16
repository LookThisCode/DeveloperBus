package mx.developerbus.foodbus.adpt;
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
import java.util.ArrayList;

import mx.developerbus.foodbus.R;
import mx.developerbus.foodbus.model.MenuOption;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListMenuOptionsAdapter extends ArrayAdapter<MenuOption> {

	private ArrayList<MenuOption> list = new ArrayList<MenuOption>();
	private Context context;
	private MenuOption selection;

	public ListMenuOptionsAdapter(Context context) {
		super(context, 0);
		this.context = context;
	}

	public ArrayList<MenuOption> getListItems() {
		return list;
	}

	@Override
	public void add(MenuOption object) {
		list.add(object);
	}

	@Override
	public void clear() {
		if (list != null) {
			list.clear();
		}
		super.clear();
	}

	@Override
	public void remove(MenuOption object) {
		list.remove(object);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MenuOption getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getPosition(MenuOption item) {
		return list.indexOf(item);
	}

	public MenuOption getSelection() {
		return selection;
	}

	public void setSelection(MenuOption selection) {
		this.selection = selection;
	}

	public int getSelectedPosition() {
		int p = -1;
		for (MenuOption o : list)
			if (o.getType() == selection.getType()) {
				p = getPosition(o);
				break;
			}
		return p;
	}

	@Override
	public View getView(int position, View row, ViewGroup parent) {

		ViewHolder holder = null;
		MenuOption option = list.get(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			row = inflater.inflate(option.getResourceId(), parent, false);

			holder = new ViewHolder();
			holder.lblTextOption = (TextView) row
					.findViewById(R.id.lblTextOption);
			holder.menuItemIcon = (ImageView) row
					.findViewById(R.id.menuItemIcon);
			row.setTag(holder);
			if (selection != null && option.getType() == selection.getType()) {
				row.setSelected(true);
				row.setPressed(true);
			}
		} else {
			holder = (ViewHolder) row.getTag();
		}

		holder.lblTextOption.setText(option.getTitle());
		holder.menuItemIcon.setVisibility(View.VISIBLE);
		holder.menuItemIcon
				.setImageResource(option.getType().getIconResource());

		return row;
	}

	private static class ViewHolder {
		TextView lblTextOption;
		ImageView menuItemIcon;
	}

}
