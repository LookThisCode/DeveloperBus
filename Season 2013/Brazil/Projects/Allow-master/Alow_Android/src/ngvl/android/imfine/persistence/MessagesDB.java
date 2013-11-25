package ngvl.android.imfine.persistence;

import ngvl.android.imfine.model.Message;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MessagesDB {

	private DBHelper helper;
	
	public MessagesDB(Context contexto){
		helper = new DBHelper(contexto);
	}
	
	public long inserir(Message post){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = contentValuesByMessage(post);
		
		long id = db.insert("messages", null, values);
		post.id = id;
		
		db.close();
		
		return id;
	}

	public static ContentValues contentValuesByMessage(Message post) {
		ContentValues values = new ContentValues();
		values.put("sender", post.sender);
		values.put("message", post.message);
		values.put("time", post.time);
		values.put("photo", post.photo);
		values.put("read", post.read ? 1 : 0);
		return values;
	}
	
	public int excluir(Message post){
		SQLiteDatabase db = helper.getWritableDatabase();
		
		int rows = db.delete("messages", "_id = "+ post.id, null);
		db.close();
		
		return rows;
	}
}



