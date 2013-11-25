package ngvl.android.imfine.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import ngvl.android.imfine.R;
import ngvl.android.imfine.persistence.DBHelper;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageCursorAdapter extends SimpleCursorAdapter {

	private static final int LAYOUT = R.layout.message_item;

	public MessageCursorAdapter(Context context, Cursor cursor) {
		super(context, LAYOUT, cursor, DBHelper.ALL_COLUMNS, null, 0);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView txtSender = (TextView) view.findViewById(R.id.txtSender);
		TextView txtMessage = (TextView) view.findViewById(R.id.txtMessage);
		TextView txtTime = (TextView) view.findViewById(R.id.txtTime);
		ImageView imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
		ImageView imgRead = (ImageView) view.findViewById(R.id.imgRead);

		txtSender.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_SENDER)));
		txtMessage.setText(cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_MESSAGE)));

		try {
			Date date = new Date(cursor.getLong(cursor
					.getColumnIndex(DBHelper.COLUMN_TIME)));

			txtTime.setText(SimpleDateFormat.getDateTimeInstance().format(date));

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean read = cursor.getInt(cursor
				.getColumnIndex(DBHelper.COLUMN_READ)) == 1;
		imgRead.setImageResource(read ? R.drawable.ic_read : R.drawable.ic_unread);
		
		String imgUrl = cursor.getString(cursor
				.getColumnIndex(DBHelper.COLUMN_PHOTO));
		if (!TextUtils.isEmpty(imgUrl)) {
			ImageLoader.getInstance().displayImage(imgUrl, imgPhoto);
		}
	}

	@Override
	public View newView(Context contex, Cursor cursor, ViewGroup viewGroup) {
		return LayoutInflater.from(contex).inflate(LAYOUT, null);
	}
}
