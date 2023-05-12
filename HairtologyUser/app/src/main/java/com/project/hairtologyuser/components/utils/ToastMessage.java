package com.project.hairtologyuser.components.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

    public static void display(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
