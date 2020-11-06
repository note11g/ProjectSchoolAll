package com.note11.projectschoolall.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.note11.projectschoolall.model.UserModel;
import com.google.gson.Gson;

public class UserCache {

    public static SharedPreferences getShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, UserModel model){
        Gson gson = new Gson();
        String json = gson.toJson(model);
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("user",json).apply();
    }

    public static UserModel getUser(Context context){
        Gson gson = new Gson();
        return gson.fromJson(getShared(context).getString("user",""), UserModel.class);
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("user", null).apply();
    }

}
