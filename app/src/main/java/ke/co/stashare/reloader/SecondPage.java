package ke.co.stashare.reloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

/**
 * Created by Ken Wainaina on 12/03/2017.
 */

public class SecondPage extends AppCompatActivity {

    NiftyDialogBuilder materialDesignAnimatedDialog;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String email_stash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppThemeB);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        setContentView(R.layout.plain_firstpage);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.type_message_dark));
        }



        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), FirstPage.class));
        }


        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
        animatedDialog3();
    }


    public void animatedDialog3() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.custom_key, null);

        materialDesignAnimatedDialog
                .withTitle("REGISTER").withTitleColor("#000000")
                .setCustomView(view1, getApplicationContext())
                .withDuration(700)
                .withDialogColor("#ffffff")
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Fall)
                .show();

        final ShowHidePasswordEditText Key = (ShowHidePasswordEditText)view1.findViewById(R.id.key);
        Button proceed = (Button)view1.findViewById(R.id.proceed);
        Button cancel = (Button)view1.findViewById(R.id.cancel);

        proceed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //getting Product Key from edit texts
                String key = Key.getText().toString().trim();


                //checking if email and passwords are empty
                if(TextUtils.isEmpty(key)){
                    Toast.makeText(SecondPage.this,"Please enter the product key",Toast.LENGTH_LONG).show();
                    return;
                }

                //if the email and password are not empty
                //displaying a progress dialog

                progressDialog.setMessage("validating, Please Wait...");
                progressDialog.show();

                email_stash="@stashare.co.ke";
                String email=null;
                email=key + email_stash;
                String password= null;
                password= key;
                //logging in the user
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SecondPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                //if the task is successfull
                                if(task.isSuccessful()){
                                    //start the profile activity
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), FirstPage.class));
                                }else{

                                    progressDialog.dismiss();
                                    Toast.makeText(SecondPage.this,"Problem encountered while validating the product key, please try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.exit(0);

            }
        });




    }




}

