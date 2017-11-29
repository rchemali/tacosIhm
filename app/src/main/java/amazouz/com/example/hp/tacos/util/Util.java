package amazouz.com.example.hp.tacos.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chemali on 28/11/2017.
 */

public class Util {


    public static void paramsSound(Context context,boolean value){


        SharedPreferences preferences = context.getSharedPreferences("sound",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("silentMode", value);

        // Commit the edits!
        editor.commit();

    }


    public static boolean getCurrentParams(Context context){

        SharedPreferences preferences = context.getSharedPreferences("sound",0);

        boolean value = preferences.getBoolean("silentMode",false);

        return value;

    }


}
