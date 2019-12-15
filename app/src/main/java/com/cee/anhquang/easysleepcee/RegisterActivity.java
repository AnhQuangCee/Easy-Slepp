//package com.cee.anhquang.easysleepcee;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
///**
// * Created by DAQ on 10/20/2017.
// */
//
//public class RegisterActivity extends Activity{
//
//    EditText edtUsername, edtPassword, edtCofirmPassword;
//    Button btnOkie;
//    Database database;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //Delete Toolbar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_register);
//
//        //Anh xa
//        edtUsername = (EditText) findViewById(R.id.edtUsername);
//        edtPassword = (EditText) findViewById(R.id.edtPassword);
//        edtCofirmPassword = (EditText) findViewById(R.id.edtComfirmPassword);
//        btnOkie = (Button) findViewById(R.id.btnOkie);
//        database = new Database(this);
//        ClickOkie();
//    }
//    //Register Member
//    public void ClickOkie(){
//        btnOkie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nameStr = edtUsername.getText().toString();
//                String passStr = edtPassword.getText().toString();
//                String pass2Str = edtCofirmPassword.getText().toString();
//
//                if(!nameStr.isEmpty() && !passStr.isEmpty()){
//                    if(passStr.equals(pass2Str)){
//                        Boolean REGSuccess = database.addData(nameStr,passStr);
//                        if (REGSuccess)
//                        {
//                            Toast.makeText(RegisterActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                        else{
//                            Toast.makeText(RegisterActivity.this, "Register fail", Toast.LENGTH_SHORT).show();
//                            edtUsername.setText("");
//                            edtPassword.setText("");
//                            edtCofirmPassword.setText("");
//                        }
//                    }
//                    else {
//                        Toast.makeText(RegisterActivity.this, "Confirm password is not true", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(RegisterActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }show
//}
