package visconsulting.simplegps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   // private SharedPreferences myPreferences;
   // private SharedPreferences.Editor myEditor;

    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create preference storage method
     // myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       // myPreferences = getSharedPreferences("visconsulting.simplegpsMine", Context.MODE_PRIVATE);
       // myEditor = myPreferences.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

        {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location permission is already granted", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Internet permission is already granted", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET}, 10);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Internet permission is already granted", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 10);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Internet permission is already granted", Toast.LENGTH_SHORT).show();
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 10);
            }
        }

    }

    public void onClickScan(View v) {

        if (v.getId() == R.id.scanBtn) {
            Intent i = new Intent(MainActivity.this, performScan.class);
            startActivity(i);

        }
    }
    public void onClickPic(View v) {

        if (v.getId() == R.id.picBtn) {
            Intent i = new Intent(MainActivity.this, picture.class);
            startActivity(i);

        }
    }
    public void onClickAccount(View v) {

        if (v.getId() == R.id.accountBtn) {
            Intent i = new Intent(MainActivity.this, loginManager.class);
            startActivity(i);

        }
    }
    public void onClickPref(View v) {

        if (v.getId() == R.id.prefBtn2) {
            Intent i = new Intent(MainActivity.this, preference.class);
            startActivity(i);

        }
    }

}
