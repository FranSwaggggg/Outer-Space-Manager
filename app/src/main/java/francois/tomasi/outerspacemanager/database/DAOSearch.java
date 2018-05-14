package francois.tomasi.outerspacemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import francois.tomasi.outerspacemanager.models.Search;


public class DAOSearch {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
        OuterSpaceManagerDB.KEY_ID,
        OuterSpaceManagerDB.KEY_SEARCH_search_id,
        OuterSpaceManagerDB.KEY_SEARCH_level,
        OuterSpaceManagerDB.KEY_SEARCH_amountOfEffectByLevel,
        OuterSpaceManagerDB.KEY_SEARCH_amountOfEffectLevel0,
        OuterSpaceManagerDB.KEY_SEARCH_building,
        OuterSpaceManagerDB.KEY_SEARCH_effect,
        OuterSpaceManagerDB.KEY_SEARCH_gasCostByLevel,
        OuterSpaceManagerDB.KEY_SEARCH_gasCostLevel0,
        OuterSpaceManagerDB.KEY_SEARCH_mineralCostByLevel,
        OuterSpaceManagerDB.KEY_SEARCH_mineralCostLevel0,
        OuterSpaceManagerDB.KEY_SEARCH_name,
        OuterSpaceManagerDB.KEY_SEARCH_timeToBuildByLevel,
        OuterSpaceManagerDB.KEY_SEARCH_timeToBuildLevel0
    };

    public DAOSearch(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Search createSearch(Search s) {

        ContentValues values = new ContentValues();
        values.put(OuterSpaceManagerDB.KEY_SEARCH_search_id, s.getSearchId());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_level, s.getLevel());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_amountOfEffectByLevel, s.getAmountOfEffectByLevel());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_amountOfEffectLevel0, s.getAmountOfEffectLevel0());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_building, s.isBuilding());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_effect, s.getEffect());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_gasCostByLevel, s.getGasCostByLevel());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_gasCostLevel0, s.getGasCostLevel0());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_mineralCostByLevel, s.getMineralCostByLevel());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_mineralCostLevel0, s.getMineralCostLevel0());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_name, s.getName());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_timeToBuildByLevel, s.getTimeToBuildByLevel());
        values.put(OuterSpaceManagerDB.KEY_SEARCH_timeToBuildLevel0, s.getTimeToBuildLevel0());
        UUID newID = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.KEY_ID, newID.toString());

        database.insert(OuterSpaceManagerDB.SEARCH_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search newSearch = cursorToSearch(cursor);
        cursor.close();
        return newSearch;
    }

    public Boolean deleteSearch(int searchId) {
        return database.delete(OuterSpaceManagerDB.SEARCH_TABLE_NAME,
                OuterSpaceManagerDB.KEY_SEARCH_search_id + '=' + searchId,
                null) > 0;
    }

    public Boolean deleteAllSearches() {
        return database.delete(OuterSpaceManagerDB.SEARCH_TABLE_NAME,
                OuterSpaceManagerDB.KEY_SEARCH_search_id + "> -1",
                null) > 0;
    }

    private Search cursorToSearch(Cursor cursor) {
        Search comment = new Search();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setSearchId(cursor.getInt(1));
        comment.setLevel(cursor.getInt(2));
        comment.setAmountOfEffectByLevel(cursor.getInt(3));
        comment.setAmountOfEffectLevel0(cursor.getInt(4));
        comment.setIsBuilding(cursor.getInt(5)==1);
        comment.setEffect(cursor.getString(6));
        comment.setGasCostByLevel(cursor.getInt(7));
        comment.setGasCostLevel0(cursor.getInt(8));
        comment.setMineralCostByLevel(cursor.getInt(9));
        comment.setMineralCostLevel0(cursor.getInt(10));
        comment.setName(cursor.getString(11));
        comment.setTimeToBuildByLevel(cursor.getInt(12));
        comment.setTimeToBuildLevel0(cursor.getInt(13));
        return comment;
    }

    public List<Search> getAllSearch() {
        List<Search> listSearch= new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME, allColumns, null,
                null, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Search search = cursorToSearch(cursor);
            listSearch.add(search);
        }
        cursor.close();
        return listSearch;
    }

    public Search getSearch(String id) {
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + id + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }

    public Search getSearchByName(String name) {
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_SEARCH_name + " =\"" + name + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }

    public Search getSearchByEffect(String effect) {
        Cursor cursor = database.query(OuterSpaceManagerDB.SEARCH_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_SEARCH_effect + " =\"" + effect + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Search search = cursorToSearch(cursor);
        cursor.close();
        return search;
    }
}