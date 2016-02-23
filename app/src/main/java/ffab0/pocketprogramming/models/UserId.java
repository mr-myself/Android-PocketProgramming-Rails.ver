package ffab0.pocketprogramming.models;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ffab0.pocketprogramming.environments.EnvironmentConstants;

public class UserId {
    private static String sID = null;
    private static final String POCKET_PROGRAMMING = "POCKET_PROGRAMMING";

    public synchronized static String id() {
        if (sID == null) {
            File installation = new File(Common.getContext().getFilesDir(), POCKET_PROGRAMMING);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }

    public static void postToServer() {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Method.POST, EnvironmentConstants.HOST_URL,
            new Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            },

            new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("UserID", error.toString());
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uuid", UserId.id());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
