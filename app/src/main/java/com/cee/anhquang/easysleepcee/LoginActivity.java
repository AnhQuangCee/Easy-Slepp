package com.cee.anhquang.easysleepcee;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by DAQ on 9/30/2017.
 */

public class LoginActivity extends Activity {
    private Button btnLogin;
    private TextView txtRegister;
    private String userName, passWord;
    Database database;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Delete toolbar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //Intent to menu layout
        btnLogin = (Button) findViewById(R.id.btnlogin);
        txtRegister =(TextView) findViewById(R.id.txtRegister);
        final EditText edtUsernameLg = (EditText) findViewById(R.id.edtUsernameLg);
        final EditText edtPasswordLg = (EditText) findViewById(R.id.edtPasswordLg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namestr = edtUsernameLg.getText().toString();
                String passstr = edtPasswordLg.getText().toString();
                if(!namestr.isEmpty()&&!passstr.isEmpty()){
                    Boolean LoginSuccess = database.findData(namestr,passstr);
                    if(LoginSuccess){
                        Intent  intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this, "Ten tai khoan va mk khong duoc de trong", Toast.LENGTH_SHORT).show();
            }
        });

        //Open Register Activity
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setTitle("REGISTER");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_register);

                //Anh xa
                final EditText edtUsername = (EditText)dialog.findViewById(R.id.edtUsername);
                final EditText edtPassword = (EditText)dialog.findViewById(R.id.edtPassword);
                final EditText edtConfirmPassword = (EditText)dialog.findViewById(R.id.edtComfirmPassword);
                Button btnCancle = (Button)dialog.findViewById(R.id.btnCancle);
                Button btnOkie = (Button)dialog.findViewById(R.id.btnOkie);

                btnOkie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nameStr = edtUsername.getText().toString();
                        String passStr = edtPassword.getText().toString();
                        String pass2Str = edtConfirmPassword.getText().toString();

                        if(!nameStr.isEmpty() && !passStr.isEmpty()){
                            if(passStr.equals(pass2Str)){
                                Boolean REGSuccess = database.addData(nameStr,passStr);
                                if (REGSuccess)
                                {
                                    Toast.makeText(LoginActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Register fail", Toast.LENGTH_SHORT).show();
                                    edtUsername.setText("");
                                    edtPassword.setText("");
                                    edtConfirmPassword.setText("");
                                }
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Confirm password is not true", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                        }
                        userName = edtUsername.getText().toString().trim();
                        passWord = edtPassword.getText().toString().trim();

                        edtPasswordLg.setText(passWord);
                        edtUsernameLg.setText(userName);

                        dialog.cancel();
                    }
                });
                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        //Database
        database = new Database(this);
    }

}
