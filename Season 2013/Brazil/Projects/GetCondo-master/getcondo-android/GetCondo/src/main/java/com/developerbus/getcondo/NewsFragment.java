package com.developerbus.getcondo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.developerbus.getcondo.adapters.NewsArrayAdapter;
import com.developerbus.getcondo.models.News;
import com.developerbus.getcondo.models.NewsList;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class NewsFragment extends ListFragment {

    RequestQueue mRequestQueue;
    List<News> mNews;

    public NewsFragment(RequestQueue requestQueue) {
        super();
        mRequestQueue = requestQueue;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchNews();
        setListShown(false);
    }

    private void fetchNews() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = Settings.apiHostname() + "/condos/" + Settings.getSession().getCondoId() + "/news";
        GsonRequest<NewsList> request = new GsonRequest<NewsList>(
                Request.Method.GET,
                url,
                NewsList.class,
                this.getNewsSuccessListener(),
                this.getNewsErrorListener());
        mRequestQueue.add(request);
    }

    private void initializeGUI() {
        final NewsArrayAdapter adapter = new NewsArrayAdapter(getActivity(), R.layout.row_news, mNews);
        setListAdapter(adapter);
        setListShown(true);
    }

    private Response.Listener<NewsList> getNewsSuccessListener() {
        return new Response.Listener<NewsList>() {
            @Override
            public void onResponse(NewsList response) {
                mNews = response.getResult();
                initializeGUI();
            }
        };
    }

    private Response.ErrorListener getNewsErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }
}