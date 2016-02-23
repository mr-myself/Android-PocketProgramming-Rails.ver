package ffab0.pocketprogramming.models;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ffab0.pocketprogramming.environments.EnvironmentConstants;


public class TrueOrFalse {

    public static void postToServer(final int questionId, final boolean result) {
        final String UUID = UserId.id();

        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Method.POST, EnvironmentConstants.HOST_URL,
            new Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("TrueOrFalse", "SUCCESS");
                }
            },

            new ErrorListener() {
                @Override public void onErrorResponse(VolleyError error) {
                    Log.d("TrueOrFalse", error.toString());
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("question_id", String.valueOf(questionId));
                params.put("result", String.valueOf(result));
                params.put("uuid", UUID);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
