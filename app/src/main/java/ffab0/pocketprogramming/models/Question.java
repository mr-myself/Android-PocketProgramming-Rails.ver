package ffab0.pocketprogramming.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Question {

    public int questionId;
    public int day;
    public JSONObject question;
    public JSONArray choices;
    public int answer;
    public String explanation;
    public int nextId;

    public Question(JSONObject response) {
        try {
            this.questionId = response.getInt("question_id");
            this.day = response.getInt("day");
            this.question = response.getJSONObject("question");
            this.choices = response.getJSONArray("choices");
            this.answer = response.getInt("answer");
            this.explanation = response.getString("explanation");
            this.nextId = response.getInt("next_id");
        }
        catch (JSONException e) {
            Log.d("Question.java", e.toString());
        }
    }
}
