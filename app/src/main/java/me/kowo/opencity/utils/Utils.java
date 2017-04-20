package me.kowo.opencity.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    public static String getCategoriesById(int... ids) {
        String result = "";
        for (int i = 0; i < ids.length; i++) {
            result += "cat[]=" + ids[i] + "&";
        }
        return result;
    }

    public static void setVisibilities(int visibility, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(visibility);
        }
    }

    public static void hideKeyboard(Activity activity) {

        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
