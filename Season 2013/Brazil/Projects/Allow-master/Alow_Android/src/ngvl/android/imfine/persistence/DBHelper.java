package ngvl.android.imfine.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME         = "messages";
	public static final String COLUMN_ID          = "_id";
	public static final String COLUMN_SENDER      = "sender";
	public static final String COLUMN_PHOTO       = "photo";
	public static final String COLUMN_MESSAGE     = "message";
	public static final String COLUMN_TIME        = "time";
	public static final String COLUMN_READ        = "read";
	
	public static final String[] ALL_COLUMNS = { 
		COLUMN_ID, 
		COLUMN_SENDER,
		COLUMN_MESSAGE,
		COLUMN_TIME,
		COLUMN_PHOTO,
		COLUMN_READ
	};	
	
	private static final String NOME_BANCO   = "dbMessages";
	private static final int    VERSAO_BANCO = 1;
	
	public DBHelper(Context context) {
		super(context, NOME_BANCO, null, VERSAO_BANCO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
				"CREATE TABLE "+ TABLE_NAME +" ("+ 
						COLUMN_ID      +" INTEGER PRIMARY KEY AUTOINCREMENT," +
						COLUMN_SENDER  +" TEXT NOT NULL, "+ 
						COLUMN_PHOTO   +" TEXT, "+ 
						COLUMN_MESSAGE +" TEXT, "+ 
						COLUMN_TIME    +" INTEGER, "+
						COLUMN_READ    +" INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Utilizar s— na proxima vers‹o :)
	}

}
