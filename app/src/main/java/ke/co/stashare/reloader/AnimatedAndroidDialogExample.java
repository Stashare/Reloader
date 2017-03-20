package ke.co.stashare.reloader;

/**
 * Created by Ken Wainaina on 31/07/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class AnimatedAndroidDialogExample extends AppCompatActivity {

    NiftyDialogBuilder materialDesignAnimatedDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
    }


    public void animatedDialog3(View view) {
        View view1 = LayoutInflater.from(this).inflate(R.layout.custom_layout, null);

        materialDesignAnimatedDialog
                .withTitle("Confirm").withTitleColor("#000000")
                .withMessage("HANNAH NJERI WAINAINA").withMessageColor("#000000")
                .setCustomView(view1, view.getContext())
                .withDuration(700)
                .withDialogColor("#407cff")
                .isCancelableOnTouchOutside(false)
                .withEffect(Effectstype.Shake)
                .show();

        Button block = (Button)view1.findViewById(R.id.block);
        Button cancel = (Button)view1.findViewById(R.id.cancel);

        block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String me = "block me";
                Toast.makeText(getApplicationContext(),me, Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                materialDesignAnimatedDialog.dismiss();

            }
        });
    }



}
