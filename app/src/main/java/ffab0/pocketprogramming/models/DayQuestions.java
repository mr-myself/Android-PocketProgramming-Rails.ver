package ffab0.pocketprogramming.models;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DayQuestions {
    public int day;
    public int nextDay;
    public JSONArray questions;
    public int score;

    public DayQuestions(JSONObject response) {
        try {
            this.day = response.getInt("day");
            this.nextDay = response.getInt("next_day");
            this.questions = response.getJSONArray("questions");
            this.score = response.getInt("score");
        }
        catch (JSONException e) {
            Log.d("Question.java", e.toString());
        }
    }
}
