package com.casperbjork.absentreport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    RESTService restService;
    Context loginActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        restService = RESTService.getInstance(this);
        loginActivityContext = this.getApplicationContext();
    }

    public void onClickLogin(View view){

        TextView TVusername = (TextView) findViewById(R.id.input_Username);
        TextView TVpassword = (TextView) findViewById(R.id.input_Password);

        final String username = TVusername.getText().toString();
        final String password = TVpassword.getText().toString();

        //Creating a listerner that listen to the Success of the request
        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("AUTHENTICATEUSER", response.toString());
                try {
                    int userID = response.getInt("userID");
                    if (userID > 0) {
                        User.getInstance().authenticate(username, true, userID);
                        checkLogin();
                    } else {
                        Toast toast = Toast.makeText(loginActivityContext, "Misslyckat inloggnings försök", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (JSONException e) {
                    Log.d("AUTHENTICATEUSER", e.getMessage());
                    User.getInstance().authenticate(username, false, -1);

                }
            }
        };

        //Creating a listener that listens for the fail of the request
        Response.ErrorListener failedListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AUTHENTICATEUSER", "Error: " + error.toString());

                User.getInstance().authenticate(username, false, -1);
            }
        };

        //Creating the passing body in the request.
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        JSONObject passingBody = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, restService.getBaseAdress() + "/authenticate", passingBody,
                successListener, failedListener);

        restService.getRequestQueue().add(request);
    }

    public void checkLogin(){
        Intent userIntent = new Intent(this, MainActivity.class);

        if (User.getInstance().isAuthenticated())
            startActivity(userIntent);
    }
}
