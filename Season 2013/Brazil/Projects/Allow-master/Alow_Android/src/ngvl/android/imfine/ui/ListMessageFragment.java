package ngvl.android.imfine.ui;

import ngvl.android.imfine.R;
import ngvl.android.imfine.gcm.GcmHelper;
import ngvl.android.imfine.model.Message;
import ngvl.android.imfine.persistence.DBHelper;
import ngvl.android.imfine.persistence.MessageProvider;
import ngvl.android.imfine.ui.adapter.MessageCursorAdapter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListMessageFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

	GcmHelper helper;
	SimpleCursorAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_list_messages, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		helper = new GcmHelper(getActivity());
		
		mAdapter = new MessageCursorAdapter(getActivity(), null);
		
		getListView().setAdapter(mAdapter);
		
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
		
		setHasOptionsMenu(true);
		
		registrarClick(null);		
	}
	
	public void registrarClick(View v) {

		if (helper.checkPlayServices(getActivity())) {
			if (helper.getRegid().isEmpty()){
				helper.registerInBackground();
			}

		} else {
			Log.i("NGVL", "No valid Google Play Services APK found.");
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(
				getActivity(),
				MessageProvider.CONTENT_URI,
				DBHelper.ALL_COLUMNS,
				null, 
				null, 
				DBHelper.COLUMN_ID);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> data) {
		mAdapter.swapCursor(null);		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor cursor = getActivity().getContentResolver().query(
				Uri.withAppendedPath(MessageProvider.CONTENT_URI, String.valueOf(id)), 
				null, null, null, null);
		if (cursor.moveToFirst()){
			Message message = new Message();
			message.id = cursor.getLong(cursor.getColumnIndex(DBHelper.COLUMN_ID));
			message.sender = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SENDER));
			message.message = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MESSAGE));
			message.photo = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PHOTO));
			message.read = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_READ)) == 1;
			
			
			if (getActivity() instanceof OnMessageSelectedListener){
				((OnMessageSelectedListener)getActivity()).onMessageSelected(message);
			}
		}		
	}
	
	interface OnMessageSelectedListener {
		void onMessageSelected(Message m);
	}
}
