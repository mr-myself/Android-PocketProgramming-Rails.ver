package ffab0.pocketprogramming.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ffab0.pocketprogramming.models.Common;

public class NetWorkChecker {

    public static boolean check() {
        ConnectivityManager cm =  (ConnectivityManager) Common.getContext().getSystemService(Common.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if( info != null ){
            return info.isConnected();
        } else {
            return false;
        }
    }
}
