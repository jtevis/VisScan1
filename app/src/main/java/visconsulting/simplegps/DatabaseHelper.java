package visconsulting.simplegps;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "BarCodes";
    private static final String COL1 ="Id";
    private static final String COL2 ="Account";
    private static final String COL3 ="TimeDate";
    private static final String COL4 ="Latitude";
    private static final String COL5 ="Longitude";
    private static final String COL6 ="Type";
    private static final String COL7 ="Manufacturer";
    private static final String COL8 ="GTIN";
    private static final String COL9 ="LotNumber";
    private static final String COL10 ="Data";
    private static final String COL11="UUID";
    private static final String COL12 ="Notes";

    double lat;
    double lon;
    String scannedData;
    String Date;
    String myAccount;
    String GTIN;
    String Lot;
    String type;
    String myUUID;
    String manufacturer;
    String notes;


   public DatabaseHelper(Context context){
       super (context, TABLE_NAME, null,1);

       //Get data from performScan class


      //Lot =getIntent().getExtras().getString("lot number").toString();
       //lat = (getIntent().getExtras().getDouble("latitude"));
       //lon = (getIntent().getExtras().getDouble("longitude"));

   }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
               COL2 + " TEXT," +
               COL3 + " TEXT," +
               COL4 + " DOUBLE," +
               COL5 + " DOUBLE," +
               COL6 + " TEXT," +
               COL7 + " TEXT,"  +
               COL8 + " TEXT," +
               COL9 + " TEXT," +
               COL10 + " TEXT," +
               COL11 + " TEXT," +
               COL12 + " TEXT)";
       db.execSQL(createTable);

       //Lot = "Dope";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL(" DROP IF TABLE EXISTS " + TABLE_NAME);
       onCreate(db);

    }
    public boolean addData (String item){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();
       contentValues.put(COL2, item);

       long result = db.insert(TABLE_NAME, null, contentValues);

       if (result == -1){
           return false;
        }else {
           return true;
        }
    }
}
