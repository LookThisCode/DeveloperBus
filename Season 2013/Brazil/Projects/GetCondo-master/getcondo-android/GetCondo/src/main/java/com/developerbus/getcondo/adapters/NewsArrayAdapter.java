package com.developerbus.getcondo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.developerbus.getcondo.R;
import com.developerbus.getcondo.models.News;

import java.util.List;

/**
 * Created by bruno on 11/21/13.
 */
public class NewsArrayAdapter extends ArrayAdapter<News> {

    List<News> mNews;
    int mRowResourceId;

    public NewsArrayAdapter(Context context, int rowResourceId,
                              List<News> objects) {
        super(context, rowResourceId, objects);
        this.mNews = objects;
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
        News currentNews = mNews.get(position);

        if (row == null)
        {
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate (this.mRowResourceId, parent, false);
        }

        TextView newsTitle = (TextView) row.findViewById (R.id.newsTitle);
        TextView newsBody  = (TextView) row.findViewById (R.id.newsBody);

        newsTitle.setText(currentNews.getTitle());
        newsBody.setText(currentNews.getDescription());

        return row;
    }
}
