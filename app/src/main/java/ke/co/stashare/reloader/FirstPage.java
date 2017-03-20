package ke.co.stashare.reloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ken Wainaina on 12/03/2017.
 */

public class FirstPage extends AppCompatActivity {

    private static final String TAG = "Counter Tally";
    private static final String COUNTER_TAG = "Total Counter";

    Toolbar mToolbar;
    NiftyDialogBuilder materialDesignAnimatedDialog;

    private boolean mbErrorOccured = false;
    private boolean mbReloadPressed = false;
    protected WebView webView;
    final static String myBlogAddr = "http://www.safaricom.com/free";
    final static String bundlesUrl = "http://www.safaricom.com/bundles";
    final static String redeemUrl = "http://www.safaricom.com/free/redeem";
    String myUrl;
    private ProgressBar progressBar;
    TextView redeem;
    TextView tally;
    int limito; //Line 4
    int counter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tool);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.type_message_dark));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);

        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);


        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //progressBar.setMax(100);

        // Retrieve UI elements
        webView = ((WebView) findViewById(R.id.webView));
        //swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setSupportZoom(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        //webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        //webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        //webView.getSettings().setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClientDemo());
        //webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default


        if (!isNetworkAvailable()) { // loading offline
            //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            Toast.makeText(this,"Network connection problem",Toast.LENGTH_LONG).show();

        }

        // Load the URLs inside the WebView, not in the external web browser
        webView.setWebViewClient(new MyWebViewClient());
        // Load a page
        webView.loadUrl(myBlogAddr);


        /**
        limito=5;
        counter=0;

        while (counter < limito)
        {
            // Load a page
            webView.loadUrl(myBlogAddr);

            //Displaying counter on logcat
            Log.d(TAG, "counter: " + counter);
            counter++; //Line 10

        }

        //Displaying counter on logcat
        Log.d(COUNTER_TAG, "Total counter: " + counter);
         **/

    }

    public void buttonClick(View view) {

        TextView redeem=(TextView)findViewById(R.id.redeem_text);
        TextView tally=(TextView)findViewById(R.id.tally);

        if(!isNetworkAvailable() )
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(FirstPage.this);
            builder.setCancelable(false);
            builder.setMessage("Redeem failed, please try again");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Redeeming");
            progressDialog.show();

            limito=7;
            counter=0;
            //webView.loadUrl(redeemUrl);

            while (counter < limito)
            {
                // Load a page
                webView.loadUrl(redeemUrl);
                // Load a page

                webView.reload();


                String count= String.valueOf(counter);
                String times=" times";
                String finalise= count+times;


                    redeem.setVisibility(View.VISIBLE);
                    tally.setVisibility(View.VISIBLE);
                    tally.setText(finalise);


                //Displaying counter on logcat
                Log.d(TAG, "counter: " + counter);

                counter++; //Line 10

            }

            //Displaying counter on logcat
            Log.d(COUNTER_TAG, "Total counter: " + counter);
            // Load a page

            progressDialog.hide();


            redeem.setVisibility(View.INVISIBLE);
            tally.setVisibility(View.INVISIBLE);

            webView.loadUrl(redeemUrl);


            //String count= String.valueOf(counter);

        }



    }
    public void animatedDialog3() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.custom_layout, null);

        materialDesignAnimatedDialog
                .withTitle("Confirm").withTitleColor("#f5f5f5")
                .withMessage("Exiting the app...").withMessageColor("#f5f5f5")
                .setCustomView(view1, getApplicationContext())
                .withDuration(700)
                .withDialogColor("#ff242f")
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Shake)
                .show();

        Button exit = (Button)view1.findViewById(R.id.block);
        Button cancel = (Button)view1.findViewById(R.id.cancel);

        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                System.exit(0);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                materialDesignAnimatedDialog.dismiss();

            }
        });
    }



    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            //swipeView.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
            //progressBar.setProgress(100);


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            myUrl = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, final String failingUrl) {


        }
    }

    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            //progressBar.setProgress(progress);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refresh();
        }else if (id == R.id.action_exit)  {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        //do something
        webView.loadUrl(myBlogAddr);

        //Toast.makeText(this,"refresh clicked",Toast.LENGTH_LONG).show();

}

    public void exit(){
        animatedDialog3();
        //Toast.makeText(this,"exit clicked",Toast.LENGTH_LONG).show();

    }

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }


}
