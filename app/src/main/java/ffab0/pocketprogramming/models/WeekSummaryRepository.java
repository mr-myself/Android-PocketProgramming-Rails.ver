package ffab0.pocketprogramming.models;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.managers.NoSummaryActivity;
import ffab0.pocketprogramming.managers.SummaryActivity;
import ffab0.pocketprogramming.viewModel.SummaryViewModel;

public class WeekSummaryRepository {
    public JSONArray mAllWeekSummary;

    public void fetch(final LayoutInflater inflater, final View rootView, final int week) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WeekSummary mWeekSummary = new WeekSummary(response);
                        SummaryViewModel.analysisList(inflater, rootView, mWeekSummary, week);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("WeekSummaryRepo", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }

    public void fetchAll() {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "/api/analyses/summary?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mAllWeekSummary = response;
                        if (mAllWeekSummary.length() == 0) {
                            Intent intent = new Intent(Common.getContext(), NoSummaryActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            Common.getContext().startActivity(intent);
                        } else {
                            Intent intent = new Intent(Common.getContext(), SummaryActivity.class);
                            intent.putExtra("allWeekSummary", mAllWeekSummary.toString());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            Common.getContext().startActivity(intent);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("WeekSummaryRepo", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
