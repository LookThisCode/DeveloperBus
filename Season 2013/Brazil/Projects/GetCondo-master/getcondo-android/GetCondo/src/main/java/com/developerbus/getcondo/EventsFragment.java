package com.developerbus.getcondo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.adapters.EventsArrayAdapter;
import com.developerbus.getcondo.adapters.NewsArrayAdapter;
import com.developerbus.getcondo.models.Event;
import com.developerbus.getcondo.models.EventsList;
import com.developerbus.getcondo.models.News;
import com.developerbus.getcondo.models.NewsList;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class EventsFragment extends ListFragment {

    RequestQueue mRequestQueue;
    List<Event> mEvents;

    public EventsFragment(RequestQueue requestQueue) {
        super();
        mRequestQueue = requestQueue;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchEvents();
        setListShown(false);
    }

    private void fetchEvents() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = Settings.apiHostname() + "/condos/" + Settings.getSession().getCondoId() + "/events";
        GsonRequest<EventsList> request = new GsonRequest<EventsList>(
                Request.Method.GET,
                url,
                EventsList.class,
                this.getEventsSuccessListener(),
                this.getEventsErrorListener());
        mRequestQueue.add(request);
    }

    private void initializeGUI() {
        final EventsArrayAdapter adapter = new EventsArrayAdapter(getActivity(), R.layout.row_event, mEvents);
        setListAdapter(adapter);
        setListShown(true);
    }

    private Response.Listener<EventsList> getEventsSuccessListener() {
        return new Response.Listener<EventsList>() {
            @Override
            public void onResponse(EventsList response) {
                mEvents = response.getResult();
                initializeGUI();
            }
        };
    }

    private Response.ErrorListener getEventsErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }
}