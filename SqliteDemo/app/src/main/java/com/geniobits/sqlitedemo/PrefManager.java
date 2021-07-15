package com.geniobits.sqlitedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PrefManager {

    private final Context context;
    public ArrayList<Contact> contactArrayList = new ArrayList<>();
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final String CONTACTS = "CONTACTS";

    public PrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("MySP", Context.MODE_PRIVATE);
        editor = pref.edit();
        initializeContact();
    }

    public void initializeContact() {
        String contactsJson =  pref.getString(CONTACTS, null);
        if(contactsJson!=null){
            Gson gson = new Gson();
            contactArrayList = gson.fromJson(contactsJson, new TypeToken<ArrayList<Contact>>(){}.getType());
        }
    }

    public void updateSharedPref(){
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(contactArrayList, new TypeToken<ArrayList<Contact>>() {}.getType());
        if (! element.isJsonArray() ) {
            // fail appropriately
            Log.e("Pref", "Not in json Array Format");
        }
        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString(CONTACTS, jsonArray.toString());
        editor.commit();
    }


}
