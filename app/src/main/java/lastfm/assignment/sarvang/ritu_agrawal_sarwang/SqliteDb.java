package lastfm.assignment.sarvang.ritu_agrawal_sarwang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteDb extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ritu_db";

    public SqliteDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqliteCreateTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SqliteCreateTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long BookmarkDetail(String name, String img, String streaming, String listeners) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqliteCreateTable.COLUMN_NAME, name);
        values.put(SqliteCreateTable.COLUMN_IMAGE, img);
        values.put(SqliteCreateTable.COLUMN_STREAMING, streaming);
        values.put(SqliteCreateTable.COLUMN_LISTENERS, listeners);
        long id = sqLiteDatabase.insert(SqliteCreateTable.TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return id;
    }

    public SqliteCreateTable getSaveBookmark(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SqliteCreateTable.TABLE_NAME,
                new String[]{SqliteCreateTable.COLUMN_ID, SqliteCreateTable.COLUMN_NAME,
                        SqliteCreateTable.COLUMN_IMAGE, SqliteCreateTable.COLUMN_STREAMING,
                        SqliteCreateTable.COLUMN_LISTENERS},
                SqliteCreateTable.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SqliteCreateTable tableDetails = new SqliteCreateTable(
                cursor.getInt(cursor.getColumnIndex(SqliteCreateTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_STREAMING)),
                cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_LISTENERS)));

        cursor.close();
        return tableDetails;
    }

    public List<SqliteCreateTable> getAllSaveBookmark() {
        List<SqliteCreateTable> detailsArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SqliteCreateTable.TABLE_NAME + " ORDER BY " +
                SqliteCreateTable.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SqliteCreateTable tableDetails = new SqliteCreateTable();
                tableDetails.setId(cursor.getInt(cursor.getColumnIndex(SqliteCreateTable.COLUMN_ID)));
                tableDetails.setName(cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_NAME)));
                tableDetails.setImg(cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_IMAGE)));
                tableDetails.setStreaming(cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_STREAMING)));
                tableDetails.setListeners(cursor.getString(cursor.getColumnIndex(SqliteCreateTable.COLUMN_LISTENERS)));

                detailsArrayList.add(tableDetails);
            } while (cursor.moveToNext());
        }
        db.close();
        return detailsArrayList;
    }

}
