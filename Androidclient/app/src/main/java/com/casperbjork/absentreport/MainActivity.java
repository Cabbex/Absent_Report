package com.casperbjork.absentreport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    User user;
    RESTService restService;
    Context mainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent userIntent = getIntent();
        mainActivityContext = this.getApplicationContext();
        restService = RESTService.getInstance(mainActivityContext);
        user = User.getInstance();

        if (!user.isAuthenticated()){
             Intent loginIntent = new Intent(this, LoginActivity.class);
             startActivity(loginIntent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        //Handles all the dates that is to be displayed.
        DateHandler dateHandler = new DateHandler();

        //Changing welcome message to include users name.
        if (user != null) {
            TextView welcomeMsg = (TextView) findViewById(R.id.textView_welcomeuser);
            welcomeMsg.setText("Välkommen " + user.getUsername() + ".");
        }

        //Changing the current date to the current date
        TextView dateToday = (TextView) findViewById(R.id.textView_DateToday);
        dateToday.setText(dateHandler.getDay(0) + " " + dateHandler.getMonth(0));

        //Changing date pickers
        CheckBox cb_1 = (CheckBox) findViewById(R.id.checkBox_datepicker1);
        CheckBox cb_2 = (CheckBox) findViewById(R.id.checkBox_datepicker2);
        CheckBox cb_3 = (CheckBox) findViewById(R.id.checkBox_datepicker3);
        cb_1.setText(dateHandler.getDay(1) + " " + dateHandler.getMonthCompact(1));
        cb_2.setText(dateHandler.getDay(2) + " " + dateHandler.getMonthCompact(2));
        cb_3.setText(dateHandler.getDay(3) + " " + dateHandler.getMonthCompact(3));

        getReasonInfo();


        super.onStart();
    }

    public void getReasonInfo(){
        //Creating a listerner that listen to the Success of the request
        Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String[] spinnerContents = new String[response.length()];
                    spinnerContents[0] = "Orsak";
                    for (int x = 1; x < response.length(); x++) {
                        spinnerContents[x] = response.getJSONObject(x).getString("status");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivityContext, R.layout.support_simple_spinner_dropdown_item, spinnerContents);

                    Spinner spinner = (Spinner) findViewById(R.id.spinner_reason);

                    spinner.setAdapter(adapter);
                } catch (Exception e){
                    Log.d("RESONINFO", "Message: "+ e.getMessage());
                }
            }
        };

        //Creating a listener that listens for the fail of the request
        Response.ErrorListener failedListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REASONINFO", error.toString());
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                this.restService.getBaseAdress() + "/statusInfo",
                null,
                successListener, failedListener);

        restService.getRequestQueue().add(request);
    }

    public void onSubmit(View view){
        CheckBox checkBox_datepicker1 = (CheckBox) findViewById(R.id.checkBox_datepicker1);
        CheckBox checkBox_datepicker2 = (CheckBox) findViewById(R.id.checkBox_datepicker2);
        CheckBox checkBox_datepicker3 = (CheckBox) findViewById(R.id.checkBox_datepicker3);
        Spinner spinner_reason = (Spinner) findViewById(R.id.spinner_reason);
        int returnDate = -1;
        DateHandler dateHandler = new DateHandler();

        if (checkBox_datepicker3.isChecked()){
            returnDate = dateHandler.convertToInt(checkBox_datepicker3.getText().toString());
        } else if (checkBox_datepicker2.isChecked()) {
            returnDate = dateHandler.convertToInt(checkBox_datepicker2.getText().toString());
        } else if (checkBox_datepicker1.isChecked()) {
            returnDate = dateHandler.convertToInt(checkBox_datepicker1.getText().toString());
        }

        String reason = (String) spinner_reason.getSelectedItem();

        //Creating a listerner that listen to the Success of the request
        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("SUBMITLEAVE", response.getString("Status"));
                    Toast toast = Toast.makeText(mainActivityContext, "Skickar anmälan...", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (Exception e ){
                    Log.d("SUBMITLEAVE", "Error in OnResponse: " + e.getMessage());
                }
            }
        };

        //Creating a listener that listens for the fail of the request
        Response.ErrorListener failedListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SUBMITLEAVE_ERROR", error.toString());
            }
        };

        Map<String, String> params = new HashMap<String, String>();
        params.put("returnDate", String.valueOf(returnDate));
        params.put("userID", String.valueOf(user.getUserID()));
        params.put("status", reason);

        JSONObject passingBody = new JSONObject(params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                this.restService.getBaseAdress() + "/submitLeave",
                passingBody,
                successListener, failedListener);

        restService.getRequestQueue().add(request);


    }
}
