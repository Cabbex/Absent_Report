package com.casperbjork.absentreport;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RESTService {
    private Context setContext;
    private RequestQueue requestQueue;
    private String baseAdress;
    private static RESTService instance;

    private RESTService(Context context){
        this.setContext = context;
        this.requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        this.baseAdress = "http://10.0.2.2:8080/absentreportbackend/api";
    }

    public static synchronized RESTService getInstance(Context context) {
        if (instance == null){
            instance = new RESTService(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (this.requestQueue == null)
            this.requestQueue = Volley.newRequestQueue(setContext.getApplicationContext());

        return this.requestQueue;
    }

    public String getBaseAdress(){
        return this.baseAdress;
    }
}
