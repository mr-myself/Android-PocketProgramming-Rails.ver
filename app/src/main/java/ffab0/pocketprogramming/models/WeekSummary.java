package ffab0.pocketprogramming.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.fragments.ResultAnalysisFragment;

public class WeekSummary {
    public JSONArray ok;
    public JSONArray no;

    public WeekSummary(JSONObject response) {
        try {
            this.ok = response.getJSONArray("ok");
            this.no = response.getJSONArray("no");
        }
        catch (JSONException e) {
            Log.d("Question.java", e.toString());
        }
    }
}
