package ffab0.pocketprogramming.models;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import ffab0.pocketprogramming.BuildConfig;
import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.fragments.LevelUpDialogFragment;

public class LevelManager {
    public Level mLevel;

    public void updateLevel(final FragmentManager fragmentManager, final SharedPreferences levelData) {
        final SharedPreferences.Editor editor = levelData.edit();
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "?uuid=" + UserId.id() + "&lang=" + Common.getLanguage() + "&version=" + BuildConfig.VERSION_NAME,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mLevel = new Level(response);
                        String currentRubyLevel = levelData.getString("ruby_level", "e");
                        String currentRailsLevel = levelData.getString("rails_level", "e");

                        if (!mLevel.rubyLevel.equals(currentRubyLevel)) {
                            if (levelUpCheck(currentRubyLevel, mLevel.rubyLevel)) {
                                DialogFragment levelUpDialogFragment = LevelUpDialogFragment.newInstance("Ruby", currentRubyLevel, mLevel.rubyLevel);
                                levelUpDialogFragment.show(fragmentManager, null);
                            }
                            editor.putString("ruby_level", mLevel.rubyLevel);
                        }
                        if (!mLevel.railsLevel.equals(currentRailsLevel)) {
                            if (levelUpCheck(currentRailsLevel, mLevel.railsLevel)) {
                                DialogFragment levelUpDialogFragment = LevelUpDialogFragment.newInstance("Rails", currentRailsLevel, mLevel.railsLevel);
                                levelUpDialogFragment.show(fragmentManager, null);
                            }
                            editor.putString("rails_level", mLevel.railsLevel);
                        }
                        editor.commit();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LevelManager.update", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }

    private boolean levelUpCheck (String currentLevel, String newLevel) {
       switch (currentLevel) {
           case "e":
               return (newLevel.equals("e") ? false : true);
           case "d":
               return ((newLevel.equals("e") || newLevel.equals("d")) ? false : true);
           case "c":
               return ((newLevel.equals("e") || newLevel.equals("d") || newLevel.equals("c")) ? false : true);
           case "b":
               return ((newLevel.equals("e") || newLevel.equals("d") || newLevel.equals("c") || newLevel.equals("b")) ? false : true);
           case "a":
               return false;
           default:
               return false;
       }
    }
}
