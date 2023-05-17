package com.project.hairtologyowner.components.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.project.hairtologyowner.components.app.AppConfig;
import com.project.hairtologyowner.models.OwnerModel;

public class Session {

    private static final String PREF_KEY = AppConfig.IDENTITY + ".SharedPreferences.Session";
    private static final String PREF_USER = "currentUser";

    private Context mContext;

    public Session(Context context) {
        mContext = context;
    }

    public SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    public void setCurrentUser(OwnerModel user) {
        String currentUser = new GsonBuilder().create().toJson(user);
        getSharedPreferences().edit().putString(PREF_USER, currentUser).apply();
    }

    public OwnerModel getCurrentUser() {
        String currentUser = getSharedPreferences().getString(PREF_USER, null);

        if (currentUser == null) {
            return null;
        }

        return new GsonBuilder().create().fromJson(currentUser, OwnerModel.class);
    }

    public void endSession() {
        setCurrentUser(null);
    }

}
