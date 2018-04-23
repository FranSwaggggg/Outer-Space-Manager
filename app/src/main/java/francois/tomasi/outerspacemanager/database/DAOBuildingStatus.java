package francois.tomasi.outerspacemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import francois.tomasi.outerspacemanager.models.BuildingStatus;

public class DAOBuildingStatus {
    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumns = {
        OuterSpaceManagerDB.KEY_ID,
        OuterSpaceManagerDB.KEY_BUILDING_STATE_building_id,
        OuterSpaceManagerDB.KEY_BUILDING_STATE_building,
        OuterSpaceManagerDB.KEY_BUILDING_STATE_date_construction
    };

    public DAOBuildingStatus(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BuildingStatus createBuildingStatus(int buildingId, String buildingState, String dateConstruction) {

        ContentValues values = new ContentValues();
        String sBuildingId = String.valueOf(buildingId);
        values.put(OuterSpaceManagerDB.KEY_BUILDING_STATE_building_id, sBuildingId);
        values.put(OuterSpaceManagerDB.KEY_BUILDING_STATE_building, buildingState);
        values.put(OuterSpaceManagerDB.KEY_BUILDING_STATE_date_construction, dateConstruction);
        UUID newID = UUID.randomUUID();
        values.put(OuterSpaceManagerDB.KEY_ID, newID.toString());

        database.insert(OuterSpaceManagerDB.BUILDING_STATE_TABLE_NAME, null,
                values);

        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_STATE_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + newID.toString() + "\"",
                null,null, null, null);
        cursor.moveToFirst();
        BuildingStatus newBuildingStatus = cursorToBuildingStatus(cursor);
        cursor.close();
        return newBuildingStatus;
    }

    public Boolean deleteBuildingState(int buildingId){
        return database.delete(OuterSpaceManagerDB.BUILDING_STATE_TABLE_NAME,
                OuterSpaceManagerDB.KEY_BUILDING_STATE_building_id + '=' + buildingId,
                null) > 0;
    }

    private BuildingStatus cursorToBuildingStatus(Cursor cursor) {
        BuildingStatus comment = new BuildingStatus();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setBuildingId(cursor.getString(1));
        comment.setBuilding(cursor.getString(2));
        comment.setDateConstruction(cursor.getString(3));
        return comment;
    }

    public List<BuildingStatus> getAllBuildingStatus() {
        List<BuildingStatus> listBuildingStatus = new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_STATE_TABLE_NAME, allColumns,null ,
                null,null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            BuildingStatus buildingStatus = cursorToBuildingStatus(cursor);
            listBuildingStatus.add(buildingStatus);
        }
        cursor.close();
        return listBuildingStatus;
    }

    public BuildingStatus getBuildingStatus(String id) {
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_STATE_TABLE_NAME, allColumns,
                OuterSpaceManagerDB.KEY_ID + " =\"" + id + "\"",
                null,null, null, null);
        cursor.moveToFirst();
        BuildingStatus buildingStatus = cursorToBuildingStatus(cursor);
        cursor.close();
        return buildingStatus;
    }
}