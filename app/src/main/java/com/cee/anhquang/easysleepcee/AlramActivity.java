package com.cee.anhquang.easysleepcee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by DAQ on 10/2/2017.
 */

public class AlramActivity extends Activity{
    private Button btnSethour;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bo toolbar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alram);

        //Chuyen sang form Sethour
        btnSethour = (Button) findViewById(R.id.btnSethour);

        btnSethour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlramActivity.this, AlramSethourActivity.class);
                startActivity(intent);
            }
        });
    }
}
