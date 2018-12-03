package visconsulting.simplegps;

import android.app.Activity;
import android.os.Bundle;

public class loginManager extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_manager);

        //myPreferences = getSharedPreferences("visconsulting.simplegpsMine", Context.MODE_PRIVATE);
        //  myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //  myEditor = myPreferences.edit();

        //emailView =  findViewById(R.id.editEmail);

        //  String myAccount = myPreferences.getString(getString(R.string.myAccount),"anybody@anydomain.com" );
        //emailView.setText(myAccount);
    }
}
