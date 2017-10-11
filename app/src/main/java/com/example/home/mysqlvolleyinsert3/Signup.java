package com.example.home.mysqlvolleyinsert3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Signup extends Activity {

    EditText email,username,password,mobile;
    TextView signup;
    String url = "http://r3mm1k5.net/database2/index.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        email= (EditText) findViewById(R.id.email);
        username= (EditText) findViewById(R.id.user);
        password= (EditText) findViewById(R.id.pass);
        mobile= (EditText) findViewById(R.id.mob);
        signup = (TextView) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString();
                String suser = username.getText().toString();
                String spass = password.getText().toString();
                String smob = mobile.getText().toString();
                if(semail.isEmpty() ||suser.isEmpty()||spass.isEmpty()||smob.isEmpty()){

                    Toast.makeText(Signup.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }else {


                    signup(semail,suser,spass,smob);
                    SharedPreferences preferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString("username",suser);
                    editor.putString("password",spass);
                    editor.putString("mobile",smob);
                    editor.putString("email",semail);
                    editor.commit();
                    show();

                }

            }
        });

    }

    public void show(){

        Intent intent = new Intent(Signup.this,Profile.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void signup(final String email, final String username,final String password, final String mobile){

        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(Signup.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("email",email);
                stringMap.put("username",username);
                stringMap.put("password",password);
                stringMap.put("mobile",mobile);
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);

    }

}