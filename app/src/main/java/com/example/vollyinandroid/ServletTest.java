package com.example.vollyinandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ServletTest extends AppCompatActivity
{
    EditText reg_name,reg_email,reg_pass,reg_gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servlet_test);

        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_pass = (EditText) findViewById(R.id.reg_pass);
        reg_gender = (EditText) findViewById(R.id.reg_gender);


        Log.d("Creation","onCreate() is being executed");
    }

    public void registerUser(View view)
    {
        //get the value
        String name1 =reg_name.getText().toString();
        String email1 = reg_email.getText().toString();
        String pass1 = reg_pass.getText().toString();
        String gender1 = reg_gender.getText().toString();//now this stored(in request obj) value go to the api using volly library which we have created in netbeans (ServletTest)
        System.out.println(name1);

        RequestQueue requestQueue=Volley.newRequestQueue(this);

//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Loading..");
//        builder.setMessage("Please wait..");
//        builder.show();
//        AlertDialog alertDialog = builder.create();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loding");
        progressDialog.setMessage("wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://192.168.1.17:9494/Testing/ServletTest",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //alertDialog.hide();
                        progressDialog.hide();
                        Log.d("responce","onResponce() is being executed");
                        Toast.makeText(ServletTest.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //alertDialog.hide();
                        progressDialog.hide();
                        Log.d("error","oneError() is being executed");
                        Toast.makeText(ServletTest.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                })

//
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hm = new HashMap<String,String>();
                hm.put("key_name", name1);
                hm.put("key_email", email1);
                hm.put("key_pass", pass1);
                hm.put("key_gender", gender1);
                return  hm;
            }
        };

        //time out code
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


       requestQueue.add(stringRequest);
    }
}