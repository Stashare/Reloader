package ke.co.stashare.reloader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Spinner mSpinner;
    private static final String TAG = "Counter Tally";
    private static final String COUNTER_TAG = "Total Counter";

    private boolean mbErrorOccured = false;
    Toolbar mToolbar;
    private boolean mbReloadPressed = false;
    protected WebView webView;
    final static String myBlogAddr = "http://www.safaricom.com/free";
    final static String safcom = "http://www.stashare.co.ke";
    String myUrl;
    private ProgressBar progressBar;
    String limit=null;
    private ArrayList<String> load_limits;
    AutoCompleteTextView autoCompleteTextView;
    ImageView button_search;
    int limito; //Line 4
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        actionBar.setDisplayShowTitleEnabled(true);

        load_limits=new ArrayList<String>();

        load_limits.add("set load_limit");
        load_limits.add("load 3 times");
        load_limits.add("load 4 times");
        load_limits.add("load 5 times");
        load_limits.add("default");

        mSpinner=(Spinner)findViewById(R.id.spinner_rss);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        button_search=(ImageView)findViewById(R.id.buttonSend);


        SpinnerAdapter adapter = new SpinnerAdapter(actionBar.getThemedContext(), load_limits);
        mSpinner.setAdapter(adapter);



        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            mSpinner.setDropDownVerticalOffset(-116);
        }


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                limit = load_limits.get(i);
                Toast.makeText(MainActivity.this, limit, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(100);

        // Retrieve UI elements
        webView = ((WebView) findViewById(R.id.webView));
        //swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        webView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default


        if (!isNetworkAvailable()) { // loading offline
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        // Load the URLs inside the WebView, not in the external web browser
        webView.setWebViewClient(new MyWebViewClient());


        limito=10;
        counter=0;

        while (counter < limito) //Line 7
        {
            // Load a page
            webView.loadUrl(myBlogAddr);

            //Displaying counter on logcat
            Log.d(TAG, "counter: " + counter);
            counter++; //Line 10

        }

        //Displaying counter on logcat
        Log.d(COUNTER_TAG, "Total counter: " + counter);



    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            //swipeView.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);


            /**
            int i = 0;
            while (i <= 5)
            {

                // Load a page
                view.loadUrl(url);
                i++;
            }
             **/



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
            progressBar.setProgress(progress);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    }
