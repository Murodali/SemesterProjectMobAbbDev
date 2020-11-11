package com.example.semesterproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;

    PrefManager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String mUsername, String mPass) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", mUsername);
        editor.putString("password", mPass);
        editor.apply();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("username", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }
}
