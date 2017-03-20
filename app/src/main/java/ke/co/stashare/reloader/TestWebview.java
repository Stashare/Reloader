package ke.co.stashare.reloader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Ken Wainaina on 04/03/2017.
 */


public class TestWebview extends AppCompatActivity {


    private boolean mbErrorOccured = false;
    private boolean mbReloadPressed = false;
    protected WebView webView;
    final static String myBlogAddr = "http://www.safaricom.com/free";
    String myUrl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preserving);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(100);

        // Retrieve UI elements
        webView = ((WebView) findViewById(R.id.webView));
        //swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
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

        // Load a page
        webView.loadUrl(myBlogAddr);


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
            //To Prevent  Web page not available
            if (errorCode == -2) {
                view.loadData("", "", null);
                //To Show Alert Dialog//SplashScreenActivity.class is the Launcher Activity
                // In Case of Frament instead of Activity Replace ClassName.this and getApplicationContext() with getActivity()

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#7F02AE'><b>Error</b></font>"));
                builder.setMessage(Html.fromHtml("<font color='#120049'>An error occurred while trying to retrieve data.</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#7F02AE'><b>RETRY</b></font>"), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //SplashScreenActivity.class is your Launcher Activity
                        // In Case of Fragment instead of Activity Replace getApplicationContext()  with getActivity()

                        webView.loadUrl(failingUrl);
                        mbErrorOccured=true;


                    }
                });


                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }


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
