package com.welshcora.pathword;

import android.app.Application;

/**
 * Created by HWANG on 15. 9. 12..
 */
public class PathWord extends Application
{
    private static String mGlobalString;

    public static String getGlobalString()
    {
        return mGlobalString;
    }

    public void setGlobalString(String globalString)
    {
        this.mGlobalString = globalString;
    }
}