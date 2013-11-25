package ngvl.android.imfine.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import ngvl.android.imfine.R;
import ngvl.android.imfine.model.Message;
import ngvl.android.imfine.persistence.DBHelper;
import ngvl.android.imfine.persistence.MessageProvider;
import ngvl.android.imfine.persistence.MessagesDB;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ViewMessageFragment extends Fragment {

	public static ViewMessageFragment newInstance(Message m){
		Bundle b = new Bundle();
		b.putSerializable("message", m);
		
		ViewMessageFragment f = new ViewMessageFragment();
		f.setArguments(b);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.fragment_view_message, null);
		
		Message message = (Message)getArguments().getSerializable("message");
		message.read = true;
		ContentValues cv = MessagesDB.contentValuesByMessage(message);
        getActivity().getContentResolver().update(MessageProvider.CONTENT_URI, cv, DBHelper.COLUMN_ID +" = "+ message.id, null);		
		
		TextView txtSender = (TextView)layout.findViewById(R.id.txtSender);
		TextView txtMessage = (TextView)layout.findViewById(R.id.txtMessage);
		TextView txtTime = (TextView)layout.findViewById(R.id.txtDate);
		ImageView imgPhoto = (ImageView)layout.findViewById(R.id.imgPhoto);
		
		txtSender.setText(message.sender);
		txtMessage.setText(message.message);
		
		try {
			Date date = new Date(message.time);

			txtTime.setText(SimpleDateFormat.getDateTimeInstance().format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!TextUtils.isEmpty(message.photo)) {
			ImageLoader.getInstance().displayImage(message.photo, imgPhoto);
		}			
		
		return layout;
	}
}
