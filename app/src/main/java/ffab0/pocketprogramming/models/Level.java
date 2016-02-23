package ffab0.pocketprogramming.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Level {

    public String rubyLevel;
    public String railsLevel;

    public Level(JSONObject response) {
        try {
            this.rubyLevel = response.getString("ruby_level");
            this.railsLevel = response.getString("rails_level");
        }
        catch (JSONException e) {
            Log.d("Level.java", e.toString());
        }
    }
}
