package com.cee.anhquang.easysleepcee;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

/**
 * Created by DAQ on 11/13/2017.
 */

public class AlertActivity extends Activity {
    Button btnOkAlert;
    Button btnCancelAlert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);

        btnOkAlert =(Button)findViewById(R.id.btnOkAlert);
        btnCancelAlert=(Button)findViewById(R.id.btnCancelAlret);
    }
}
