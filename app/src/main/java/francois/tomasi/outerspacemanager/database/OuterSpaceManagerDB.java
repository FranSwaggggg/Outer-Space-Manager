package francois.tomasi.outerspacemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class OuterSpaceManagerDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OuterSpaceManagerDB.db";

    public static final String KEY_ID = "id";

    // ============== USER ===================

    public static final String USER_TABLE_NAME = "User";
    public static final String KEY_email = "email";
    public static final String KEY_username = "username";
    public static final String KEY_password = "password";

    private static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_email + " TEXT, " +
            KEY_username + " TEXT," +
            KEY_password + " TEXT);";

    // ============== BUILDING ===================

    public static final String BUILDING_TABLE_NAME = "Building";
    public static final String KEY_BUILDING_building_id = "building_id";
    public static final String KEY_BUILDING_level = "level"; //int
    public static final String KEY_BUILDING_amountOfEffectByLevel = "amountOfEffectByLevel"; //int
    public static final String KEY_BUILDING_amountOfEffectLevel0 = "amountOfEffectLevelZero"; //int
    public static final String KEY_BUILDING_building = "building"; //boolean
    public static final String KEY_BUILDING_effect = "effect"; //string
    public static final String KEY_BUILDING_gasCostByLevel = "gasCostByLevel"; //int
    public static final String KEY_BUILDING_gasCostLevel0 = "gasCostLevelZero"; //int
    public static final String KEY_BUILDING_imageUrl = "imageUrl"; //string
    public static final String KEY_BUILDING_mineralCostByLevel = "mineralCostByLevel"; //int
    public static final String KEY_BUILDING_mineralCostLevel0 = "mineralCostLevelZero"; //int
    public static final String KEY_BUILDING_name = "name"; //string
    public static final String KEY_BUILDING_timeToBuildByLevel = "timeToBuildByLevel"; //int
    public static final String KEY_BUILDING_timeToBuildLevel0 = "timeToBuildLevelZero"; //int

    private static final String BUILDING_TABLE_CREATE = "CREATE TABLE " + BUILDING_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_BUILDING_building_id + " INTEGER, " +
            KEY_BUILDING_level + " INTEGER, " +
            KEY_BUILDING_amountOfEffectByLevel + " INTEGER, " +
            KEY_BUILDING_amountOfEffectLevel0 + " INTEGER, " +
            KEY_BUILDING_building + " INTEGER, " +
            KEY_BUILDING_effect + " TEXT, " +
            KEY_BUILDING_gasCostByLevel + " INTEGER, " +
            KEY_BUILDING_gasCostLevel0 + " INTEGER, " +
            KEY_BUILDING_imageUrl + " TEXT, " +
            KEY_BUILDING_mineralCostByLevel + " INTEGER, " +
            KEY_BUILDING_mineralCostLevel0 + " INTEGER, " +
            KEY_BUILDING_name + " TEXT, " +
            KEY_BUILDING_timeToBuildByLevel + " INTEGER, " +
            KEY_BUILDING_timeToBuildLevel0 + " INTEGER)";

    // ============== BUILDING STATE ===================

    public static final String BUILDING_STATE_TABLE_NAME = "BuildingState";
    public static final String KEY_BUILDING_STATE_building_id = "building_id";
    public static final String KEY_BUILDING_STATE_building = "building";
    public static final String KEY_BUILDING_STATE_date_construction = "date_construction";

    private static final String BUILDING_STATE_TABLE_CREATE = "CREATE TABLE " + BUILDING_STATE_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_BUILDING_STATE_building_id + " TEXT, " +
            KEY_BUILDING_STATE_building + " TEXT, " +
            KEY_BUILDING_STATE_date_construction + " TEXT);";

    // ============== SEARCH ===================

    public static final String SEARCH_TABLE_NAME = "Search";
    public static final String KEY_SEARCH_search_id = "search_id";
    public static final String KEY_SEARCH_level = "level"; //int
    public static final String KEY_SEARCH_building = "building"; //boolean
    public static final String KEY_SEARCH_amountOfEffectByLevel = "amountOfEffectByLevel"; //int
    public static final String KEY_SEARCH_amountOfEffectLevel0 = "amountOfEffectLevelZero"; //int
    public static final String KEY_SEARCH_effect = "effect"; //string
    public static final String KEY_SEARCH_gasCostByLevel = "gasCostByLevel"; //int
    public static final String KEY_SEARCH_gasCostLevel0 = "gasCostLevelZero"; //int
    public static final String KEY_SEARCH_mineralCostByLevel = "mineralCostByLevel"; //int
    public static final String KEY_SEARCH_mineralCostLevel0 = "mineralCostLevelZero"; //int
    public static final String KEY_SEARCH_name = "name"; //string
    public static final String KEY_SEARCH_timeToBuildByLevel = "timeToBuildByLevel"; //int
    public static final String KEY_SEARCH_timeToBuildLevel0 = "timeToBuildLevelZero"; //int

    private static final String SEARCH_TABLE_CREATE = "CREATE TABLE " + SEARCH_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_SEARCH_search_id + " INTEGER, " +
            KEY_SEARCH_level + " INTEGER, " +
            KEY_SEARCH_amountOfEffectByLevel + " INTEGER, " +
            KEY_SEARCH_amountOfEffectLevel0 + " INTEGER, " +
            KEY_SEARCH_building + " INTEGER, " +
            KEY_SEARCH_effect + " TEXT, " +
            KEY_SEARCH_gasCostByLevel + " INTEGER, " +
            KEY_SEARCH_gasCostLevel0 + " INTEGER, " +
            KEY_SEARCH_mineralCostByLevel + " INTEGER, " +
            KEY_SEARCH_mineralCostLevel0 + " INTEGER, " +
            KEY_SEARCH_name + " TEXT, " +
            KEY_SEARCH_timeToBuildByLevel + " INTEGER, " +
            KEY_SEARCH_timeToBuildLevel0 + " INTEGER)";

    // ============== SEARCH STATE ===================

    public static final String SEARCH_STATE_TABLE_NAME = "SearchState";
    public static final String KEY_SEARCH_STATE_search_id = "search_id";
    public static final String KEY_SEARCH_STATE_searching = "searching";
    public static final String KEY_SEARCH_STATE_date_searching = "date_searching";

    private static final String SEARCH_STATE_TABLE_CREATE = "CREATE TABLE " + SEARCH_STATE_TABLE_NAME + " (" +
            KEY_ID + " TEXT, " +
            KEY_SEARCH_STATE_search_id + " TEXT, " +
            KEY_SEARCH_STATE_searching + " TEXT, " +
            KEY_SEARCH_STATE_date_searching + " TEXT);";

    public OuterSpaceManagerDB(Context context) {
        super(context, Environment.getExternalStorageDirectory() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(BUILDING_TABLE_CREATE);
        db.execSQL(BUILDING_STATE_TABLE_CREATE);
        db.execSQL(SEARCH_TABLE_CREATE);
        db.execSQL(SEARCH_STATE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // TODO : Create back of data before erase List<ofTables>
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + BUILDING_STATE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SEARCH_STATE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SEARCH_TABLE_NAME);
            onCreate(db);
        }
    }
}
