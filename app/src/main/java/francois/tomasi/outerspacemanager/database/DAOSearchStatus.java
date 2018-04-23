package francois.tomasi.outerspacemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import francois.tomasi.outerspacemanager.models.SearchStatus;


public class DAOSearchStatus {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
        OuterSpaceManagerDB.KEY_ID,
        OuterSpaceManagerDB.KEY_SEARCH_STATE_search_id,
        OuterSpaceManagerDB.KEY_SEARCH_STATE_searching,
        OuterSpaceManagerDB.KEY_SEARCH_STATE_date_searching
    };

    public DAOSearchStatus(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SearchStatus createSearchStatus(int searchId, String searchState, String dateConstruction) {

        ContentValues values = new ContentValues();
        String sSearchId = String.valueOf(searchId);
        values.put(OuterSpaceManagerDB.KEY_SEARCH_STATE_search_id, sSearchId);
        values.put(OuterSpaceManagerDB.KEY_SEARCH_STATE_searching, searchState);
        values.put(OuterSpaceManagerDB.KEY_SEARCH_STATE_date_searching, dateConstruction);
        UUID newID = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.KEY_ID, newID.toString());

        database.insert(OuterSpaceManagerDB.SEARCH_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_STATE_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null,null, null, null);
        cursor.moveToFirst();
        SearchStatus newSearchStatus = cursorToSearchStatus(cursor);
        cursor.close();
        return newSearchStatus;
    }

    public Boolean deleteSearchState(int searchId){
        return database.delete(OuterSpaceManagerDB.SEARCH_STATE_TABLE_NAME,
                OuterSpaceManagerDB.KEY_SEARCH_STATE_search_id + '=' + searchId,
                null) > 0;
    }

    private SearchStatus cursorToSearchStatus(Cursor cursor) {
        SearchStatus comment = new SearchStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setSearchId(cursor.getString(1));
        comment.setSearching(cursor.getString(2));
        comment.setDateSearching(cursor.getString(3));
        return comment;
    }

    public List<SearchStatus> getAllSearchStatus() {
        List<SearchStatus> listSearchStatus = new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_STATE_TABLE_NAME, allColumns,null,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            SearchStatus searchStatus = cursorToSearchStatus(cursor);
            listSearchStatus.add(searchStatus);
        }
        cursor.close();
        return listSearchStatus;
    }

    public SearchStatus getSearchStatus(String id) {
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_STATE_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + id + "\"",
                null,null, null, null);
        cursor.moveToFirst();
        SearchStatus searchStatus = cursorToSearchStatus(cursor);
        cursor.close();
        return searchStatus;
    }
}