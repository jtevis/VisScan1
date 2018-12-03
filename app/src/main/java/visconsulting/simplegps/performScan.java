package visconsulting.simplegps;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import static android.view.WindowManager.*;

public class performScan extends Activity {

    double lat;
    double lon;
    String scannedData;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    TextView dateTimeView;
    String myAccount;
    String GTIN;
    String Lot;
    String type;
    String myUUID;
    String manufacturer;
    String notes;

    private static final String TAG = "performScan";
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

    DatabaseHelper mDatabaseHelper = null;
    SQLiteDatabase myDb;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perform_scan);

//Remove notification bar
        this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        //get lat and lon
        GpsTracker g = new GpsTracker(getApplicationContext());

        Location l = g.getLocation();

        if (l != null)

        {
            lat = l.getLatitude();
            lon = l.getLongitude();
        }else{
            lat=0;
            lon=0;
        }


        myAccount = "jwtevis@vis4ag.com";

        //Populate results
        TextView accountView =  findViewById(R.id.editAccount);
        accountView.setText (myAccount);
        TextView typeView =  findViewById(R.id.editType);
        typeView.setText ("GS1-128");

        TextView latView = (TextView) findViewById(R.id.editLat);
        TextView lonView = (TextView) findViewById(R.id.editLon);
        latView.setText (Double.toString(lat));
        lonView.setText (Double.toString(lon));

        //Create timestamp
        dateTimeView = (TextView) findViewById(R.id.editDateTime);
        calendar =Calendar.getInstance();
        //simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        simpleDateFormat = new SimpleDateFormat();
        Date = simpleDateFormat.format(calendar.getTime());
        dateTimeView.setText (Date);


        //Scan Barcode
        final Activity activity = this;

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setBeepEnabled(false);
        integrator.setCameraId(0);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();


          }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null) {
            scannedData = result.getContents();
            TextView scannedView = (TextView) findViewById(R.id.editData);
            scannedView.setText (scannedData);
            /*
            if (scannedData != null) {
                // Here we need to handle scanned data...
                new SendRequest().execute();


            }else {
                //Deactivate POST button
                Button postButton = (Button) findViewById(R.id.postBtn);
                postButton.setEnabled(false);

            } */
        }
        super.onActivityResult(requestCode, resultCode, data);

        type = result.getFormatName();
        TextView typeView = findViewById(R.id.editType);
        typeView.setText (type);

        TextView GTINView =  findViewById(R.id.editGTIN);
        TextView lotView =  findViewById(R.id.editLot);
        TextView manView =  findViewById(R.id.editManufacturer);

         //Create a UUID for the transfer event
                UUID uuid = UUID.randomUUID();
                myUUID = uuid.toString();

        //if type = CODE_128 parse to get GTIN and lot number

        int dataLength = scannedData.length();


        if (type.equals("CODE_128") && dataLength > 20){
            GTIN = scannedData.substring (2,16);
            GTINView.setText(GTIN);
            Lot = scannedData.substring(18,dataLength);
            lotView.setText(Lot);
            manufacturer = "TBD";
            //Need to add parser to extract manufacturer for GTIN
            manView.setText(manufacturer);
        }else{
            GTINView.setText("");
            lotView.setText("");
            manufacturer="unknown";
            manView.setText(manufacturer);
        }
    }
    public class SendRequest extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){}
        Intent intent = new Intent();

        protected String doInBackground(String... arg0) {

            String myAccount = "jwtevis@vis4ag.com";

            try{

                URL url = new URL("https://script.google.com/macros/s/AKfycbyIrllY21-7-8lqE_nFU3jPF9KixUTQEqdaC0IqVXe9BSRUJDg/exec");

                //https://script.google.com/macros/s/AKfycbyIrllY21-7-8lqE_nFU3jPF9KixUTQEqdaC0IqVXe9BSRUJDg/exec
                JSONObject postDataParams = new JSONObject();

                /*Create a UUID for the transfer event
                UUID uuid = UUID.randomUUID();
                myUUID = uuid.toString(); */

                String id ="1uQ7l1fmhMDYVhgpP-8phVCN4czJYlxEBzwPxbIojFD4";


                //Passing scanned code as parameter
                postDataParams.put("id",id);
                postDataParams.put("account",myAccount);
                postDataParams.put("timeStamp",Date);
                postDataParams.put("latitude",lat);
                postDataParams.put("longitude",lon);
                postDataParams.put("type",type);
                postDataParams.put("manufacturer",manufacturer);
                postDataParams.put("GTIN",GTIN);
                postDataParams.put("lot",Lot);
                postDataParams.put("sdata",scannedData);
                postDataParams.put("UUID",myUUID);
                postDataParams.put("Notes",notes);



                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(30000 /* milliseconds */);
                conn.setConnectTimeout(30000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                URL currentURL = conn.getURL();
                int WifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Larry",
                    Toast.LENGTH_LONG).show();
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    public void onClickMap(View v) {

        if (v.getId() == R.id.mapBtn) {
            Intent i = new Intent(performScan.this, newGTINMap.class);
            i.putExtra("lot number", Lot);
            i.putExtra("latitude", lat);
            i.putExtra("longitude", lon);
            startActivity(i);
        }
    }

    public void onClickSave(View v) {

        if (v.getId() == R.id.saveBtn) {
            //The first time this is called it will create the database
            if (mDatabaseHelper == null) {
                mDatabaseHelper = new DatabaseHelper(this);
            }
            myDb= mDatabaseHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, myAccount);
            contentValues.put(COL3, Date);
            contentValues.put(COL4, lat);
            contentValues.put(COL5, lon);
            contentValues.put(COL6, type);
            contentValues.put(COL7, manufacturer);
            contentValues.put(COL8, GTIN);
            contentValues.put(COL9, Lot);
            contentValues.put(COL10, scannedData);
            contentValues.put(COL11, myUUID);
            contentValues.put(COL12, notes);

            long result = myDb.insert(TABLE_NAME, null, contentValues);
           // mDatabaseHelper.addData();
            myDb.close();

            /*Intent i = new Intent(performScan.this, DatabaseHelper.class);
            i.putExtra("account", myAccount);
            i.putExtra("Time stamp", Date);
            i.putExtra("latitude", lat);
            i.putExtra("longitude", lon);
            i.putExtra("type", type);
            i.putExtra("manufacturer", manufacturer);
            i.putExtra("GTIN", GTIN);
            i.putExtra("lot number", Lot);
            i.putExtra("Barcode Data", scannedData);
            i.putExtra("UUID", myUUID);
            i.putExtra("Notes", notes);
            startActivity(i);*/


        }
    }
    public void onClickPost(View v) {
        if (v.getId() == R.id.postBtn) {

            if (scannedData != null) {
                // Here we need to handle scanned data...
                new SendRequest().execute();
            }
        }
    }

}

