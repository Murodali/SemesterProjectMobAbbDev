package com.example.semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username,pwd;
    private Button logIn;
    private ProgressBar Loading;
    private TextView reg;
    private TextView user,pass;
    private static String url ="http://104.248.51.94:8000/api/v1/api-token-auth/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            setContentView(R.layout.login_activity);

      SharedPreferences sp = getApplicationContext().getSharedPreferences("token",MODE_PRIVATE);
      String token = sp.getString("token","");
        if(token.isEmpty()) {
           setContentView(R.layout.login_activity);

       }else{
            //logIn.setEnabled(false);
//            setContentView(R.layout.login_activity);
           Intent logout = new Intent(getApplication(), Feed.class);
         logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logout);

        }

//        SharedPreferences sp = getApplicationContext().getSharedPreferences("token",MODE_PRIVATE);
////        String token = sp.getString("token","");
//        if(token.isEmpty())
//        {

//        }
//        else
//        {
//            setContentView(R.layout.login_activity);
//////            setContentView(R.layout.login_activity);
////            SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
////            SharedPreferences.Editor editor = sharedPreferences.edit();
////            editor.putString("token","");
////            editor.apply();
//            Intent intent = new Intent(this,LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            finish();
//            startActivity(intent);
//

 //       }


        username = (EditText)findViewById(R.id.emailaddress);
        pwd = (EditText)findViewById(R.id.password);
        logIn = (Button)findViewById(R.id.login);
        Loading =(ProgressBar)findViewById(R.id.loading);
        reg =(TextView)findViewById(R.id.register);
        user = (TextView)findViewById(R.id.textView2);
        pass = (TextView)findViewById(R.id.textView3);



        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPass = pwd.getText().toString().trim();

                if(!mUsername.isEmpty() || !mPass.isEmpty()){
                    Login(mUsername,mPass);
                }
                else{
                    username.setError("Please enter email");
                    pwd.setError("Please enter password");
                }
            }
        });

    }

    public void Login(String mUsername, String mpPass){
        Loading.setVisibility(View.VISIBLE);
        logIn.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Loading.setVisibility(View.VISIBLE);

                    SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token",response);
                    editor.putString("username",mUsername);
                    editor.putString("password",mpPass);
                    editor.apply();
                System.out.println(response.toString());
                reg.setText(response.toString());

                    OpenFeed();

                    Loading.setVisibility(View.GONE);

                    //JSONObject jsonObject = new JSONObject(response);
                    //String success = jsonObject.getString("success");
                    //JSONArray jsonArray = jsonObject.getJSONArray("login");

//                    if(success.equals("1")){
//                        for(int i =0;i<jsonArray.length();i++){
//                            JSONObject object = jsonArray.getJSONObject(i);
//
//                            String name = object.getString("name").trim();
//                            String email = object.getString("email").trim();
//
                          Toast.makeText(LoginActivity.this, "Succes Login. \nYour Username : "+mUsername+"\n ", Toast.LENGTH_SHORT).show();
//                        }
                  //  }

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //reg.setText(error.toString());
                System.out.println(error.toString());
                if(error instanceof ClientError){
                    Toast.makeText(LoginActivity.this, "Error : Incorrect Usermame or Email. Please try again"+error.toString(), Toast.LENGTH_SHORT).show();
                    Loading.setVisibility(View.GONE);

                }
                logIn.setVisibility(View.VISIBLE);
                logIn.setEnabled(true);

            }

        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",mUsername);
                params.put("password",mpPass);
                return params;
            }
            //@Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void OpenFeed(){
        Intent intent = new Intent(this,Feed.class);
        startActivity(intent);
    }

//    public void saveUsernamePasswprd(String mUsername,String mPass){
//        new PrefManager(this).saveLoginDetails(mUsername,mPass);
//    }

//    public void storeToken(){
//        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token",response);
//        editor.apply();
//
//    }
}