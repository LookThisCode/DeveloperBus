package com.devbus.sweethistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.devbus.controller.Tips;
import com.devbus.data.Tip;

public class TipListFragment extends ListFragment {

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(this.generateAdapter());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View timeLineView = inflater.inflate(R.layout.time_line, null);
		final TextView txtCalendar = (TextView) timeLineView
				.findViewById(R.id.txtCalendar);
		txtCalendar.setText(formatter.format(new Date()));
		txtCalendar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDateDialog(txtCalendar);
			}
		});

		return timeLineView;
	}

	private void showDateDialog(final TextView txtView) {
		// create a new dialog fragment
		DialogFragment newFragment;
		try {
			// Gets a new instance of the DatePickerFragment using the
			// actual not after date and create a new DataSetListener
			newFragment = DatePickerFragment.newInstance(
					formatter.parse(txtView.getText().toString()),
					new DatePickerFragment.OnDateSetListener() {
						@Override
						public void onDateSet(Date date) {
							txtView.setText(formatter.format(date));
						}
					}, true);
			newFragment.show(getFragmentManager(), "datePicker");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private ArrayAdapter<Tip> generateAdapter() {
		Tips tips = new Tips();
		tips.fillList();

		// ArrayAdapter<Person> adapter = new
		// ArrayAdapter<Person>(this.getActivity(),
		// R.layout.row_person,R.id.txvPersonName,persons.getPersons());
		ArrayAdapter<Tip> adapter = new TipArrayAdapter(this.getActivity(),
				R.layout.row_tip, tips.getTips());

		return adapter;
	}

	// Create a custimized adapter
	public class TipArrayAdapter extends ArrayAdapter<Tip> {

		public TipArrayAdapter(Context context, int resource, List<Tip> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowElement = inflater.inflate(R.layout.row_tip, null);

			TextView elementTitle = (TextView) rowElement
					.findViewById(R.id.tipTitle);
			elementTitle.setText(this.getItem(position).getTitle());
			LinearLayout circleElement = (LinearLayout) rowElement
					.findViewById(R.id.backTimeLineCircle);
			circleElement.setBackgroundResource(R.drawable.circle_tip_state);
			ImageView iconTimeLineCircle = (ImageView) rowElement
					.findViewById(R.id.iconTimeLineCircle);

			switch (this.getItem(position).getIdTip()) {
			case 0:
				iconTimeLineCircle.setImageResource(R.drawable.cero);
				break;
			case 1:
				iconTimeLineCircle.setImageResource(R.drawable.uno);
				break;
			case 2:
				iconTimeLineCircle.setImageResource(R.drawable.dos);
				break;
			case 3:
				iconTimeLineCircle.setImageResource(R.drawable.tres);
				break;
			case 4:
				iconTimeLineCircle.setImageResource(R.drawable.cuatro);
				break;
			case 5:
				iconTimeLineCircle.setImageResource(R.drawable.cinco);
				break;
			}

			return rowElement;
		}
	}

}
