package francois.tomasi.outerspacemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import francois.tomasi.outerspacemanager.models.Building;

public class DAOBuilding {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
        OuterSpaceManagerDB.KEY_ID,
        OuterSpaceManagerDB.KEY_BUILDING_building_id,
        OuterSpaceManagerDB.KEY_BUILDING_level,
        OuterSpaceManagerDB.KEY_BUILDING_amountOfEffectByLevel,
        OuterSpaceManagerDB.KEY_BUILDING_amountOfEffectLevel0,
        OuterSpaceManagerDB.KEY_BUILDING_building,
        OuterSpaceManagerDB.KEY_BUILDING_effect,
        OuterSpaceManagerDB.KEY_BUILDING_gasCostByLevel,
        OuterSpaceManagerDB.KEY_BUILDING_gasCostLevel0,
        OuterSpaceManagerDB.KEY_BUILDING_imageUrl,
        OuterSpaceManagerDB.KEY_BUILDING_mineralCostByLevel,
        OuterSpaceManagerDB.KEY_BUILDING_mineralCostLevel0,
        OuterSpaceManagerDB.KEY_BUILDING_name,
        OuterSpaceManagerDB.KEY_BUILDING_timeToBuildByLevel,
        OuterSpaceManagerDB.KEY_BUILDING_timeToBuildLevel0
    };

    public DAOBuilding(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Building createBuilding(Building b) {

        ContentValues values = new ContentValues();
        values.put(OuterSpaceManagerDB.KEY_BUILDING_building_id, b.getBuildingId());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_level, b.getLevel());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_amountOfEffectByLevel, b.getAmountOfEffectByLevel());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_amountOfEffectLevel0, b.getAmountOfEffectLevel0());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_building, b.isBuilding());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_effect, b.getEffect());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_gasCostByLevel, b.getGasCostByLevel());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_gasCostLevel0, b.getGasCostLevel0());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_imageUrl, b.getImageUrl());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_mineralCostByLevel, b.getMineralCostByLevel());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_mineralCostLevel0, b.getMineralCostLevel0());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_name, b.getName());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_timeToBuildByLevel, b.getTimeToBuildByLevel());
        values.put(OuterSpaceManagerDB.KEY_BUILDING_timeToBuildLevel0, b.getTimeToBuildLevel0());
        UUID newID = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.KEY_ID, newID.toString());

        database.insert(OuterSpaceManagerDB.BUILDING_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building newBuilding = cursorToBuilding(cursor);
        cursor.close();
        return newBuilding;
    }

    public Boolean deleteBuilding(int buildingId) {
        return database.delete(OuterSpaceManagerDB.BUILDING_TABLE_NAME,
                OuterSpaceManagerDB.KEY_BUILDING_building_id + '=' + buildingId,
                null) > 0;
    }

    public Boolean deleteAllBuildings() {
        return database.delete(OuterSpaceManagerDB.BUILDING_TABLE_NAME,
                OuterSpaceManagerDB.KEY_BUILDING_building_id + "> -1",
                null) > 0;
    }

    private Building cursorToBuilding(Cursor cursor) {
        Building comment = new Building();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setBuildingId(cursor.getInt(1));
        comment.setLevel(cursor.getInt(2));
        comment.setAmountOfEffectByLevel(cursor.getInt(3));
        comment.setAmountOfEffectLevel0(cursor.getInt(4));
        comment.setIsBuilding(cursor.getInt(5) == 1);
        comment.setEffect(cursor.getString(6));
        comment.setGasCostByLevel(cursor.getInt(7));
        comment.setGasCostLevel0(cursor.getInt(8));
        comment.setImageUrl(cursor.getString(9));
        comment.setMineralCostByLevel(cursor.getInt(10));
        comment.setMineralCostLevel0(cursor.getInt(11));
        comment.setName(cursor.getString(12));
        comment.setTimeToBuildByLevel(cursor.getInt(13));
        comment.setTimeToBuildLevel0(cursor.getInt(14));
        return comment;
    }

    public List<Building> getAllBuilding() {
        List<Building> listBuilding = new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumns, null,
                null, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Building building = cursorToBuilding(cursor);
            listBuilding.add(building);
        }
        cursor.close();
        return listBuilding;
    }

    public Building getBuilding(String id) {
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + id + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }

    public Building getBuildingByName(String name) {
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_BUILDING_name + " =\"" + name + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }

    public Building getBuildingByEffect(String effect) {
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_BUILDING_effect + " =\"" + effect + "\"",
                null, null, null, null);
        cursor.moveToFirst();
        Building building = cursorToBuilding(cursor);
        cursor.close();
        return building;
    }
}