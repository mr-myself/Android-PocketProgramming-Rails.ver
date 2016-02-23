package ffab0.pocketprogramming.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Review {

    public int day;
    public int score;

    public Review(JSONObject response) {
        try {
            this.day = response.getInt("day");
            this.score = response.getInt("score");
        } catch (JSONException e) {
            Log.d("Review.java", e.toString());
        }
    }
}
