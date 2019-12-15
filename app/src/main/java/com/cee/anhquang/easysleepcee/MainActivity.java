package com.cee.anhquang.easysleepcee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import  android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;



public class MainActivity extends AppCompatActivity {

    private Button btnAlram;
    private Button btnMassage;
    private Button btnMusic;
    private Button btnSetting;
    private Toolbar toolbar;
    //private AlertDialog alertdialog;
    private RadioButton rdEnglish, rdvietNamese;
    private RadioGroup rdGroup;
    private Locale myLocale;
//    private BluetoothAdapter BA;
//    private Set<BluetoothDevice>pairedDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Bo toolbar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Chuyen sang form Alram - Massage - Music - Setting
        Anhxa();
        //Setup toolbar
        setSupportActionBar(toolbar);

        //Intent to Alram, Massage, Music, Setting
        btnAlram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlramActivity.class);
                startActivity(intent);
            }
        });

        btnMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MassageActivity.class);
                startActivity(intent);
            }
        });
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            //int i;
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                //OnOffBluetooth(i);
                //TurnOnBluetooth();
                //ConnectBluetooth();
            }
        });
    }


//    //Turn on Bluetooth
//    public void TurnOnBluetooth(){
//        if(!BA.isEnabled()){
//            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(turnOn,0);
//            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_SHORT).show();
//        }
//    }
//    //If you turn on Bluetooth
//    public void ConnectBluetooth(){
//        final Dialog dialog = new Dialog(this);
//        dialog.setTitle("Connect bluetooth");
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.activity_connectbluetooh);
//
//        //Anh xa
//        Button btnTurnOff = (Button)dialog.findViewById(R.id.btnTurnOff);
//        Button btnGetVisible = (Button)dialog.findViewById(R.id.btngetVisible);
//        final ListView lvBluetooth = (ListView)dialog.findViewById(R.id.lvBluetooth);
//
//        //Turn off Bluetooth
//        btnTurnOff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BA.disable();
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//
//        //Get Visible bluetooth devices
//        btnGetVisible.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                startActivityForResult(getVisible,0);
//            }
//        });
//        lvBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //ListView Bluetooth
//                pairedDevices = BA.getBondedDevices();
//                ArrayList list = new ArrayList();
//
//                for(BluetoothDevice bt:pairedDevices)
//                    list.add(bt.getName());
//                Toast.makeText(getApplicationContext(), "Showing Paired Device", Toast.LENGTH_SHORT).show();
//                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list);
//                lvBluetooth.setAdapter(adapter);
//            }
//        });
//    }

    //Anh xa
    public void Anhxa(){
        btnMassage = (Button) findViewById(R.id.btnMassage);
        btnAlram = (Button) findViewById(R.id.btnAlram);
        btnMusic = (Button) findViewById(R.id.btnMusic);
        btnSetting = (Button) findViewById(R.id.btnSetting);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        rdGroup =(RadioGroup)findViewById(R.id.rdGroup);
        //BA = BluetoothAdapter.getDefaultAdapter();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // Set languages setting in Toolbar - show Dialog (radio button)
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_setting:
                //final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setTitle("Languages setting");

                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.activity_alertdialog);
                dialog.setTitle("Languages setting");

                //View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_alertdialog, null);

                //Anh xa cho layout activity_alertdialog
                rdEnglish = (RadioButton)dialog.findViewById(R.id.rdEnglish);
                rdvietNamese = (RadioButton)dialog.findViewById(R.id.rdVietnamese);
                Button btnOkAlert = (Button)dialog.findViewById(R.id.btnOkAlert);

                btnOkAlert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(rdEnglish.isChecked()){
                            setLocal("en");
                        }
                        if(rdvietNamese.isChecked()){
                            setLocal("vi");
                        }
                    }
                });
                dialog.show();
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        if (rdEnglish.isChecked()){
//                            setLocal("en");
//                        }
//                        if (rdvietNamese.isChecked()){
//                            setLocal("vi");
//                        }
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.setView(view);
//                builder.show()
                break;
            case R.id.action_infomation:

                break;
        }
        return true;
    }

    public void setLocal(String language) {
        myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf,dm);
        Intent refresh = new Intent(this,MainActivity.class);
        startActivity(refresh);
    }
}

