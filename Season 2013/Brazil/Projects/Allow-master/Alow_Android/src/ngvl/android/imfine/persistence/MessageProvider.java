package ngvl.android.imfine.persistence;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

// Source: http://www.vogella.com/articles/AndroidSQLite/article.html
public class MessageProvider extends ContentProvider {

	public static final String AUTHORITY = "ngvl.android.imfine";

	private static final int ALL_ENTRIES = 10;
	private static final int ENTRY_ID = 20;

	private static final String BASE_PATH = "messages";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);
	
	private static final UriMatcher sUriMatcher;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, BASE_PATH, ALL_ENTRIES);
		sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ENTRY_ID);
	}

	private DBHelper mOpenHelper;

	@Override
	public boolean onCreate() {
		mOpenHelper = new DBHelper(getContext());
		return false;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case ALL_ENTRIES:
			id = sqlDB.insert(DBHelper.TABLE_NAME, null, values);
			break;			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case ALL_ENTRIES:
			rowsUpdated = sqlDB.update(DBHelper.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case ENTRY_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(DBHelper.TABLE_NAME, values,
						DBHelper.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(DBHelper.TABLE_NAME, values,
						DBHelper.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sUriMatcher.match(uri);
		SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case ALL_ENTRIES:
			rowsDeleted = sqlDB.delete(DBHelper.TABLE_NAME, selection,
					selectionArgs);
			break;
		case ENTRY_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(DBHelper.TABLE_NAME,
						DBHelper.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(DBHelper.TABLE_NAME,
						DBHelper.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
		queryBuilder.setTables(DBHelper.TABLE_NAME);

		int uriType = sUriMatcher.match(uri);
		Cursor cursor = null;
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		
		switch (uriType) {
		case ALL_ENTRIES:
			cursor = queryBuilder.query(db, projection, selection,
					selectionArgs, null, null, sortOrder);			
			break;
		case ENTRY_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(DBHelper.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			cursor = queryBuilder.query(db, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	private void checkColumns(String[] projection) {

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(DBHelper.ALL_COLUMNS));

			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						"Unknown columns in projection");
			}
		}
	}
}
