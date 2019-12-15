package com.cee.anhquang.easysleepcee;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static android.R.attr.data;

public class MassageActivity extends Activity {

    boolean connect = false;
    Button btnLed1, btnLed2, btnLed3, btnConnect;
    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONNECT = 2;
    private static final int MESSAGE_READ = 3;

    ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();
    BluetoothAdapter meuBluetoothAdapter = null;
    BluetoothDevice meuDevice = null;
    BluetoothSocket meuSocket = null;
    private static String MAC = null;

    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);

        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnLed1 = (Button) findViewById(R.id.btnLed1);
        btnLed2 = (Button) findViewById(R.id.btnLed2);
        btnLed3 = (Button) findViewById(R.id.btnLed3);
        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (meuBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo nao possui bluetooth", Toast.LENGTH_LONG).show();
        } else if (!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
                    ////desconectar
                    try {
                        meuSocket.close();
                        connect = false;
                        btnConnect.setText("Connect");
                        Toast.makeText(getApplicationContext(), "Bluetooth for disconnect", Toast.LENGTH_LONG).show();
                    } catch (IOException erro) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro :" + erro, Toast.LENGTH_LONG).show();
                    }
                } else {
                    ////conectar
                    Intent abreLista = new Intent(MassageActivity.this, ListDeviceActivity.class);
                    startActivityForResult(abreLista, SOLICITA_CONNECT);
                }
            }
        });
        btnLed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnLed1.setBackgroundColor(Color.GRAY);
                btnLed2.setBackgroundColor(Color.TRANSPARENT);
                btnLed3.setBackgroundColor(Color.TRANSPARENT);
                if (connect) {
                    connectedThread.enviar("7");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth chưa được kết nối", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLed1.setBackgroundColor(Color.TRANSPARENT);
                btnLed2.setBackgroundColor(Color.GRAY);
                btnLed3.setBackgroundColor(Color.TRANSPARENT);
                if (connect) {
                    connectedThread.enviar("8");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth chưa được kết nối", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLed1.setBackgroundColor(Color.TRANSPARENT);
                btnLed2.setBackgroundColor(Color.TRANSPARENT);
                btnLed3.setBackgroundColor(Color.GRAY);
                if (connect) {
                    connectedThread.enviar("9");
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth chưa được kết nối", Toast.LENGTH_LONG).show();
                }
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_READ){
                    String recebidos = (String) msg.obj;
                    dadosBluetooth.append(recebidos);
                    int filmInfomacao = dadosBluetooth.indexOf("}");
                    if(filmInfomacao > 0){
                        String dadosCompletos = dadosBluetooth.substring(0, filmInfomacao);
                        int tamInformacao = dadosCompletos.length();
                        if(dadosBluetooth.charAt(0) =='{'){
                            String dadosFinais = dadosBluetooth.substring(1, tamInformacao);
                            Log.d("Recebidos", dadosFinais);
                            if(dadosFinais.contains("l1on")){
                                btnLed1.setText("LED 1 ON");
                            }else if(dadosFinais.contains("l1of")){
                                btnLed1.setText("LED 1 OFF");
                            }
                            if(dadosFinais.contains("l2on")){
                                btnLed2.setText("LED 2 ON");
                            }else if(dadosFinais.contains("l2of")){
                                btnLed2.setText("LED 2 OFF");
                            }
                            if(dadosFinais.contains("l3on")){
                                btnLed3.setText("LED 3 ON");
                            }else if(dadosFinais.contains("l3of")){
                                btnLed3.setText("LED 3 OFF");
                            }
                        }
                        dadosBluetooth.delete(0, dadosBluetooth.length());
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SOLICITA_ATIVACAO :
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "0 bluetooth foi ativado", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), "0 bluetooth nao foi ativado, o app sera", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case SOLICITA_CONNECT:
                if(resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(ListDeviceActivity.ENDERECO_MAC);
                    //Toast.makeText(getApplicationContext(),"MAC FINAL :" + MAC, Toast.LENGTH_LONG).show();
                    meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC);
                    try{
                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);
                        meuSocket.connect();
                        connect = true;
                        connectedThread = new ConnectedThread(meuSocket);
                        connectedThread.start();
                        btnConnect.setText("Đang kết nối");
                        Toast.makeText(getApplicationContext(),"Đã Kết Nối : " + MAC, Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        connect = false;
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro :" + erro, Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG).show();

                }
        }
    }
    private class ConnectedThread extends Thread {

        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            //mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs

            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    String dadosBt = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }


        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {}
        }
    }
}
