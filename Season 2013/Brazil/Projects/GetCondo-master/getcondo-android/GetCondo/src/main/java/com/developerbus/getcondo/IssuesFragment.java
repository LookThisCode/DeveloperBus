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
import com.developerbus.getcondo.adapters.IssuesArrayAdapter;
import com.developerbus.getcondo.adapters.NewsArrayAdapter;
import com.developerbus.getcondo.models.Issue;
import com.developerbus.getcondo.models.IssuesList;
import com.developerbus.getcondo.models.News;
import com.developerbus.getcondo.models.NewsList;
import com.developerbus.getcondo.utils.GsonRequest;
import com.developerbus.getcondo.utils.Settings;

import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class IssuesFragment extends ListFragment {

    RequestQueue mRequestQueue;
    List<Issue> mIssues;

    public IssuesFragment(RequestQueue requestQueue) {
        super();
        mRequestQueue = requestQueue;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        mCurrentView = (View) inflater.inflate(R.layout.fragment_issues, container, false);

        fetchIssues();
        return mCurrentView;
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchIssues();
        setListShown(false);
    }

    private void fetchIssues() {
        String url = Settings.apiHostname() + "/condos/" + Settings.getSession().getCondoId() + "/issues";
        GsonRequest<IssuesList> request = new GsonRequest<IssuesList>(
                Request.Method.GET,
                url,
                IssuesList.class,
                this.getIssuesSuccessListener(),
                this.getIssuesErrorListener());
        mRequestQueue.add(request);
    }

    private void initializeGUI() {
        final IssuesArrayAdapter adapter = new IssuesArrayAdapter(getActivity(), R.layout.row_issue, mIssues);
        setListAdapter(adapter);
        setListShown(true);
    }

    private Response.Listener<IssuesList> getIssuesSuccessListener() {
        return new Response.Listener<IssuesList>() {
            @Override
            public void onResponse(IssuesList response) {
                mIssues = response.getResult();
                initializeGUI();
            }
        };
    }

    private Response.ErrorListener getIssuesErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }
}