package com.developerbus.getcondo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developerbus.getcondo.R;
import com.developerbus.getcondo.models.Issue;
import com.developerbus.getcondo.models.News;
import com.developerbus.getcondo.utils.Settings;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class IssuesArrayAdapter extends ArrayAdapter<Issue> {

    List<Issue> mIssues;
    int mRowResourceId;
    Context mContext;

    public IssuesArrayAdapter(Context context, int rowResourceId,
                              List<Issue> objects) {
        super(context, rowResourceId, objects);
        this.mIssues = objects;
        this.mRowResourceId = rowResourceId;
        this.mContext = context;
    }

    private LayoutInflater getLayoutInflater()
    {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        Issue currentIssue = mIssues.get(position);

        if (row == null)
        {
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate (this.mRowResourceId, parent, false);
        }

        TextView issueDescription  = (TextView) row.findViewById (R.id.issueDescription);
        TextView issueAuthor  = (TextView) row.findViewById (R.id.issueAuthor);
        ImageView issuePicture  = (ImageView) row.findViewById (R.id.issuePicture);

        issueDescription.setText(currentIssue.getDescription());
        issueAuthor.setText("por " + currentIssue.getAuthorName() + " do " + currentIssue.getAuthorApartment() );

        if(currentIssue.getPictureUrl() != null) {
            String pictureUrl = Settings.assetsHostname() + currentIssue.getPictureUrl();
            Picasso.with(mContext).load(pictureUrl).placeholder(R.drawable.noimg).into(issuePicture);
        }
        return row;
    }
}
