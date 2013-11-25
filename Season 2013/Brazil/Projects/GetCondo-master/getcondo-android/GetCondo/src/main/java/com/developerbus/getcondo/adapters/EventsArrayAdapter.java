package com.developerbus.getcondo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developerbus.getcondo.R;
import com.developerbus.getcondo.models.Event;
import com.developerbus.getcondo.models.News;

import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class EventsArrayAdapter extends ArrayAdapter<Event> {

    List<Event> mEvents;
    int mRowResourceId;

    public EventsArrayAdapter(Context context, int rowResourceId,
                              List<Event> objects) {
        super(context, rowResourceId, objects);
        this.mEvents = objects;
        this.mRowResourceId = rowResourceId;
    }

    private LayoutInflater getLayoutInflater()
    {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        Event currentEvent = mEvents.get(position);

        if (row == null)
        {
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate (this.mRowResourceId, parent, false);
        }

        TextView eventPlace = (TextView) row.findViewById (R.id.eventPlace);
        TextView eventTime  = (TextView) row.findViewById (R.id.eventTime);
        TextView eventResident  = (TextView) row.findViewById (R.id.eventResident);

        eventPlace.setText(currentEvent.getPlace());
        eventTime.setText(currentEvent.getTime());
        eventResident.setText(currentEvent.getResidentName());

        return row;
    }
}
