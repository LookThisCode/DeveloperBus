package com.developerbus.android;

import java.util.ArrayList;

import com.developerbus.android.adapters.TweetAdapter;
import com.developerbus.android.model.Tweet;
import com.developerbus.android.utils.ConstantsUtils;
import com.developerbus.android.utils.TwitterUtils;
import com.developerbus.android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lvTimeLine;	
	private TweetAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_timeline);

		lvTimeLine = (ListView) findViewById(R.id.lv_timeline);		

		new TweetSearchTask().execute();

	}

	public void updateListView(ArrayList<Tweet> tweets) {
		adapter = new TweetAdapter(this, R.layout.row_tweet, tweets);
		lvTimeLine.setAdapter(adapter);
	}

	class TweetSearchTask extends AsyncTask<Object, Void, ArrayList<Tweet>> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPostExecute(ArrayList<Tweet> result) {
			super.onPostExecute(result);
			progressDialog.dismiss();

			if (!result.isEmpty()) {
				updateListView(result);
			} else {
				Toast.makeText(
						MainActivity.this,
						getResources().getString(
								R.string.label_tweets_not_found),
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage(getResources().getString(
					R.string.label_tweet_search_loader));
			progressDialog.show();
		}

		@Override
		protected ArrayList<Tweet> doInBackground(Object... params) {
			ArrayList<Tweet> tweets = new ArrayList<Tweet>();

			try {

				tweets = TwitterUtils
						.getTimelineForSearchTerm(ConstantsUtils.DEVELOPERBUS_TERM);			

			} catch (Exception e) {
				e.printStackTrace();
			}

			return tweets;
		}
	}

}
