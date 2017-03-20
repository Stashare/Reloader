package ke.co.stashare.reloader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Ken Wainaina on 12/03/2017.
 */

public class Bundles extends AppCompatActivity {

    private boolean mbErrorOccured = false;
    private boolean mbReloadPressed = false;
    Toolbar mToolbar;
    protected WebView webView;
    final static String bundlesUrl = "http://www.safaricom.com/bundles";
    final static String freeSaf = "http://www.safaricom.com/free";

    String myUrl;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundles);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.type_message_dark));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Bundles");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        // Retrieve UI elements
        webView = ((WebView) findViewById(R.id.webView2));
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
            Toast.makeText(this, "Network connection problem", Toast.LENGTH_LONG).show();

        }

        // Load the URLs inside the WebView, not in the external web browser
        webView.setWebViewClient(new MyWebViewClient());

        // Load a page
        webView.loadUrl(bundlesUrl);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bundles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            refresh();
        }
        else if (id == R.id.action_exit)  {
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);

    }

    public void refresh() {
        //do something
        // Load a page
        webView.loadUrl(freeSaf);

        //Toast.makeText(this,"refresh clicked",Toast.LENGTH_LONG).show();

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
