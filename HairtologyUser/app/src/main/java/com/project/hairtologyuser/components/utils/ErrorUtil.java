package com.project.hairtologyuser.components.utils;

public class ErrorUtil {

    public enum ErrorCode {
        NO_ACTIVITY_TO_START
    }

    public static String getErrorMessage(ErrorCode errorCode, Class<?> cls) {
        if (errorCode == ErrorCode.NO_ACTIVITY_TO_START) {
            return "No activity to start the " + cls.getSimpleName();
        }

        return "Encountered an error during runtime.";
    }

}
