package ffab0.pocketprogramming.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ffab0.pocketprogramming.R;
import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.fragments.CompleteDayFragment;
import ffab0.pocketprogramming.fragments.MainFragment;
import ffab0.pocketprogramming.viewModel.CompleteDayViewModel;
import ffab0.pocketprogramming.viewModel.MainViewModel;


public class DayQuestionsRepository {

    public static Integer mStartDay;
    public DayQuestions mDayQuestions;

    public void startDayFromNoSummary(final View rootView) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mStartDay = response.getInt("start_day");
                            int weekStartDay;
                            if (mStartDay%7 == 0) {
                                weekStartDay = 7;
                            } else {
                                weekStartDay = mStartDay%7;
                            }
                            TextView toNextWeekView = (TextView) rootView.findViewById(R.id.toNextWeek);
                            toNextWeekView.setText(Common.getContext().getString(R.string.challenge_nth_day, weekStartDay));
                        } catch (JSONException e) {
                            Log.d("DayQuestionsRepo", e.toString());
                        }
                    }
                },

                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DayQuestionsRepo", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }

    public void startDayFromMain(final MainFragment mMainFragment, final View rootView) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "/api/day/start_day?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mStartDay = response.getInt("start_day");
                            MainViewModel.setProgress(rootView, mStartDay);
                            MainViewModel.showRateDialog(mMainFragment, mStartDay);
                        } catch (JSONException e) {
                            Log.d("DayQuestionsRepo", e.toString());
                        }
                    }
                },

                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DayQuestionsRepo", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }

    public static void completeDay(int completedDay) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST,
                EnvironmentConstants.HOST_URL + "/api/day/" + completedDay + "/completion",
                new Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DayQuestionRepo", response.toString());
                    }
                },

                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DayQuestionRepo", error.toString());
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

    public void fetchSummary(final CompleteDayFragment mCompleteDayFragment, final LayoutInflater inflater, final View rootView, int day) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "/api/day/" + day + "/summary?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mDayQuestions = new DayQuestions(response);
                        CompleteDayViewModel.buildView(mCompleteDayFragment, inflater, rootView, mDayQuestions);
                    }
                },

                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DayQuestionRepo", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
