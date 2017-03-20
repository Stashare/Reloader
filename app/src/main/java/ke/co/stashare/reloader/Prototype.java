package ke.co.stashare.reloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Ken Wainaina on 04/03/2017.
 */

public class Prototype extends AppCompatActivity

{

    private boolean mbErrorOccured = false;
    private boolean mbReloadPressed = false;
    protected WebView webView;
    final static String myBlogAddr = "http://www.safaricom.com/free";
    String myUrl;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
