package pt.franciscorp.wit_challenge.Utils;

import android.util.Log;

import static pt.franciscorp.wit_challenge.Utils.Constants.debugVerbose;

public class Logger {

    private String tag;
    private boolean isActive;

    public Logger(String tag, boolean isActive) {
        this.tag = tag;
        this.isActive = isActive;
    }

    public void log(String msg){
        if(isActive)
            Log.d("DEBUG", msg);
    }

    public static void log(String tag,String msg){
        Log.d(tag, msg);
    }

    public static void crashLog(String tag,String msg, Exception exception){
        Log.d(tag, msg);
        Log.d(tag, exception.getMessage());
        if(debugVerbose)
            Log.d(tag, exception.toString());
    }
}
