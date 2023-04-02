package com.project.hairtologyuser.components.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.project.hairtologyuser.components.app.AppConfig;
import com.project.hairtologyuser.models.UserModel;

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
    public void setCurrentUser(String uuid) {
        getSharedPreferences().edit().putString(PREF_USER, uuid).apply();
    }

    public String getCurrentUser() {
        return getSharedPreferences().getString(PREF_USER, null);
    }

}
