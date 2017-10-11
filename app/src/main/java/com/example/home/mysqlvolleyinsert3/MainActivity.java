package com.example.home.mysqlvolleyinsert3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    TextView signup,login;
    String pass,username;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signup = (TextView) findViewById(R.id.signup);
        email = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);
        login = (TextView) findViewById(R.id.login);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = email.getText().toString();
                pass = password.getText().toString();

                if (username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "fill details", Toast.LENGTH_SHORT).show();

                } else {

                    login(username, pass);
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
    }


    public void login(final String user, final String pass){

        url = "http://r3mm1k5.net/database2/signin.php?username="+user+"&password="+pass+"";
        Log.i("Hiteshurl",""+url);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String id = jsonObject1.getString("id");
                    String pass = jsonObject1.getString("password");
                    String username = jsonObject1.getString("username");
                    String email = jsonObject1.getString("email");
                    String phone = jsonObject1.getString("mobile");
                    SharedPreferences shared = getSharedPreferences("Mypref",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("id",id);
                    editor.putString("pass",pass);
                    editor.putString("username",username);
                    editor.putString("email",email);
                    editor.putString("phone",phone);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,Profile.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("HiteshURLerror",""+error);
            }
        });

        requestQueue.add(stringRequest);

    }
}