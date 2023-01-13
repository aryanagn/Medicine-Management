package com.example.pccmedicare20;

import android.app.Application;
public class UserSettings extends Application {
    //constructor class for settingsmainactivity
    //non changeable, set string value
    //setting themes
    public static final String PREFERENCES = "preferences";
    public static final String CUSTOM_THEME = "customTheme";
    public static final String LIGHT_THEME = "lightTheme";
    public static final String DARK_THEME = "darkTheme";

    private String customTheme; //custom theme creation for light/dark mode changes

    public String getCustomTheme()
    {
        return customTheme;
    }

    public void setCustomTheme(String customTheme)
    {
        this.customTheme = customTheme;
    }
}