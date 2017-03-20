package ke.co.stashare.reloader;

/**
 * Created by Ken Wainaina on 10/03/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class HideShowAndroidEditTextPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hide_show_android_edittext_password);

        ShowHidePasswordEditText showHidePasswordEditText = (ShowHidePasswordEditText) findViewById(R.id.android_hide_show_edittext_password);
        ShowHidePasswordEditText showHidePasswordEditText1 = (ShowHidePasswordEditText) findViewById(R.id.hide_show_edittext_password);
        ShowHidePasswordEditText showHidePasswordEditText3 = (ShowHidePasswordEditText) findViewById(R.id.hide_show_android_edittext_password);
    }
}
