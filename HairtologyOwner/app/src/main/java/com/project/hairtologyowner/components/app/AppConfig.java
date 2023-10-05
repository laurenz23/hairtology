package com.project.hairtologyowner.components.app;

import com.project.hairtologyowner.BuildConfig;

public class AppConfig {

    public static final String IDENTITY = BuildConfig.APPLICATION_ID;
    public static final class Api {
        public static final String BASE_URL = "https://hairtology-242e8-default-rtdb.asia-southeast1.firebasedatabase.app/";
    }
    public static final class Duration {
        public static final int MINIMUM = 3000;
        public static final int MAXIMUM = 5000;
    }

}
