package com.geniobits.apiproject;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApiApp extends Application {

    private static ApiApp mInstance;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public synchronized static ApiApp getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
